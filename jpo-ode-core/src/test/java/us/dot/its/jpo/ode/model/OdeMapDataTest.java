package us.dot.its.jpo.ode.model;

import org.junit.Test;
import static org.junit.Assert.*;

import us.dot.its.jpo.ode.util.JsonUtils;

public class OdeMapDataTest {
    @Test
    public void shouldDeserializJson() {

        final String bsmJson = "";

        var deserialized = (OdeMapData)JsonUtils.fromJson(bsmJson, OdeMapData.class);
        assertNotNull(deserialized);
        assertTrue(deserialized.getMetadata() instanceof OdeMapMetadata);
        assertTrue(deserialized.getPayload() instanceof OdeMapPayload);
        
    }
}
