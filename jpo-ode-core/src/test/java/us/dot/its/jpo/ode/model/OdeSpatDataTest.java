package us.dot.its.jpo.ode.model;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeSpatDataTest {
    @Test
    public void shouldDeserializJson() {

        final String bsmJson = "";

        var deserialized = (OdeSpatData)JsonUtils.fromJson(bsmJson, OdeSpatData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeSpatMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeSpatPayload);
        
    }
}
