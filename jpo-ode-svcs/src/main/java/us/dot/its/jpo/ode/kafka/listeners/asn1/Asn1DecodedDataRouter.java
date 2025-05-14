package us.dot.its.jpo.ode.kafka.listeners.asn1;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException;

import us.dot.its.jpo.asn.j2735.r2024.MessageFrame.DSRCmsgID;
import us.dot.its.jpo.ode.coder.OdeMessageFrameDataCreatorHelper;

import us.dot.its.jpo.ode.kafka.topics.JsonTopics;
import us.dot.its.jpo.ode.kafka.topics.PojoTopics;
import us.dot.its.jpo.ode.model.OdeAsn1Data;

import us.dot.its.jpo.ode.model.OdeLogMetadata;
import us.dot.its.jpo.ode.model.OdeMessageFrameData;
import us.dot.its.jpo.ode.model.OdeMsgMetadata;
import us.dot.its.jpo.ode.model.OdeMsgPayload;

import us.dot.its.jpo.ode.util.JsonUtils;
import us.dot.its.jpo.ode.util.XmlUtils;
import us.dot.its.jpo.ode.util.XmlUtils.XmlUtilsException;

/**
 * The Asn1DecodedDataRouter class is a component responsible for processing decoded ASN.1 data from
 * Kafka topics. It listens to messages on a specified Kafka topic and handles the incoming data by
 * processing and forwarding it to different topics based on specific criteria.
 *
 * <p>This listener is specifically designed to handle decoded data produced by the asn1_codec.
 * Upon receiving a payload, it transforms the payload and then determines the appropriate Kafka
 * topic to forward the processed data.</p>
 *
 * <p>The class utilizes Spring Kafka's annotation-driven listener configuration,
 * allowing it to automatically consume messages from a configured Kafka topic.</p>
 */
@Slf4j
@Component
public class Asn1DecodedDataRouter {

  private final PojoTopics pojoTopics;
  private final JsonTopics jsonTopics;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaTemplate<String, OdeMessageFrameData> bsmDataKafkaTemplate;

  /**
   * Exception for Asn1DecodedDataRouter specific failures.
   */
  public static class Asn1DecodedDataRouterException extends Exception {
    public Asn1DecodedDataRouterException(String string) {
      super(string);
    }
  }

  /**
   * Constructs an instance of Asn1DecodedDataRouter.
   *
   * @param kafkaTemplate the KafkaTemplate used for sending messages to Kafka topics.
   */
  public Asn1DecodedDataRouter(KafkaTemplate<String, String> kafkaTemplate,
                               KafkaTemplate<String, OdeMessageFrameData> bsmDataKafkaTemplate,
                               PojoTopics pojoTopics,
                               JsonTopics jsonTopics) {
    this.kafkaTemplate = kafkaTemplate;
    this.bsmDataKafkaTemplate = bsmDataKafkaTemplate;
    this.pojoTopics = pojoTopics;
    this.jsonTopics = jsonTopics;
  }

  /**
   * Processes the given Kafka message payload by transforming it into ODE data and publishing it to
   * appropriate Kafka topics based on its record type.
   */
  @KafkaListener(
      id = "Asn1DecodedDataRouter",
      topics = "${ode.kafka.topics.asn1.decoder-output}"
  )
  public void listen(ConsumerRecord<String, String> consumerRecord)
      throws XmlUtilsException, JsonProcessingException, Asn1DecodedDataRouterException, JsonMappingException, JsonProcessingException, IOException {
    log.debug("Key: {} payload: {}", consumerRecord.key(), consumerRecord.value());

    JSONObject consumed = XmlUtils.toJSONObject(consumerRecord.value())
        .getJSONObject(OdeAsn1Data.class.getSimpleName());

    JSONObject payloadData = consumed.getJSONObject(OdeMsgPayload.PAYLOAD_STRING).getJSONObject(OdeMsgPayload.DATA_STRING);

    if (payloadData.has("code")) {
      throw new Asn1DecodedDataRouterException(
          String.format("Error processing decoded message with code %s and message %s", payloadData.getString("code"),
              payloadData.has("message") ? payloadData.getString("message") : "NULL")
      );
    }

    int msgId = payloadData.getJSONObject("MessageFrame")
        .getInt("messageId");
    DSRCmsgID messageId = new DSRCmsgID(msgId);
    String messageName = messageId.name().orElse("Unknown");


    var metadataJson = XmlUtils.toJSONObject(consumerRecord.value())
        .getJSONObject(OdeAsn1Data.class.getSimpleName())
        .getJSONObject(OdeMsgMetadata.METADATA_STRING);
    OdeLogMetadata.RecordType recordType = OdeLogMetadata.RecordType
        .valueOf(metadataJson.getString("recordType"));

    switch (messageName) {
      case "BasicSafetyMessage" -> {
        switch (recordType) {
          case bsmLogDuringEvent -> routeMessageFrame(consumerRecord, pojoTopics.getBsmDuringEvent(), pojoTopics.getBsm());
          case rxMsg -> routeMessageFrame(consumerRecord, pojoTopics.getRxBsm(), pojoTopics.getBsm());
          case bsmTx -> routeMessageFrame(consumerRecord, pojoTopics.getTxBsm(), pojoTopics.getBsm());
          default -> routeMessageFrame(consumerRecord, pojoTopics.getBsm());
        }
      }
      case "TravelerInformation" -> {
        switch (recordType) {
          case dnMsg -> routeMessageFrame(consumerRecord, jsonTopics.getDnMessage(), jsonTopics.getTim());
          case rxMsg -> routeMessageFrame(consumerRecord, jsonTopics.getRxTim(), jsonTopics.getTim());
          default -> routeMessageFrame(consumerRecord, jsonTopics.getTim());
        }
      }
      case "SPAT" -> {
        switch (recordType) {
          case dnMsg -> routeMessageFrame(consumerRecord, jsonTopics.getDnMessage(), jsonTopics.getSpat());
          case rxMsg -> routeMessageFrame(consumerRecord, jsonTopics.getRxSpat(), jsonTopics.getSpat());
          case spatTx -> routeMessageFrame(consumerRecord, pojoTopics.getTxSpat(), jsonTopics.getSpat());
          default -> routeMessageFrame(consumerRecord, jsonTopics.getSpat());
        }
      }
      case "MapData" -> routeMessageFrame(consumerRecord, pojoTopics.getTxMap(), jsonTopics.getMap());
      case "SignalStatusMessage" -> routeMessageFrame(consumerRecord, pojoTopics.getSsm(), jsonTopics.getSsm());
      case "SignalRequestMessage" -> routeMessageFrame(consumerRecord, pojoTopics.getTxSrm(), jsonTopics.getSrm());
      case "PersonalSafetyMessage" -> routeMessageFrame(consumerRecord, pojoTopics.getTxPsm(), jsonTopics.getPsm());
      default -> routeMessageFrame(consumerRecord, "topic.Ode" + messageName + "Json");
    }
  }

  private void routeMessageFrame(ConsumerRecord<String, String> consumerRecord, String ... topics)
  throws XmlUtils.XmlUtilsException, IOException {
    OdeMessageFrameData odeMessageFrameData =
        OdeMessageFrameDataCreatorHelper.createOdeMessageFrameData(consumerRecord.value());
    String dataStr = JsonUtils.getPlainMapper().writeValueAsString(odeMessageFrameData);
    for (String topic : topics) {
      kafkaTemplate.send(topic, consumerRecord.key(), dataStr);
    }
  }

}
