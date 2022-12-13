package us.dot.its.jpo.ode.model;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeSrmDataTest {
    @Test
    public void shouldDeserializeJson() {

        final String bsmJson = "";

        var deserialized = (OdeSrmData)JsonUtils.fromJson(bsmJson, OdeSrmData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeSrmMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeSrmPayload);
        
    }
}
