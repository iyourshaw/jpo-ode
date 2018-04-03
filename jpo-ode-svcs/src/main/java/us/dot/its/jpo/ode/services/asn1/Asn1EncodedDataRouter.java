package us.dot.its.jpo.ode.services.asn1;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.ScopedPDU;
import org.snmp4j.event.ResponseEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import us.dot.its.jpo.ode.OdeProperties;
import us.dot.its.jpo.ode.context.AppContext;
import us.dot.its.jpo.ode.dds.DdsClient.DdsClientException;
import us.dot.its.jpo.ode.dds.DdsDepositor;
import us.dot.its.jpo.ode.dds.DdsRequestManager.DdsRequestManagerException;
import us.dot.its.jpo.ode.dds.DdsStatusMessage;
import us.dot.its.jpo.ode.eventlog.EventLogger;
import us.dot.its.jpo.ode.model.Asn1Encoding;
import us.dot.its.jpo.ode.model.OdeAsdPayload;
import us.dot.its.jpo.ode.model.OdeAsn1Data;
import us.dot.its.jpo.ode.model.OdeMsgMetadata;
import us.dot.its.jpo.ode.model.OdeMsgPayload;
import us.dot.its.jpo.ode.model.OdeTravelerInputData;
import us.dot.its.jpo.ode.model.Asn1Encoding.EncodingRule;
import us.dot.its.jpo.ode.plugin.RoadSideUnit.RSU;
import us.dot.its.jpo.ode.plugin.j2735.DdsAdvisorySituationData;
import us.dot.its.jpo.ode.plugin.j2735.J2735DSRCmsgID;
import us.dot.its.jpo.ode.plugin.j2735.J2735MessageFrame;
import us.dot.its.jpo.ode.plugin.j2735.builders.GeoRegionBuilder;
import us.dot.its.jpo.ode.plugin.j2735.timstorage.MessageFrame;
import us.dot.its.jpo.ode.plugin.SNMP;
import us.dot.its.jpo.ode.plugin.SituationDataWarehouse.SDW;
import us.dot.its.jpo.ode.plugin.ieee1609dot2.Ieee1609Dot2Content;
import us.dot.its.jpo.ode.plugin.ieee1609dot2.Ieee1609Dot2Data;
import us.dot.its.jpo.ode.plugin.ieee1609dot2.Ieee1609Dot2DataTag;
import us.dot.its.jpo.ode.snmp.SnmpSession;
import us.dot.its.jpo.ode.traveler.TimController;
import us.dot.its.jpo.ode.traveler.TimController.TimControllerException;
import us.dot.its.jpo.ode.traveler.TimPduCreator;
import us.dot.its.jpo.ode.traveler.TimPduCreator.TimPduCreatorException;
import us.dot.its.jpo.ode.util.JsonUtils;
import us.dot.its.jpo.ode.util.JsonUtils.JsonUtilsException;
import us.dot.its.jpo.ode.util.XmlUtils.XmlUtilsException;
import us.dot.its.jpo.ode.util.XmlUtils;
import us.dot.its.jpo.ode.wrapper.AbstractSubscriberProcessor;
import us.dot.its.jpo.ode.wrapper.MessageProducer;
import us.dot.its.jpo.ode.wrapper.WebSocketEndpoint.WebSocketException;

public class Asn1EncodedDataRouter extends AbstractSubscriberProcessor<String, String> {

   public static class Asn1EncodedDataRouterException extends Exception {

      private static final long serialVersionUID = 1L;

      public Asn1EncodedDataRouterException(String string) {
         super(string);
      }

   }

   private Logger logger = LoggerFactory.getLogger(this.getClass());

    private OdeProperties odeProperties;
    private DdsDepositor<DdsStatusMessage> depositor;
    
    private MessageProducer<String, String> stringMsgProducer;

    public Asn1EncodedDataRouter(OdeProperties odeProps) {
      super();
      this.odeProperties = odeProps;

      try {
         depositor = new DdsDepositor<>(this.odeProperties);
      } catch (Exception e) {
         String msg = "Error starting SDW depositor";
         EventLogger.logger.error(msg, e);
         logger.error(msg, e);
      }
      
      this.stringMsgProducer = MessageProducer.defaultStringMessageProducer(odeProperties.getKafkaBrokers(),
            odeProperties.getKafkaProducerType());

    }

   @Override
   public Object process(String consumedData) {
      try {
         logger.debug("Consumed: {}", consumedData);
         JSONObject consumedObj = XmlUtils.toJSONObject(consumedData).getJSONObject(OdeAsn1Data.class.getSimpleName());

         /*
          * When receiving the 'rsus' in xml, since there is only one 'rsu' and
          * there is no construct for array in xml, the rsus does not translate
          * to an array of 1 element. The following workaround, resolves this
          * issue.
          */
         JSONObject metadata = consumedObj.getJSONObject(AppContext.METADATA_STRING);

         if (metadata.has("request")) {
            JSONObject request = metadata.getJSONObject("request");

            if (request.has("rsus")) {
               Object rsu = request.get("rsus");
               if (!(rsu instanceof JSONArray)) {
                  JSONArray rsus = new JSONArray();
                  rsus.put(rsu);
                  request.put("rsus", rsus);
               }
            }

            // Convert JSON to POJO
            OdeTravelerInputData travelerinputData = buildTravelerInputData(consumedObj);

            processEncodedTim(travelerinputData, consumedObj);

         } else {
            throw new Asn1EncodedDataRouterException("Encoder response missing 'request'");
         }
      } catch (Exception e) {
         String msg = "Error in processing received message from ASN.1 Encoder module: " + consumedData;
         EventLogger.logger.error(msg, e);
         logger.error(msg, e);
      }
      return null;
   }

    public OdeTravelerInputData buildTravelerInputData(JSONObject consumedObj) {
       String request = consumedObj
             .getJSONObject(AppContext.METADATA_STRING)
             .getJSONObject("request").toString();
       
       // Convert JSON to POJO
       OdeTravelerInputData travelerinputData = null;
       try {
          logger.debug("JSON: {}", request);
          travelerinputData = (OdeTravelerInputData) JsonUtils.fromJson(request, OdeTravelerInputData.class);

       } catch (Exception e) {
          String errMsg = "Malformed JSON.";
          EventLogger.logger.error(errMsg, e);
          logger.error(errMsg, e);
       }

       return travelerinputData;
    }

    public void processEncodedTim(OdeTravelerInputData travelerInfo, JSONObject consumedObj) throws TimControllerException {
       // Send TIMs and record results
       HashMap<String, String> responseList = new HashMap<>();

       JSONObject dataObj = consumedObj
             .getJSONObject(AppContext.PAYLOAD_STRING)
             .getJSONObject(AppContext.DATA_STRING);
       
       if (null != travelerInfo.getSdw()) {
          JSONObject asdObj = null;
          if (dataObj.has("AdvisorySituationData")) {
             asdObj = dataObj.getJSONObject("AdvisorySituationData");
          } else {
             logger.error("ASD structure present in metadata but not in JSONObject!");
          }
         
         if (null != asdObj) {
            String asdBytes = asdObj.getString("bytes");

            // Deposit to DDS
            String ddsMessage = "";
            try {
               depositToDDS(travelerInfo, asdBytes);
               ddsMessage = "\"dds_deposit\":{\"success\":\"true\"}";
               logger.info("DDS deposit successful.");
            } catch (Exception e) {
               ddsMessage = "\"dds_deposit\":{\"success\":\"false\"}";
               logger.error("Error on DDS deposit.", e);
            }

            responseList.put("ddsMessage", ddsMessage);
         } else {
            logger.error("I think this is when we would add the ASD header {}", consumedObj);
            String msg = "ASN.1 Encoder did not return ASD encoding {}";
            EventLogger.logger.error(msg, consumedObj.toString());
            logger.error(msg, consumedObj.toString());
         }
       }
       
       JSONObject mfObj = dataObj.getJSONObject("MessageFrame");
       
       String encodedTim = mfObj.getString("bytes");
       logger.debug("Encoded message: {}", encodedTim);
       
       logger.debug("Sending message for signature!");
       String signedTim = sendForSignature(encodedTim);
       
       logger.debug("Message signed!");
       
       JSONObject jsonifiedResponse = null;
       try {
         jsonifiedResponse = JsonUtils.toJSONObject(signedTim);
      } catch (JsonUtilsException e1) {
         logger.error("Unable to parse signed message response {}", e1);
      }
       
       logger.debug("Publishing message for round 2 encoding!");
       String xmlizedMessage = putSignedTimIntoAsdObject(travelerInfo, jsonifiedResponse.getString("result"));
       stringMsgProducer.send(odeProperties.getKafkaTopicAsn1EncoderInput(), null, xmlizedMessage);
       
      // only send message to rsu if snmp, rsus, and message frame fields are present
      if (null != travelerInfo.getSnmp() && null != travelerInfo.getRsus() && null != mfObj) {
         String timBytes = mfObj.getString("bytes");
         logger.debug("Encoded message: {}", timBytes);
         for (RSU curRsu : travelerInfo.getRsus()) {

            ResponseEvent rsuResponse = null;
            String httpResponseStatus = null;

             try {
                rsuResponse = createAndSend(travelerInfo.getSnmp(), curRsu, 
                   travelerInfo.getOde().getIndex(), timBytes, travelerInfo.getOde().getVerb());

                if (null == rsuResponse || null == rsuResponse.getResponse()) {
                   // Timeout
                   httpResponseStatus = "Timeout";
                } else if (rsuResponse.getResponse().getErrorStatus() == 0) {
                   // Success
                   httpResponseStatus = "Success";
                } else if (rsuResponse.getResponse().getErrorStatus() == 5) {
                   // Error, message already exists
                   httpResponseStatus = "Message already exists at ".concat(Integer.toString(travelerInfo.getOde().getIndex()));
                } else {
                   // Misc error
                   httpResponseStatus = "Error code " + rsuResponse.getResponse().getErrorStatus() + " "
                               + rsuResponse.getResponse().getErrorStatusText();
                }

             } catch (Exception e) {
                String msg = "Exception caught in TIM deposit loop.";
               EventLogger.logger.error(msg, e);
                logger.error(msg, e);
                httpResponseStatus = e.getClass().getName() + ": " + e.getMessage();
             }
             
             responseList.put(curRsu.getRsuTarget(), httpResponseStatus);
          }

       }
       
       logger.info("TIM deposit response {}", responseList);
       
       return;
    }
    
   public String putSignedTimIntoAsdObject(OdeTravelerInputData travelerInputData, String signedMsg) {

      SDW sdw = travelerInputData.getSdw();
      SNMP snmp = travelerInputData.getSnmp();
      DdsAdvisorySituationData asd = null;
      //
      Ieee1609Dot2DataTag ieeeDataTag = new Ieee1609Dot2DataTag();
      Ieee1609Dot2Data ieee = new Ieee1609Dot2Data();
      Ieee1609Dot2Content ieeeContent = new Ieee1609Dot2Content();
      J2735MessageFrame j2735Mf = new J2735MessageFrame();
      MessageFrame mf = new MessageFrame();
      mf.setMessageFrame(j2735Mf);
      ieeeContent.setUnsecuredData(mf);
      ieee.setContent(ieeeContent);
      ieeeDataTag.setIeee1609Dot2Data(ieee);

      byte sendToRsu = travelerInputData.getRsus() != null ? DdsAdvisorySituationData.RSU
            : DdsAdvisorySituationData.NONE;
      byte distroType = (byte) (DdsAdvisorySituationData.IP | sendToRsu);
      //
      String outputXml = null;
      try {
         if (null != snmp) {

            asd = new DdsAdvisorySituationData(snmp.getDeliverystart(), snmp.getDeliverystop(), ieeeDataTag,
                  GeoRegionBuilder.ddsGeoRegion(sdw.getServiceRegion()), sdw.getTtl(), sdw.getGroupID(),
                  sdw.getRecordId(), distroType);
         } else {
            asd = new DdsAdvisorySituationData(sdw.getDeliverystart(), sdw.getDeliverystop(), ieeeDataTag,
                  GeoRegionBuilder.ddsGeoRegion(sdw.getServiceRegion()), sdw.getTtl(), sdw.getGroupID(),
                  sdw.getRecordId(), distroType);
         }

      
      OdeMsgPayload payload = null;

      ObjectNode dataBodyObj = JsonUtils.newNode();
         ObjectNode asdObj = JsonUtils.toObjectNode(asd.toJson());
         ObjectNode mfBodyObj = (ObjectNode) asdObj.findValue("MessageFrame");
         mfBodyObj.put("bytes", signedMsg);

         dataBodyObj.set("AdvisorySituationData", asdObj);

         payload = new OdeAsdPayload(asd);
         
         ObjectNode payloadObj = JsonUtils.toObjectNode(payload.toJson());
         payloadObj.set(AppContext.DATA_STRING, dataBodyObj);
         
         OdeMsgMetadata metadata = new OdeMsgMetadata(payload);
         ObjectNode metaObject = JsonUtils.toObjectNode(metadata.toJson());
         
         metaObject.set("encodings_palceholder", null);
         
  
         
         ObjectNode message = JsonUtils.newNode();
         message.set(AppContext.METADATA_STRING, metaObject);
         message.set(AppContext.PAYLOAD_STRING, payloadObj);

         ObjectNode root = JsonUtils.newNode();
         root.set("OdeAsn1Data", message);
         
         outputXml = XmlUtils.toXmlS(root);
         String encStr = buildEncodings(asd);
         outputXml = outputXml.replace("<encodings_palceholder/>", encStr);
         
         // remove the surrounding <ObjectNode></ObjectNode>
         outputXml = outputXml.replace("<ObjectNode>", "");
         outputXml = outputXml.replace("</ObjectNode>", "");
         
      } catch (ParseException | JsonUtilsException | XmlUtilsException e) {
         logger.error("Parsing exception thrown while populating ASD structure: {}", e);
      }
      
      logger.debug("Here is the fully crafted structure, I think this should go to the encoder again: {}", outputXml);
      
      return outputXml;
   }
   
   private String buildEncodings(DdsAdvisorySituationData asd) throws JsonUtilsException, XmlUtilsException {
      ArrayNode encodings = JsonUtils.newArrayNode();
         encodings.add(addEncoding("Ieee1609Dot2Data", "Ieee1609Dot2Data", EncodingRule.COER));
         encodings.add(addEncoding("AdvisorySituationData", "AdvisorySituationData", EncodingRule.UPER));
      ObjectNode encodingWrap = (ObjectNode) JsonUtils.newNode().set("wrap", encodings);
      String encStr = XmlUtils.toXmlS(encodingWrap)
            .replace("</wrap><wrap>", "")
            .replace("<wrap>", "")
            .replace("</wrap>", "")
            .replace("<ObjectNode>", "<encodings>")
            .replace("</ObjectNode>", "</encodings>");
      return encStr;
   }
   
   private JsonNode addEncoding(String name, String type, EncodingRule rule) throws JsonUtilsException {
      Asn1Encoding mfEnc = new Asn1Encoding(name, type, rule);
      return JsonUtils.newNode().set("encodings", JsonUtils.toObjectNode(mfEnc.toJson()));
   }
    
    public String sendForSignature(String message) {
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);

       HttpEntity<String> entity = new HttpEntity<>(TimController.jsonKeyValue("message", message), headers);

       RestTemplate template = new RestTemplate();

       logger.info("Rest request: {}", entity);

       String uri = "http://localhost:8090/sign";

//       ResponseEntity<String> respEntity = template.postForEntity(uri, TimController.jsonKeyValue("message", message),
//             String.class);
       
       ResponseEntity<String> respEntity = template.postForEntity(uri, entity,
             String.class);

       logger.info("Security services module response: {}", respEntity);
       
       return respEntity.getBody();
    }

    /**
     * Create an SNMP session given the values in
     * 
     * @param tim
     *           - The TIM parameters (payload, channel, mode, etc)
     * @param props
     *           - The SNMP properties (ip, username, password, etc)
     * @return ResponseEvent
     * @throws TimPduCreatorException
     * @throws IOException
     */
    public static ResponseEvent createAndSend(SNMP snmp, RSU rsu, int index, String payload, int verb)
          throws IOException, TimPduCreatorException {

       SnmpSession session = new SnmpSession(rsu);

       // Send the PDU
       ResponseEvent response = null;
       ScopedPDU pdu = TimPduCreator.createPDU(snmp, payload, index, verb);
       response = session.set(pdu, session.getSnmp(), session.getTarget(), false);
       EventLogger.logger.info("Message Sent to {}: {}", rsu.getRsuTarget(), payload);
       return response;
    }

    private void depositToDDS(OdeTravelerInputData travelerinputData, String asdBytes)
          throws ParseException, DdsRequestManagerException, DdsClientException, WebSocketException {
       // Step 4 - Step Deposit TIM to SDW if sdw element exists
       if (travelerinputData.getSdw() != null) {
          depositor.deposit(asdBytes);
          EventLogger.logger.info("Message Deposited to SDW: {}", asdBytes);
       }
    }

//    /**
//    * Temporary method using OSS to build a ASD with IEEE 1609.2 encapsulating MF/TIM
//    * @param travelerInputData
//    * @param mfTimBytes
//    * @throws ParseException
//    * @throws EncodeFailedExceptionmcon
//    * @throws DdsRequestManagerException
//    * @throws DdsClientException
//    * @throws WebSocketException
//    * @throws EncodeNotSupportedException
//    */
//   private void depositToDDSUsingOss(OdeTravelerInputData travelerInputData, String mfTimBytes) 
//         throws ParseException, EncodeFailedException, DdsRequestManagerException, DdsClientException, WebSocketException, EncodeNotSupportedException {
//      // Step 4 - Step Deposit IEEE 1609.2 wrapped TIM to SDW if sdw element exists
//      SDW sdw = travelerInputData.getSdw();
//      if (sdw != null) {
//         Ieee1609Dot2Data ieee1609Data = new Ieee1609Dot2Data();
//         ieee1609Data.setProtocolVersion(new Uint8(3));
//         Ieee1609Dot2Content ieee1609Dot2Content = new Ieee1609Dot2Content();
//         ieee1609Dot2Content.setUnsecuredData(new Opaque(CodecUtils.fromHex(mfTimBytes)));
//         ieee1609Data.setContent(ieee1609Dot2Content);
//         ByteBuffer ieee1609DataBytes = ossCoerCoder.encode(ieee1609Data);
//         
//         // take deliverystart and stop times from SNMP object, if present
//         // else take from SDW object
//         SNMP snmp = travelerInputData.getSnmp();
//         AsdMessage asdMsg = null;
//         if (null != snmp) {
//            asdMsg = new AsdMessage(
//               snmp.getDeliverystart(),
//               snmp.getDeliverystop(), 
//               CodecUtils.toHex(ieee1609DataBytes.array()),
//               sdw.getServiceRegion(), 
//               sdw.getTtl());
//         } else {
//            asdMsg = new AsdMessage(
//               sdw.getDeliverystart(),
//               sdw.getDeliverystop(), 
//               CodecUtils.toHex(ieee1609DataBytes.array()),
//               sdw.getServiceRegion(), 
//               sdw.getTtl());
//         }
//
//         depositor.deposit(asdMsg.encodeHex());
//         EventLogger.logger.info("Message Deposited to SDW: {}", mfTimBytes);
//      }
//      
//   }

}
