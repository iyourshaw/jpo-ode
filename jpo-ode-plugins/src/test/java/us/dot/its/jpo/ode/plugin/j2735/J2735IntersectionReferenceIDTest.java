package us.dot.its.jpo.ode.plugin.j2735;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class J2735IntersectionReferenceIDTest {
    
    @Test
	public void testGettersSetters() {
        J2735IntersectionReferenceID interRefId = new J2735IntersectionReferenceID();
        interRefId.setId(5);
        assertEquals(interRefId.getId().intValue(), 5);

        interRefId.setRegion(4);
        assertEquals(interRefId.getRegion().intValue(), 4);
	}
}
