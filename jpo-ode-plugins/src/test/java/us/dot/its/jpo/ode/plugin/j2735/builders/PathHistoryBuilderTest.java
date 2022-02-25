package us.dot.its.jpo.ode.plugin.j2735.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import us.dot.its.jpo.ode.plugin.j2735.J2735PathHistory;
import us.dot.its.jpo.ode.util.XmlUtils;
import us.dot.its.jpo.ode.util.XmlUtils.XmlUtilsException;

public class PathHistoryBuilderTest {

  @Test
  public void testGenericPathHistoryWithCrumbDataOnly() {
    JsonNode currGNSSstatus = null;
    try {
      currGNSSstatus = XmlUtils.toObjectNode(
          "<pathHistory><crumbData><PathHistoryPoint><latOffset>-141</latOffset><lonOffset>65</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>670</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-294</latOffset><lonOffset>297</lonOffset><elevationOffset>43</elevationOffset><timeOffset>3571</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-329</latOffset><lonOffset>170</lonOffset><elevationOffset>7</elevationOffset><timeOffset>5150</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-130</latOffset><lonOffset>-142</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>8071</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>129</latOffset><lonOffset>-634</lonOffset><elevationOffset>-75</elevationOffset><timeOffset>10170</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>173</latOffset><lonOffset>-588</lonOffset><elevationOffset>-62</elevationOffset><timeOffset>11560</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>77</latOffset><lonOffset>-645</lonOffset><elevationOffset>-66</elevationOffset><timeOffset>12570</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>29</latOffset><lonOffset>-500</lonOffset><elevationOffset>-42</elevationOffset><timeOffset>13750</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-45</latOffset><lonOffset>-123</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>15710</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-103</latOffset><lonOffset>-20</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>16680</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-54</latOffset><lonOffset>198</lonOffset><elevationOffset>-36</elevationOffset><timeOffset>19130</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-193</latOffset><lonOffset>-294</lonOffset><elevationOffset>19</elevationOffset><timeOffset>22209</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-382</latOffset><lonOffset>-467</lonOffset><elevationOffset>28</elevationOffset><timeOffset>23300</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-390</latOffset><lonOffset>-364</lonOffset><elevationOffset>18</elevationOffset><timeOffset>24320</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-178</latOffset><lonOffset>-120</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>26240</timeOffset></PathHistoryPoint></crumbData></pathHistory>");
    } catch (XmlUtilsException e) {
      fail("XML parsing error:" + e);
    }

    J2735PathHistory actualPathHistory = PathHistoryBuilder.genericPathHistory(currGNSSstatus.get("pathHistory"));

    // assertEquals("string", actualBsm.toJson());
    assertNotNull(actualPathHistory);
    String expected = "{\"initialPosition\":null,\"currGNSSstatus\":null,\"crumbData\":[{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000141,\"lonOffset\":0.0000065,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":6.70},{\"elevationOffset\":4.3,\"heading\":null,\"latOffset\":-0.0000294,\"lonOffset\":0.0000297,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":35.71},{\"elevationOffset\":0.7,\"heading\":null,\"latOffset\":-0.0000329,\"lonOffset\":0.0000170,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":51.50},{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000130,\"lonOffset\":-0.0000142,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":80.71},{\"elevationOffset\":-7.5,\"heading\":null,\"latOffset\":0.0000129,\"lonOffset\":-0.0000634,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":101.70},{\"elevationOffset\":-6.2,\"heading\":null,\"latOffset\":0.0000173,\"lonOffset\":-0.0000588,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":115.60},{\"elevationOffset\":-6.6,\"heading\":null,\"latOffset\":0.0000077,\"lonOffset\":-0.0000645,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":125.70},{\"elevationOffset\":-4.2,\"heading\":null,\"latOffset\":0.0000029,\"lonOffset\":-0.0000500,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":137.50},{\"elevationOffset\":-4.3,\"heading\":null,\"latOffset\":-0.0000045,\"lonOffset\":-0.0000123,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":157.10},{\"elevationOffset\":-4.3,\"heading\":null,\"latOffset\":-0.0000103,\"lonOffset\":-0.0000020,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":166.80},{\"elevationOffset\":-3.6,\"heading\":null,\"latOffset\":-0.0000054,\"lonOffset\":0.0000198,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":191.30},{\"elevationOffset\":1.9,\"heading\":null,\"latOffset\":-0.0000193,\"lonOffset\":-0.0000294,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":222.09},{\"elevationOffset\":2.8,\"heading\":null,\"latOffset\":-0.0000382,\"lonOffset\":-0.0000467,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":233.00},{\"elevationOffset\":1.8,\"heading\":null,\"latOffset\":-0.0000390,\"lonOffset\":-0.0000364,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":243.20},{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000178,\"lonOffset\":-0.0000120,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":262.40}]}";
    assertEquals(expected, actualPathHistory.toString());

  }

  @Test
  public void testGenericPathHistoryWithCrumbDataAndCurrGNSSstatus() {
    JsonNode currGNSSstatus = null;
    try {
      currGNSSstatus = XmlUtils.toObjectNode(
          "<pathHistory><crumbData><PathHistoryPoint><latOffset>-141</latOffset><lonOffset>65</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>670</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-294</latOffset><lonOffset>297</lonOffset><elevationOffset>43</elevationOffset><timeOffset>3571</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-329</latOffset><lonOffset>170</lonOffset><elevationOffset>7</elevationOffset><timeOffset>5150</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-130</latOffset><lonOffset>-142</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>8071</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>129</latOffset><lonOffset>-634</lonOffset><elevationOffset>-75</elevationOffset><timeOffset>10170</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>173</latOffset><lonOffset>-588</lonOffset><elevationOffset>-62</elevationOffset><timeOffset>11560</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>77</latOffset><lonOffset>-645</lonOffset><elevationOffset>-66</elevationOffset><timeOffset>12570</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>29</latOffset><lonOffset>-500</lonOffset><elevationOffset>-42</elevationOffset><timeOffset>13750</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-45</latOffset><lonOffset>-123</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>15710</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-103</latOffset><lonOffset>-20</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>16680</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-54</latOffset><lonOffset>198</lonOffset><elevationOffset>-36</elevationOffset><timeOffset>19130</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-193</latOffset><lonOffset>-294</lonOffset><elevationOffset>19</elevationOffset><timeOffset>22209</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-382</latOffset><lonOffset>-467</lonOffset><elevationOffset>28</elevationOffset><timeOffset>23300</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-390</latOffset><lonOffset>-364</lonOffset><elevationOffset>18</elevationOffset><timeOffset>24320</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-178</latOffset><lonOffset>-120</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>26240</timeOffset></PathHistoryPoint></crumbData><currGNSSstatus>01010101</currGNSSstatus></pathHistory>");
    } catch (XmlUtilsException e) {
      fail("XML parsing error:" + e);
    }

    J2735PathHistory actualPathHistory = PathHistoryBuilder.genericPathHistory(currGNSSstatus.get("pathHistory"));

    // assertEquals("string", actualBsm.toJson());
    assertNotNull(actualPathHistory);
    String expected = "{\"initialPosition\":null,\"currGNSSstatus\":{\"localCorrectionsPresent\":false,\"baseStationType\":true,\"inViewOfUnder5\":true,\"unavailable\":false,\"aPDOPofUnder5\":false,\"isMonitored\":false,\"isHealthy\":true,\"networkCorrectionsPresent\":true},\"crumbData\":[{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000141,\"lonOffset\":0.0000065,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":6.70},{\"elevationOffset\":4.3,\"heading\":null,\"latOffset\":-0.0000294,\"lonOffset\":0.0000297,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":35.71},{\"elevationOffset\":0.7,\"heading\":null,\"latOffset\":-0.0000329,\"lonOffset\":0.0000170,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":51.50},{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000130,\"lonOffset\":-0.0000142,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":80.71},{\"elevationOffset\":-7.5,\"heading\":null,\"latOffset\":0.0000129,\"lonOffset\":-0.0000634,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":101.70},{\"elevationOffset\":-6.2,\"heading\":null,\"latOffset\":0.0000173,\"lonOffset\":-0.0000588,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":115.60},{\"elevationOffset\":-6.6,\"heading\":null,\"latOffset\":0.0000077,\"lonOffset\":-0.0000645,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":125.70},{\"elevationOffset\":-4.2,\"heading\":null,\"latOffset\":0.0000029,\"lonOffset\":-0.0000500,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":137.50},{\"elevationOffset\":-4.3,\"heading\":null,\"latOffset\":-0.0000045,\"lonOffset\":-0.0000123,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":157.10},{\"elevationOffset\":-4.3,\"heading\":null,\"latOffset\":-0.0000103,\"lonOffset\":-0.0000020,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":166.80},{\"elevationOffset\":-3.6,\"heading\":null,\"latOffset\":-0.0000054,\"lonOffset\":0.0000198,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":191.30},{\"elevationOffset\":1.9,\"heading\":null,\"latOffset\":-0.0000193,\"lonOffset\":-0.0000294,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":222.09},{\"elevationOffset\":2.8,\"heading\":null,\"latOffset\":-0.0000382,\"lonOffset\":-0.0000467,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":233.00},{\"elevationOffset\":1.8,\"heading\":null,\"latOffset\":-0.0000390,\"lonOffset\":-0.0000364,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":243.20},{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000178,\"lonOffset\":-0.0000120,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":262.40}]}";
    assertEquals(expected, actualPathHistory.toString());

  }

  @Test
  public void testGenericPathHistoryWithCurrGNSSstatusAndInitialPosition() {
    JsonNode currGNSSstatus = null;
    try {
      currGNSSstatus = XmlUtils.toObjectNode(
          "<pathHistory><crumbData><PathHistoryPoint><latOffset>-141</latOffset><lonOffset>65</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>670</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-294</latOffset><lonOffset>297</lonOffset><elevationOffset>43</elevationOffset><timeOffset>3571</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-329</latOffset><lonOffset>170</lonOffset><elevationOffset>7</elevationOffset><timeOffset>5150</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-130</latOffset><lonOffset>-142</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>8071</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>129</latOffset><lonOffset>-634</lonOffset><elevationOffset>-75</elevationOffset><timeOffset>10170</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>173</latOffset><lonOffset>-588</lonOffset><elevationOffset>-62</elevationOffset><timeOffset>11560</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>77</latOffset><lonOffset>-645</lonOffset><elevationOffset>-66</elevationOffset><timeOffset>12570</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>29</latOffset><lonOffset>-500</lonOffset><elevationOffset>-42</elevationOffset><timeOffset>13750</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-45</latOffset><lonOffset>-123</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>15710</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-103</latOffset><lonOffset>-20</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>16680</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-54</latOffset><lonOffset>198</lonOffset><elevationOffset>-36</elevationOffset><timeOffset>19130</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-193</latOffset><lonOffset>-294</lonOffset><elevationOffset>19</elevationOffset><timeOffset>22209</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-382</latOffset><lonOffset>-467</lonOffset><elevationOffset>28</elevationOffset><timeOffset>23300</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-390</latOffset><lonOffset>-364</lonOffset><elevationOffset>18</elevationOffset><timeOffset>24320</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-178</latOffset><lonOffset>-120</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>26240</timeOffset></PathHistoryPoint></crumbData><initialPosition><long>1800000001</long><lat>0</lat><elevation>0</elevation><heading>0</heading><posAccuracy><semiMajor>0</semiMajor><semiMinor>0</semiMinor><orientation>0</orientation></posAccuracy><posConfidence><pos>unavailable</pos><elevation>unavailable</elevation></posConfidence><speed><transmisson><forwardGears /></transmisson><speed>0</speed></speed><speedConfidence><heading>unavailable</heading><speed>unavailable</speed><throttle>unavailable</throttle></speedConfidence><timeConfidence>UNAVAILABLE</timeConfidence><utcTime><year>0</year><month>0</month><day>0</day><hour>31</hour><minute>60</minute><second>65535</second><offset>0</offset></utcTime></initialPosition><currGNSSstatus>01010101</currGNSSstatus></pathHistory>");
    } catch (XmlUtilsException e) {
      fail("XML parsing error:" + e);
    }

    J2735PathHistory actualPathHistory = PathHistoryBuilder.genericPathHistory(currGNSSstatus.get("pathHistory"));

    // assertEquals("string", actualBsm.toJson());
    assertNotNull(actualPathHistory);
    String expected = "{\"initialPosition\":{\"position\":{\"latitude\":0E-7,\"longitude\":null,\"elevation\":0.0},\"heading\":0.0000,\"posAccuracy\":{\"semiMajor\":0.00,\"semiMinor\":0.00,\"orientation\":0E-10},\"posConfidence\":{\"pos\":\"UNAVAILABLE\",\"elevation\":\"UNAVAILABLE\"},\"speed\":{\"speed\":0.00,\"transmisson\":\"FORWARDGEARS\"},\"speedConfidence\":{\"heading\":\"UNAVAILABLE\",\"speed\":\"UNAVAILABLE\",\"throttle\":\"UNAVAILABLE\"},\"timeConfidence\":\"UNAVAILABLE\",\"utcTime\":{\"day\":null,\"hour\":null,\"minute\":null,\"month\":null,\"offset\":0,\"second\":null,\"year\":null}},\"currGNSSstatus\":{\"localCorrectionsPresent\":false,\"baseStationType\":true,\"inViewOfUnder5\":true,\"unavailable\":false,\"aPDOPofUnder5\":false,\"isMonitored\":false,\"isHealthy\":true,\"networkCorrectionsPresent\":true},\"crumbData\":[{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000141,\"lonOffset\":0.0000065,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":6.70},{\"elevationOffset\":4.3,\"heading\":null,\"latOffset\":-0.0000294,\"lonOffset\":0.0000297,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":35.71},{\"elevationOffset\":0.7,\"heading\":null,\"latOffset\":-0.0000329,\"lonOffset\":0.0000170,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":51.50},{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000130,\"lonOffset\":-0.0000142,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":80.71},{\"elevationOffset\":-7.5,\"heading\":null,\"latOffset\":0.0000129,\"lonOffset\":-0.0000634,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":101.70},{\"elevationOffset\":-6.2,\"heading\":null,\"latOffset\":0.0000173,\"lonOffset\":-0.0000588,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":115.60},{\"elevationOffset\":-6.6,\"heading\":null,\"latOffset\":0.0000077,\"lonOffset\":-0.0000645,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":125.70},{\"elevationOffset\":-4.2,\"heading\":null,\"latOffset\":0.0000029,\"lonOffset\":-0.0000500,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":137.50},{\"elevationOffset\":-4.3,\"heading\":null,\"latOffset\":-0.0000045,\"lonOffset\":-0.0000123,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":157.10},{\"elevationOffset\":-4.3,\"heading\":null,\"latOffset\":-0.0000103,\"lonOffset\":-0.0000020,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":166.80},{\"elevationOffset\":-3.6,\"heading\":null,\"latOffset\":-0.0000054,\"lonOffset\":0.0000198,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":191.30},{\"elevationOffset\":1.9,\"heading\":null,\"latOffset\":-0.0000193,\"lonOffset\":-0.0000294,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":222.09},{\"elevationOffset\":2.8,\"heading\":null,\"latOffset\":-0.0000382,\"lonOffset\":-0.0000467,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":233.00},{\"elevationOffset\":1.8,\"heading\":null,\"latOffset\":-0.0000390,\"lonOffset\":-0.0000364,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":243.20},{\"elevationOffset\":-0.3,\"heading\":null,\"latOffset\":-0.0000178,\"lonOffset\":-0.0000120,\"posAccuracy\":null,\"speed\":null,\"timeOffset\":262.40}]}";
    assertEquals(expected, actualPathHistory.toString());

  }
}
