package us.dot.its.jpo.ode.plugin.j2735;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class J2735SignalRequestTest {
    
    @Test
	public void testGettersSetters() {
        J2735SignalRequest signalRequest = new J2735SignalRequest();
        
        J2735IntersectionReferenceID interRefId = new J2735IntersectionReferenceID();
        interRefId.setId(5);
        signalRequest.setId(interRefId);
        assertEquals(signalRequest.getId().getId().intValue(), 5);

        signalRequest.setRequestID(5);
        assertEquals(signalRequest.getRequestID().intValue(), 5);

        signalRequest.setRequestType(J2735PriorityRequestType.priorityRequest);
        assertEquals(signalRequest.getRequestType(), J2735PriorityRequestType.priorityRequest);

        J2735IntersectionAccessPoint interAccessPoint = new J2735IntersectionAccessPoint();
        interAccessPoint.setApproach(5);
        signalRequest.setInBoundLane(interAccessPoint);
        assertEquals(signalRequest.getInBoundLane().getApproach().intValue(), 5);

        signalRequest.setOutBoundLane(interAccessPoint);
        assertEquals(signalRequest.getOutBoundLane().getApproach().intValue(), 5);
	}
}
