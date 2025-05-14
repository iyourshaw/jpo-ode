package us.dot.its.jpo.ode.coder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import us.dot.its.jpo.asn.j2735.r2024.MessageFrame.MessageFrame;
import us.dot.its.jpo.ode.model.OdeMessageFrameData;
import us.dot.its.jpo.ode.model.OdeMessageFrameMetadata;
import us.dot.its.jpo.ode.model.OdeMessageFramePayload;
import us.dot.its.jpo.ode.model.OdeMsgMetadata;

import us.dot.its.jpo.ode.model.ReceivedMessageDetails;
import us.dot.its.jpo.ode.model.RxSource;
import us.dot.its.jpo.ode.util.JsonUtils;
import us.dot.its.jpo.ode.util.XmlUtils;
import us.dot.its.jpo.ode.util.XmlUtils.XmlUtilsException;

public class OdeMessageFrameDataCreatorHelper {

  public static OdeMessageFrameData createOdeMessageFrameData(String consumedData) throws XmlUtilsException {
    ObjectNode consumed = XmlUtils.toObjectNode(consumedData);

    JsonNode metadataNode = consumed.findValue(OdeMsgMetadata.METADATA_STRING);
    if (metadataNode instanceof ObjectNode) {
      ObjectNode object = (ObjectNode) metadataNode;
      object.remove(OdeMsgMetadata.ENCODINGS_STRING);

      //Spat header file does not have a location and use predefined set required RxSource
      ReceivedMessageDetails receivedMessageDetails = new ReceivedMessageDetails();
      receivedMessageDetails.setRxSource(RxSource.NA);
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode;
      try {
        jsonNode = objectMapper.readTree(receivedMessageDetails.toJson());
        object.set(OdeMsgMetadata.RECEIVEDMSGDETAILS_STRING, jsonNode);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    OdeMessageFrameMetadata metadata = (OdeMessageFrameMetadata) JsonUtils.fromJson(metadataNode.toString(), OdeMessageFrameMetadata.class);

    if(metadataNode.findValue("certPresent") != null) {
      boolean isCertPresent = metadataNode.findValue("certPresent").asBoolean();
      metadata.setCertPresent(isCertPresent);
    }

    if (metadata.getSchemaVersion() <= 4) {
      metadata.setReceivedMessageDetails(null);
    }

    //OdeSpatPayload payload = new OdeSpatPayload(SPATBuilder.genericSPAT(consumed.findValue("SPAT")));
    JsonNode messageFrameNode = consumed.findValue("MessageFrame");
    MessageFrame<?> messageFrame = XmlUtils.getStaticXmlMapper().convertValue(messageFrameNode, MessageFrame.class);
    OdeMessageFramePayload payload = new OdeMessageFramePayload(messageFrame);
    return new OdeMessageFrameData(metadata, payload);
  }

}
