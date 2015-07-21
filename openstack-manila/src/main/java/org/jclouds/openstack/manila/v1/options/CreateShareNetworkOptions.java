/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.manila.v1.options;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

public class CreateShareNetworkOptions implements MapBinder {
   public static final CreateShareNetworkOptions NONE = new CreateShareNetworkOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   private String name;
   private String description;
   private String neutronNetId;
   private String neutronSubnetId;
   private String novaNetId;

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      Map<String, Object> data = Maps.newHashMap(postParams);
      data.put("name", name);
      data.put("description", description);
      data.put("neutron_net_id", neutronNetId);
      data.put("neutron_subnet_id", neutronSubnetId);
      data.put("nova_net_id", novaNetId);
      return jsonBinder.bindToRequest(request, ImmutableMap.of("share_network", data));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("CreateShareNetwork is a POST operation");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof CreateShareNetworkOptions)) return false;
      final CreateShareNetworkOptions other = CreateShareNetworkOptions.class.cast(object);
      return equal(name, other.name) && equal(description, other.description) && equal(neutronNetId, other.neutronNetId)
              && equal(neutronSubnetId, other.neutronSubnetId) && equal(novaNetId, other.novaNetId);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, description, neutronNetId, neutronSubnetId, novaNetId);
   }

   protected ToStringHelper string() {
      return toStringHelper("").add("name", name).add("description", description).add("neutronNetId", neutronNetId)
              .add("neutronSubnetId", neutronSubnetId).add("novaNetId", novaNetId);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public CreateShareNetworkOptions name(String name) {
      this.name = name;
      return this;
   }

   public CreateShareNetworkOptions description(String description) {
      this.description = description;
      return this;
   }

   public CreateShareNetworkOptions neutronNetId(String neutronNetId) {
      this.neutronNetId = neutronNetId;
      return this;
   }

   public CreateShareNetworkOptions neutronSubnetId(String neutronSubnetId) {
      this.neutronSubnetId = neutronSubnetId;
      return this;
   }

   public CreateShareNetworkOptions novaNetId(String novaNetId) {
      this.novaNetId = novaNetId;
      return this;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public String getNeutronNetId() {
      return neutronNetId;
   }

   public String getNeutronSubnetId() {
      return neutronSubnetId;
   }

   public String getNovaNetId() {
      return novaNetId;
   }

   public static class Builder {
      /**
       * @see CreateShareNetworkOptions#getName()
       */
      public static CreateShareNetworkOptions name(String name) {
         return new CreateShareNetworkOptions().name(name);
      }

      /**
       * @see CreateShareNetworkOptions#getDescription()
       */
      public static CreateShareNetworkOptions description(String description) {
         return new CreateShareNetworkOptions().description(description);
      }

      /**
       * @see CreateShareNetworkOptions#getNeutronNetId()
       */
      public static CreateShareNetworkOptions neutronNetId(String neutronNetId) {
         return new CreateShareNetworkOptions().neutronNetId(neutronNetId);
      }

      /**
       * @see CreateShareNetworkOptions#getNeutronSubnetId()
       */
      public static CreateShareNetworkOptions neutronSubnetId(String neutronSubnetId) {
         return new CreateShareNetworkOptions().neutronSubnetId(neutronSubnetId);
      }

      /**
       * @see CreateShareNetworkOptions#getNovaNetId()
       */
      public static CreateShareNetworkOptions novaNetId(String novaNetId) {
         return new CreateShareNetworkOptions().novaNetId(novaNetId);
      }

   }
}
