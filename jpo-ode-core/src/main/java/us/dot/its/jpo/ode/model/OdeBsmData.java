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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;

public class OdeBsmData extends OdeData {

   private static final long serialVersionUID = 4944935387116447760L;

   public OdeBsmData() {
      super();
   }

   public OdeBsmData(OdeMsgMetadata metadata, OdeMsgPayload payload) {
      super(metadata, payload);
   }

  

   @JsonTypeInfo(use = Id.CLASS, defaultImpl=OdeBsmMetadata.class)
   @Override
   public void setMetadata(OdeMsgMetadata metadata) {
      super.setMetadata(metadata);
   }

   @JsonTypeInfo(use = Id.CLASS, defaultImpl=OdeBsmPayload.class)
   @Override
   public void setPayload(OdeMsgPayload payload) {
      super.setPayload(payload);
   }

}
