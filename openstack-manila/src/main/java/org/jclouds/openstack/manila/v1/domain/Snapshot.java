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

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;
import java.util.Date;

import javax.inject.Named;

import org.jclouds.javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * An Openstack Manila Share Snapshot.
 */
public class Snapshot {

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder().fromSnapshot(this);
   }

   public static class Builder {

      protected String id;
      protected String shareId;
      protected Share.Status status;
      protected int size;
      protected Date created;
      protected String name;
      protected String description;
      protected String export;
      protected String proto;

      /**
       * @see Snapshot#getId()
       */
      public Builder id(String id) {
         this.id = id;
         return self();
      }

      /**
       * @see Snapshot#getShareId()
       */
      public Builder shareId(String shareId) {
         this.shareId = shareId;
         return self();
      }

      /**
       * @see Snapshot#getStatus()
       */
      public Builder status(Share.Status status) {
         this.status = status;
         return self();
      }

      /**
       * @see Snapshot#getSize()
       */
      public Builder size(int size) {
         this.size = size;
         return self();
      }

      /**
       * @see Snapshot#getCreated()
       */
      public Builder created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @see Snapshot#getName()
       */
      public Builder name(String name) {
         this.name = name;
         return self();
      }

      /**
       * @see Snapshot#getDescription()
       */
      public Builder description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @see Snapshot#getExport()
       */
      public Builder export(String export) {
         this.export = export;
         return self();
      }

      /**
       * @see Snapshot#getProto()
       */
      public Builder proto(String proto) {
         this.proto = proto;
         return self();
      }

      public Snapshot build() {
         return new Snapshot(id, shareId, status, size, created, name, description, export, proto);
      }

      public Builder fromSnapshot(Snapshot in) {
         return this
                  .id(in.getId())
                  .shareId(in.getShareId())
                  .status(in.getStatus())
                  .size(in.getSize())
                  .created(in.getCreated())
                  .name(in.getName())
                  .description(in.getDescription())
                  .export(in.getExport())
                  .proto(in.getProto());
      }

      protected Builder self() {
         return this;
      }
   }


   private final String id;
   @Named("share_id")
   private final String shareId;
   private final Share.Status status;
   @Named("share_size")
   private final int size;
   @Named("created_at")
   private final Date created;
   private final String name;
   private final String description;
   @Named("export_location")
   private final String export;
   @Named("share_proto")
   private final String proto;

   @ConstructorProperties({"id", "share_id", "status", "share_size", "created_at", "name", "description", "export", "proto"})
   protected Snapshot(String id, String shareId, Share.Status status, int size, @Nullable Date created, @Nullable String name, @Nullable String description, @Nullable String export, @Nullable String proto) {
      this.id = checkNotNull(id, "id");
      this.shareId = checkNotNull(shareId, "shareId");
      this.status = checkNotNull(status, "status");
      this.size = size;
      this.created = created;
      this.name = name;
      this.description = description;
      this.export = export;
      this.proto = proto;
   }

   /**
    * @return The id of this snapshot
    */
   public String getId() {
      return this.id;
   }

   /**
    * @return The id of the Share this snapshot was taken from
    */
   public String getShareId() {
      return this.shareId;
   }

   /**
    * @return The status of this snapshot
    */
   public Share.Status getStatus() {
      return this.status;
   }

   /**
    * @return The size in GB of the share this snapshot was taken from
    */
   public int getSize() {
      return this.size;
   }

   /**
    * @return The data the snapshot was taken
    */
   @Nullable
   public Date getCreated() {
      return this.created;
   }

   /**
    * @return The name of this snapshot - as displayed in the openstack console
    */
   @Nullable
   public String getName() {
      return this.name;
   }

   /**
    * @return The description of this snapshot - as displayed in the openstack console
    */
   @Nullable
   public String getDescription() {
      return this.description;
   }

   /**
    * @return The export location of the share this snapshot was taken from
    */
   @Nullable
   public String getExport() {
      return this.export;
   }

   /**
    * @return The share protocol of the share this snapshot was taken from
    */
   @Nullable
   public String getProto() {
      return this.proto;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, shareId, status, size, created, name, description, export, proto);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Snapshot that = Snapshot.class.cast(obj);
      return Objects.equal(this.id, that.id)
               && Objects.equal(this.shareId, that.shareId)
               && Objects.equal(this.status, that.status)
               && Objects.equal(this.size, that.size)
               && Objects.equal(this.created, that.created)
               && Objects.equal(this.name, that.name)
               && Objects.equal(this.description, that.description)
               && Objects.equal(this.export, that.export)
               && Objects.equal(this.proto, that.proto);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id).add("shareId", shareId).add("status", status).add("size", size).add("created", created).add("name", name).add("description", description).add("export", export).add("proto", proto);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}
