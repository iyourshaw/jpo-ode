package us.dot.its.jpo.ode.model;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeBsmDataTest {
    
    @Test
    public void shouldDeserializJson() {

        final String bsmJson = "{\"metadata\":{\"bsmSource\":\"RV\",\"logFileName\":\"\",\"recordType\":\"bsmTx\",\"securityResultCode\":\"success\",\"receivedMessageDetails\":{\"locationData\":{\"latitude\":\"\",\"longitude\":\"\",\"elevation\":\"\",\"speed\":\"\",\"heading\":\"\"},\"rxSource\":\"RV\"},\"payloadType\":\"us.dot.its.jpo.ode.model.OdeBsmPayload\",\"serialId\":{\"streamId\":\"504becf3-8e20-49cb-a2d7-25b646c34d0f\",\"bundleSize\":1,\"bundleId\":0,\"recordId\":0,\"serialNumber\":0},\"odeReceivedAt\":\"2022-06-17T19:14:21.223956Z\",\"schemaVersion\":6,\"maxDurationTime\":0,\"recordGeneratedAt\":\"\",\"sanitized\":false,\"odePacketID\":\"\",\"odeTimStartDateTime\":\"\",\"originIp\":\"10.11.81.12\"},\"payload\":{\"data\":{\"coreData\":{\"msgCnt\":46,\"id\":\"E6A99808\",\"secMark\":21061,\"position\":{\"latitude\":39.5881304,\"longitude\":-105.0910403,\"elevation\":1692.0},\"accelSet\":{\"accelLong\":-0.07,\"accelYaw\":-0.09},\"accuracy\":{\"semiMajor\":2.0,\"semiMinor\":2.0,\"orientation\":44.49530799},\"transmission\":\"UNAVAILABLE\",\"speed\":22.62,\"heading\":169.3,\"brakes\":{\"wheelBrakes\":{\"leftFront\":false,\"rightFront\":false,\"unavailable\":true,\"leftRear\":false,\"rightRear\":false},\"traction\":\"unavailable\",\"abs\":\"off\",\"scs\":\"unavailable\",\"brakeBoost\":\"unavailable\",\"auxBrakes\":\"unavailable\"},\"size\":{\"width\":180,\"length\":480}},\"partII\":[{\"id\":\"VehicleSafetyExtensions\",\"value\":{\"pathHistory\":{\"crumbData\":[{\"elevationOffset\":0.8,\"latOffset\":-0.0001802,\"lonOffset\":0.0000434,\"timeOffset\":0.89},{\"elevationOffset\":4.5,\"latOffset\":-0.0011801,\"lonOffset\":0.0002357,\"timeOffset\":5.7},{\"elevationOffset\":9.3,\"latOffset\":-0.0023623,\"lonOffset\":0.0003881,\"timeOffset\":11.1}]},\"pathPrediction\":{\"confidence\":70.0,\"radiusOfCurve\":0.0}}}]},\"dataType\":\"us.dot.its.jpo.ode.plugin.j2735.J2735Bsm\"}}";

        var deserialized = (OdeBsmData)JsonUtils.fromJson(bsmJson, OdeBsmData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeBsmMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeBsmPayload);
        System.out.printf("%s", deserialized);
        
    }
}
