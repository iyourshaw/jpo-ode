package us.dot.its.jpo.ode.plugin.j2735;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class J2735VehicleIDTest {
    
    @Test
	public void testGettersSetters() {
        J2735VehicleID vehicleId = new J2735VehicleID();
        vehicleId.setEntityID("test");
        vehicleId.setStationID(105L);
        assertEquals(vehicleId.getEntityID(), "test");
        assertEquals(vehicleId.getStationID().intValue(), 105L);
	}
}
