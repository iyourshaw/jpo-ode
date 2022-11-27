/*******************************************************************************
 * Copyright 2018 572682
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package us.dot.its.jpo.ode.plugin.j2735.builders;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import us.dot.its.jpo.ode.plugin.j2735.J2735Bsm;
import us.dot.its.jpo.ode.plugin.j2735.builders.BsmPart2ContentBuilder.BsmPart2ContentBuilderException;
import us.dot.its.jpo.ode.util.XmlUtils;
import us.dot.its.jpo.ode.util.XmlUtils.XmlUtilsException;

public class BsmBuilderTest {

   @Test
   public void shouldTranslateBsm() throws BsmPart2ContentBuilderException {

      JsonNode jsonBsm = null;
      try {
         jsonBsm = XmlUtils.toObjectNode(
               "<OdeAsn1Data><metadata><payloadType>us.dot.its.jpo.ode.model.OdeAsn1Payload</payloadType><serialId><streamId>538ffb5c-ce7d-438d-8727-8100c11f08b2</streamId><bundleSize>1</bundleSize><bundleId>4</bundleId><recordId>2</recordId><serialNumber>0</serialNumber></serialId><odeReceivedAt>2018-05-03T18:36:41.38Z[UTC]</odeReceivedAt><schemaVersion>4</schemaVersion><recordGeneratedAt>2018-05-03T17:54:14.206Z[UTC]</recordGeneratedAt><recordGeneratedBy>OBU</recordGeneratedBy><sanitized>false</sanitized><logFileName>bsmLogDuringEvent.csv</logFileName><recordType>bsmLogDuringEvent</recordType><securityResultCode>success</securityResultCode><receivedMessageDetails><locationData><latitude>40.4740068</latitude><longitude>-104.9692033</longitude><elevation>1492.5</elevation><speed>0.24</speed><heading>246.3125</heading></locationData><rxSource/></receivedMessageDetails><encodings><encodings><elementName>root</elementName><elementType>Ieee1609Dot2Data</elementType><encodingRule>COER</encodingRule></encodings><encodings><elementName>unsecuredData</elementName><elementType>MessageFrame</elementType><encodingRule>UPER</encodingRule></encodings></encodings><bsmSource>EV</bsmSource></metadata><payload><dataType>MessageFrame</dataType><data><MessageFrame><messageId>20</messageId><value><BasicSafetyMessage><coreData><msgCnt>41</msgCnt><id>4B3AD218</id><secMark>14206</secMark><lat>404740068</lat><long>-1049692033</long><elev>14925</elev><accuracy><semiMajor>195</semiMajor><semiMinor>254</semiMinor><orientation>65535</orientation></accuracy><transmission><unavailable/></transmission><speed>12</speed><heading>19705</heading><angle>127</angle><accelSet><long>23</long><lat>0</lat><vert>0</vert><yaw>0</yaw></accelSet><brakes><wheelBrakes>10000</wheelBrakes><traction><unavailable/></traction><abs><unavailable/></abs><scs><unavailable/></scs><brakeBoost><unavailable/></brakeBoost><auxBrakes><unavailable/></auxBrakes></brakes><size><width>200</width><length>570</length></size></coreData><partII><PartIIcontent><partII-Id>0</partII-Id><partII-Value><VehicleSafetyExtensions><pathHistory><crumbData><PathHistoryPoint><latOffset>-141</latOffset><lonOffset>65</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>670</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-294</latOffset><lonOffset>297</lonOffset><elevationOffset>43</elevationOffset><timeOffset>3571</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-329</latOffset><lonOffset>170</lonOffset><elevationOffset>7</elevationOffset><timeOffset>5150</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-130</latOffset><lonOffset>-142</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>8071</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>129</latOffset><lonOffset>-634</lonOffset><elevationOffset>-75</elevationOffset><timeOffset>10170</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>173</latOffset><lonOffset>-588</lonOffset><elevationOffset>-62</elevationOffset><timeOffset>11560</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>77</latOffset><lonOffset>-645</lonOffset><elevationOffset>-66</elevationOffset><timeOffset>12570</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>29</latOffset><lonOffset>-500</lonOffset><elevationOffset>-42</elevationOffset><timeOffset>13750</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-45</latOffset><lonOffset>-123</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>15710</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-103</latOffset><lonOffset>-20</lonOffset><elevationOffset>-43</elevationOffset><timeOffset>16680</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-54</latOffset><lonOffset>198</lonOffset><elevationOffset>-36</elevationOffset><timeOffset>19130</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-193</latOffset><lonOffset>-294</lonOffset><elevationOffset>19</elevationOffset><timeOffset>22209</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-382</latOffset><lonOffset>-467</lonOffset><elevationOffset>28</elevationOffset><timeOffset>23300</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-390</latOffset><lonOffset>-364</lonOffset><elevationOffset>18</elevationOffset><timeOffset>24320</timeOffset></PathHistoryPoint><PathHistoryPoint><latOffset>-178</latOffset><lonOffset>-120</lonOffset><elevationOffset>-3</elevationOffset><timeOffset>26240</timeOffset></PathHistoryPoint></crumbData></pathHistory><pathPrediction><radiusOfCurve>32767</radiusOfCurve><confidence>0</confidence></pathPrediction></VehicleSafetyExtensions></partII-Value></PartIIcontent><PartIIcontent><partII-Id>1</partII-Id><partII-Value><SpecialVehicleExtensions><trailers><sspRights>0</sspRights><connection><pivotOffset>100</pivotOffset><pivotAngle>28800</pivotAngle><pivots><true/></pivots></connection><units><TrailerUnitDescription><isDolly><false/></isDolly><width>200</width><length>600</length><height>40</height><mass>128</mass><frontPivot><pivotOffset>100</pivotOffset><pivotAngle>0</pivotAngle><pivots><false/></pivots></frontPivot><positionOffset><x>0</x><y>0</y></positionOffset></TrailerUnitDescription></units></trailers></SpecialVehicleExtensions></partII-Value></PartIIcontent><PartIIcontent><partII-Id>2</partII-Id><partII-Value><SupplementalVehicleExtensions><classDetails><keyType>0</keyType><role><basicVehicle/></role><hpmsType><none/></hpmsType><fuelType>0</fuelType></classDetails><vehicleData><height>38</height></vehicleData><weatherProbe><airTemp>191</airTemp></weatherProbe></SupplementalVehicleExtensions></partII-Value></PartIIcontent></partII></BasicSafetyMessage></value></MessageFrame></data></payload></OdeAsn1Data>");
      } catch (XmlUtilsException e) {
         fail("XML parsing error:" + e);
      }

      J2735Bsm actualBsm = BsmBuilder.genericBsm(jsonBsm.findValue("BasicSafetyMessage"));

      //assertEquals("string", actualBsm.toJson());
      assertNotNull(actualBsm);
      String expected = "{\"coreData\":{\"msgCnt\":41,\"id\":\"4B3AD218\",\"secMark\":14206,\"position\":{\"latitude\":40.4740068,\"longitude\":-104.9692033,\"elevation\":1492.5},\"accelSet\":{\"accelLat\":0.00,\"accelLong\":0.23,\"accelVert\":0.00,\"accelYaw\":0.00},\"accuracy\":{\"semiMajor\":9.75,\"semiMinor\":12.70},\"transmission\":\"UNAVAILABLE\",\"speed\":0.24,\"heading\":246.3125,\"brakes\":{\"wheelBrakes\":{\"leftFront\":false,\"rightFront\":false,\"unavailable\":true,\"leftRear\":false,\"rightRear\":false},\"traction\":\"unavailable\",\"abs\":\"unavailable\",\"scs\":\"unavailable\",\"brakeBoost\":\"unavailable\",\"auxBrakes\":\"unavailable\"},\"size\":{\"width\":200,\"length\":570}},\"partII\":[{\"id\":\"VehicleSafetyExtensions\",\"value\":{\"pathHistory\":{\"crumbData\":[{\"elevationOffset\":-0.3,\"latOffset\":-0.0000141,\"lonOffset\":0.0000065,\"timeOffset\":6.70},{\"elevationOffset\":4.3,\"latOffset\":-0.0000294,\"lonOffset\":0.0000297,\"timeOffset\":35.71},{\"elevationOffset\":0.7,\"latOffset\":-0.0000329,\"lonOffset\":0.0000170,\"timeOffset\":51.50},{\"elevationOffset\":-0.3,\"latOffset\":-0.0000130,\"lonOffset\":-0.0000142,\"timeOffset\":80.71},{\"elevationOffset\":-7.5,\"latOffset\":0.0000129,\"lonOffset\":-0.0000634,\"timeOffset\":101.70},{\"elevationOffset\":-6.2,\"latOffset\":0.0000173,\"lonOffset\":-0.0000588,\"timeOffset\":115.60},{\"elevationOffset\":-6.6,\"latOffset\":0.0000077,\"lonOffset\":-0.0000645,\"timeOffset\":125.70},{\"elevationOffset\":-4.2,\"latOffset\":0.0000029,\"lonOffset\":-0.0000500,\"timeOffset\":137.50},{\"elevationOffset\":-4.3,\"latOffset\":-0.0000045,\"lonOffset\":-0.0000123,\"timeOffset\":157.10},{\"elevationOffset\":-4.3,\"latOffset\":-0.0000103,\"lonOffset\":-0.0000020,\"timeOffset\":166.80},{\"elevationOffset\":-3.6,\"latOffset\":-0.0000054,\"lonOffset\":0.0000198,\"timeOffset\":191.30},{\"elevationOffset\":1.9,\"latOffset\":-0.0000193,\"lonOffset\":-0.0000294,\"timeOffset\":222.09},{\"elevationOffset\":2.8,\"latOffset\":-0.0000382,\"lonOffset\":-0.0000467,\"timeOffset\":233.00},{\"elevationOffset\":1.8,\"latOffset\":-0.0000390,\"lonOffset\":-0.0000364,\"timeOffset\":243.20},{\"elevationOffset\":-0.3,\"latOffset\":-0.0000178,\"lonOffset\":-0.0000120,\"timeOffset\":262.40}]},\"pathPrediction\":{\"confidence\":0.0,\"radiusOfCurve\":0.0}}},{\"id\":\"SpecialVehicleExtensions\",\"value\":{\"trailers\":{\"connection\":{\"pivotOffset\":1.00,\"pivots\":false},\"sspRights\":0,\"units\":[{\"isDolly\":false,\"width\":200,\"length\":600,\"height\":2.00,\"mass\":64000,\"frontPivot\":{\"pivotOffset\":1.00,\"pivotAngle\":0.0000,\"pivots\":false},\"positionOffset\":{\"x\":0.00,\"y\":0.00},\"crumbData\":[]}]}}},{\"id\":\"SupplementalVehicleExtensions\",\"value\":{\"classDetails\":{\"fuelType\":\"unknownFuel\",\"hpmsType\":\"none\",\"keyType\":0,\"role\":\"basicVehicle\"},\"vehicleData\":{\"height\":1.90},\"weatherProbe\":{}}}]}";
      assertEquals(expected , actualBsm.toString());
   }

}
