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
package us.dot.its.jpo.ode.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;

public class OdeBsmData extends OdeData {

   private static final long serialVersionUID = 4944935387116447760L;

   public OdeBsmData() {
      super();
   }

   public OdeBsmData(OdeMsgMetadata metadata, OdeMsgPayload payload) {
      super(metadata, payload);
   }

   // @JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "payloadType")
   // @JsonSubTypes({
   //       @Type(value = OdeBsmMetadata.class, name = "us.dot.its.jpo.ode.model.OdeBsmPayload"),
   //       @Type(value = OdeMapMetadata.class, name = "us.dot.its.jpo.ode.model.OdeMapPayload"),
   //       @Type(value = OdeSpatMetadata.class, name = "us.dot.its.jpo.ode.model.OdeSpatPayload"),
   //       @Type(value = OdeSrmMetadata.class, name = "us.dot.its.jpo.ode.model.OdeSrmPayload"),
   //       @Type(value = OdeSsmMetadata.class, name = "us.dot.its.jpo.ode.model.OdeSsmPayload"),
   //       @Type(value = OdeTimMetadata.class, name = "us.dot.its.jpo.ode.model.OdeTimPayload")
   // })
   // @Override
   // public void setMetadata(OdeMsgMetadata metadata) {
   //    super.setMetadata(metadata);
   // }

   // @JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "dataType")
   // @JsonSubTypes({
   //       @Type(value = OdeBsmPayload.class, name = "us.dot.its.jpo.ode.plugin.j2735.J2735Bsm"),
   //       @Type(value = OdeMapPayload.class, name = "us.dot.its.jpo.ode.plugin.j2735.J2735MAP"),
   //       @Type(value = OdeSpatPayload.class, name = "us.dot.its.jpo.ode.plugin.j2735.J2735SPAT"),
   //       @Type(value = OdeSrmPayload.class, name = "us.dot.its.jpo.ode.plugin.j2735.J2735SRM"),
   //       @Type(value = OdeSsmPayload.class, name = "us.dot.its.jpo.ode.plugin.j2735.J2735SSM"),
   //       @Type(value = OdeTimPayload.class, name = "us.dot.its.jpo.ode.plugin.j2735.OdeTravelerInformationMessage")
   // })
   // @Override
   // public void setPayload(OdeMsgPayload payload) {
   //    super.setPayload(payload);
   // }

}
