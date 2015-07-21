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

import static com.google.common.base.Preconditions.checkNotNull;

public class ShareQuota {

   private final String id;
   private final int shares;
   private final int gigabytes;
   private final int snapshots;

   protected ShareQuota(String id, int shares, int gigabytes, int snapshots) {
      this.id = checkNotNull(id, "id");
      this.shares = shares;
      this.gigabytes = gigabytes;
      this.snapshots = snapshots;
   }

   /**
    * The id of the tenant this set of limits applies to
    */
   public String getId() {
      return this.id;
   }

   /**
    * The limit of the number of shares that can be created for the tenant
    */
   public int getShares() {
      return this.shares;
   }

   /**
    * The limit of the total size of all shares for the tenant
    */
   public int getGigabytes() {
      return this.gigabytes;
   }

   /**
    * The limit of the number of snapshots that can be used by the tenant
    */
   public int getSnapshots() {
      return this.snapshots;
   }


   @Override
   public int hashCode() {
      return Objects.hashCode(id, shares, gigabytes, snapshots);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      ShareQuota that = ShareQuota.class.cast(obj);
      return Objects.equal(this.id, that.id)
            && Objects.equal(this.shares, that.shares)
            && Objects.equal(this.gigabytes, that.gigabytes)
            && Objects.equal(this.snapshots, that.snapshots);
   }

   protected Objects.ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id).add("shares", shares).add("gigabytes", gigabytes).add("snapshots", snapshots);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromShareQuota(this);
   }

   public abstract static class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String id;
      protected int shares;
      protected int gigabytes;
      protected int snapshots;


      /**
       * @see ShareQuota#getId()
       */
      public T id(String id) {
         this.id = id;
         return self();
      }

      /**
       * @see ShareQuota#getShares()
       */
      public T shares(int shares) {
         this.shares = shares;
         return self();
      }

      /**
       * @see ShareQuota#getGigabytes()
       */
      public T gigabytes(int gigabytes) {
         this.gigabytes = gigabytes;
         return self();
      }

      /**
       * @see ShareQuota#getSnapshots()
       */
      public T snapshots(int snapshots) {
         this.snapshots = snapshots;
         return self();
      }


      public ShareQuota build() {
         return new ShareQuota(id, shares, gigabytes, snapshots);
      }

      public T fromShareQuota(ShareQuota in) {
         return this
               .id(in.getId())
               .shares(in.getShares())
               .gigabytes(in.getGigabytes())
               .snapshots(in.getSnapshots());

      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }
}
