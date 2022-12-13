package us.dot.its.jpo.ode.model;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeTimDataTest {
    @Test
    public void shouldDeserializeJson() {

        final String bsmJson = "";

        var deserialized = (OdeTimData)JsonUtils.fromJson(bsmJson, OdeTimData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeTimMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeTimPayload);
        
    }
}
