package us.dot.its.jpo.ode.plugin.j2735;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class J2735SignalRequestPackageTest {

    @Test
	public void testGettersSetters() {
		J2735SignalRequestPackage signalRequestPackage = new J2735SignalRequestPackage();

        J2735SignalRequest signalRequest = new J2735SignalRequest();
        signalRequest.setRequestID(5);
        signalRequestPackage.setRequest(signalRequest);
		assertEquals(signalRequestPackage.getRequest().getRequestID().intValue(), 5);

        signalRequestPackage.setMinute(5);
        assertEquals(signalRequestPackage.getMinute().intValue(), 5);

        signalRequestPackage.setSecond(5);
        assertEquals(signalRequestPackage.getSecond().intValue(), 5);

        signalRequestPackage.setDuration(5);
        assertEquals(signalRequestPackage.getDuration().intValue(), 5);
	}
}
