package us.dot.its.jpo.ode.plugin.j2735;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class J2735ConnectionTest {

	@Test
	public void testGettersSetters() {
		J2735ConnectingLane connectionLane = new J2735ConnectingLane();
		connectionLane.setLane(0);
		
		J2735Connection connection = new J2735Connection();
		connection.setConnectingLane(connectionLane);
		
		connection.setSignalGroup(10);
		connection.setUserClass(10);
		connection.setConnectionID(10);
		
		J2735IntersectionReferenceID remoteIntersection = new J2735IntersectionReferenceID();
		remoteIntersection.setId(Integer.valueOf(10));
		remoteIntersection.setRegion(10);
		connection.setRemoteIntersection(remoteIntersection);
		
		assertEquals(remoteIntersection.getId().intValue(), 10);
		assertEquals(remoteIntersection.getRegion().intValue(), 10);
		assertEquals(connection.getSignalGroup().intValue(), 10);
	}
}
