package us.dot.its.jpo.ode.plugin.j2735;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class J2735IntersectionAccessPointTest {
    
    @Test
	public void testGettersSetters() {
        J2735IntersectionAccessPoint interAccessPoint = new J2735IntersectionAccessPoint();
        interAccessPoint.setApproach(5);
        assertEquals(interAccessPoint.getApproach().intValue(), 5);

        interAccessPoint.setConnection(4);
        assertEquals(interAccessPoint.getConnection().intValue(), 4);

        interAccessPoint.setLane(3);
        assertEquals(interAccessPoint.getLane().intValue(), 3);
	}
}
