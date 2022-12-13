package us.dot.its.jpo.ode.model;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeSsmDataTest {
    @Test
    public void shouldDeserializeJson() {

        final String bsmJson = "";

        var deserialized = (OdeSsmData)JsonUtils.fromJson(bsmJson, OdeSsmData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeSsmMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeSsmPayload);
        
    }
}
