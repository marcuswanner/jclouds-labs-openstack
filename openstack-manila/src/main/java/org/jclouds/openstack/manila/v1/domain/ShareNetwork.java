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
package org.jclouds.openstack.manila.v1.domain;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Named;
import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An Openstack Manila Share Network.
 */
public class ShareNetwork {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromShareNetwork(this);
   }

   public abstract static class Builder<T extends Builder<T>>  {
      protected abstract T self();

      protected String id;
      protected String name;
      protected String description;
      protected String neutronNetId;
      protected String neutronSubnetId;
      protected String novaNetId;

      /**
       * @see ShareNetwork#getId()
       */
      public T id(String id) {
         this.id = id;
         return self();
      }

      /**
       * @see ShareNetwork#getName()
       */
      public T name(String name) {
         this.name = name;
         return self();
      }

      /**
       * @see ShareNetwork#getDescription()
       */
      public T description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @see ShareNetwork#getNeutronNetId()
       */
      public T neutronNetId(String neutronNetId) {
         this.neutronNetId = neutronNetId;
         return self();
      }

      /**
       * @see ShareNetwork#getNeutronSubnetId()
       */
      public T neutronSubnetId(String neutronSubnetId) {
         this.neutronSubnetId = neutronSubnetId;
         return self();
      }

      /**
       * @see ShareNetwork#getNovaNetId()
       */
      public T novaNetId(String novaNetId) {
         this.novaNetId = novaNetId;
         return self();
      }

      public ShareNetwork build() {
         return new ShareNetwork(id, name, description, neutronNetId, neutronSubnetId, novaNetId);
      }

      public T fromShareNetwork(ShareNetwork in) {
         return this
                  .id(in.getId())
                  .name(in.getName())
                  .description(in.getDescription())
                  .neutronNetId(in.getNeutronNetId())
                  .neutronSubnetId(in.getNeutronSubnetId())
                  .novaNetId(in.getNovaNetId());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String id;
   private final String name;
   private final String description;
   @Named("neutron_net_id")
   private final String neutronNetId;
   @Named("neutron_subnet_id")
   private final String neutronSubnetId;
   @Named("nova_net_id")
   private final String novaNetId;

   @ConstructorProperties({
      "id", "name", "description", "neutron_net_id", "neutron_subnet_id", "nova_net_id"
   })
   protected ShareNetwork(String id, @Nullable String name, @Nullable String description, @Nullable String neutronNetId, @Nullable String neutronSubnetId, @Nullable String novaNetId) {
      this.id = checkNotNull(id, "id");
      this.name = name;
      this.description = description;
      this.neutronNetId = neutronNetId;
      this.neutronSubnetId = neutronSubnetId;
      this.novaNetId = novaNetId;
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public String getNeutronNetId() {
      return this.neutronNetId;
   }

   public String getNeutronSubnetId() {
      return this.neutronSubnetId;
   }

   public String getNovaNetId() {
      return this.novaNetId;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, name, description, neutronNetId, neutronSubnetId, novaNetId);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      ShareNetwork that = ShareNetwork.class.cast(obj);
      return Objects.equal(this.id, that.id)
              && Objects.equal(this.name, that.name)
              && Objects.equal(this.description, that.description)
              && Objects.equal(this.neutronNetId, that.neutronNetId)
              && Objects.equal(this.neutronSubnetId, that.neutronSubnetId)
              && Objects.equal(this.novaNetId, that.novaNetId);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id).add("name", name).add("description", description).add("neutronNetId", neutronNetId)
            .add("neutronSubnetId", neutronSubnetId).add("novaNetId", novaNetId);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}
