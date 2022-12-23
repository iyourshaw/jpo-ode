package us.dot.its.jpo.ode.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeSpatDataTest {
    
    final String json = "{\"metadata\":{\"logFileName\":\"\",\"recordType\":\"spatTx\",\"securityResultCode\":\"success\",\"receivedMessageDetails\":{\"locationData\":null,\"rxSource\":\"NA\"},\"encodings\":null,\"payloadType\":\"us.dot.its.jpo.ode.model.OdeSpatPayload\",\"serialId\":{\"streamId\":\"24d9dfcb-1ca0-41b8-8576-f4a00a51218e\",\"bundleSize\":1,\"bundleId\":0,\"recordId\":0,\"serialNumber\":0},\"odeReceivedAt\":\"2022-06-17T19:02:05.366148Z\",\"schemaVersion\":6,\"maxDurationTime\":0,\"recordGeneratedAt\":\"\",\"recordGeneratedBy\":null,\"sanitized\":false,\"odePacketID\":\"\",\"odeTimStartDateTime\":\"\",\"spatSource\":\"V2X\",\"originIp\":\"10.11.81.12\",\"isCertPresent\":false},\"payload\":{\"data\":{\"timeStamp\":null,\"name\":null,\"intersectionStateList\":null},\"dataType\":\"us.dot.its.jpo.ode.plugin.j2735.J2735SPAT\"}}";

    @Test
    public void shouldDeserializeJson() {
        final var deserialized = (OdeSpatData)JsonUtils.fromJson(json, OdeSpatData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeSpatMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeSpatPayload);
        
    }

    @Test
    public void serializationShouldNotAddClassProperty() {
        final var deserialized = (OdeSpatData)JsonUtils.fromJson(json, OdeSpatData.class);
        final String serialized = deserialized.toJson(false);
        assertFalse(serialized.contains("@class"));
    }

    @Test
    public void shouldValidateJson() throws Exception {
        final var deserialized = (OdeSpatData)JsonUtils.fromJson(json, OdeSpatData.class);
        final String serialized = deserialized.toJson(false);

        // Load json schema from resource
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        final JsonSchema schema = factory.getSchema(getClass().getClassLoader().getResource("schemas/schema-spat.json").toURI());
        final JsonNode node = (JsonNode)JsonUtils.fromJson(serialized, JsonNode.class);
        Set<ValidationMessage> validationMessages = schema.validate(node);
        assertEquals(String.format("Json validation errors: %s", validationMessages), validationMessages.size(), 0);
    }
}
