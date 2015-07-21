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
import java.util.Map;

import javax.inject.Named;

import org.jclouds.javax.annotation.Nullable;

import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableMap;

/**
 * An Openstack Manila Share.
 */
public class Share {

   public static enum Status {
      CREATING, AVAILABLE, ERROR, DELETING, UNRECOGNIZED;

      public String value() {
         return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
      }

      @Override
      public String toString() {
         return value();
      }

      public static Status fromValue(String status) {
         try {
            return valueOf(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, checkNotNull(status, "status")));
         }
         catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder().fromShare(this);
   }

   public static class Builder {

      protected String id;
      protected Share.Status status;
      protected int size;
      protected String zone;
      protected Date created;
      protected String shareType;
      protected String snapshotId;
      protected String name;
      protected String description;
      protected String export;
      protected String proto;
      protected String shareNetworkId;
      protected String host;
      protected Map<String, String> metadata = ImmutableMap.of();

      /**
       * @see Share#getId()
       */
      public Builder id(String id) {
         this.id = id;
         return self();
      }

      /**
       * @see Share#getStatus()
       */
      public Builder status(Share.Status status) {
         this.status = status;
         return self();
      }

      /**
       * @see Share#getSize()
       */
      public Builder size(int size) {
         this.size = size;
         return self();
      }

      /**
       * @see Share#getZone()
       */
      public Builder zone(String zone) {
         this.zone = zone;
         return self();
      }

      /**
       * @see Share#getCreated()
       */
      public Builder created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @see Share#getShareType()
       */
      public Builder shareType(String shareType) {
         this.shareType = shareType;
         return self();
      }

      /**
       * @see Share#getSnapshotId()
       */
      public Builder snapshotId(String snapshotId) {
         this.snapshotId = snapshotId;
         return self();
      }

      /**
       * @see Share#getName()
       */
      public Builder name(String name) {
         this.name = name;
         return self();
      }

      /**
       * @see Share#getDescription()
       */
      public Builder description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @see Share#getExport()
       */
      public Builder export(String export) {
         this.export = export;
         return self();
      }

      /**
       * @see Share#getProto()
       */
      public Builder proto(String proto) {
         this.proto = proto;
         return self();
      }

      /**
       * @see Share#getShareNetworkId()
       */
      public Builder shareNetworkId(String shareNetworkId) {
         this.shareNetworkId = shareNetworkId;
         return self();
      }

      /**
       * @see Share#getHost()
       */
      public Builder host(String host) {
         this.host = host;
         return self();
      }

      /**
       * @see Share#getMetadata()
       */
      public Builder metadata(Map<String, String> metadata) {
         this.metadata = ImmutableMap.copyOf(checkNotNull(metadata, "metadata"));
         return self();
      }

      public Share build() {
         return new Share(id, status, size, zone, created, shareType, snapshotId, name, description, export, proto, shareNetworkId, host, metadata);
      }

      public Builder fromShare(Share in) {
         return this
                  .id(in.getId())
                  .status(in.getStatus())
                  .size(in.getSize())
                  .zone(in.getZone())
                  .created(in.getCreated())
                  .shareType(in.getShareType())
                  .snapshotId(in.getSnapshotId())
                  .name(in.getName())
                  .description(in.getDescription())
                  .export(in.getExport())
                  .proto(in.getProto())
                  .shareNetworkId(in.getShareNetworkId())
                  .host(in.getHost())
                  .metadata(in.getMetadata());
      }

      protected Builder self() {
         return this;
      }
   }

   /**
    * Creates a dummy Share when you need a Share with just the shareId.
    * Several fields must be set in the returned Share:
    *
    * 1. status=Status.UNRECOGNIZED
    * 2. zone="nova"
    * 3. created=[The Date the method was called]
    */
   public static Share forId(String shareId) {
      return builder().id(shareId).status(Status.UNRECOGNIZED).zone("nova").created(new Date()).build();
   }

   private final String id;
   private final Share.Status status;
   private final int size;
   @Named("availability_zone")
   private final String zone;
   @Named("created_at")
   private final Date created;
   @Named("share_type")
   private final String shareType;
   @Named("snapshot_id")
   private final String snapshotId;
   @Named("name")
   private final String name;
   @Named("description")
   private final String description;
   @Named("export_location")
   private final String export;
   @Named("share_proto")
   private final String proto;
   @Named("share_network_id")
   private final String shareNetworkId;
   private final String host;
   private final Map<String, String> metadata;

   @ConstructorProperties({"id", "status", "size", "availability_zone", "created_at", "share_type", "snapshot_id", "name", "description", "export_location", "share_proto", "share_network_id", "host", "metadata"})
   protected Share(String id, Share.Status status, int size, String zone, Date created, @Nullable String shareType, @Nullable String snapshotId, @Nullable String name, @Nullable String description, @Nullable String export, @Nullable String proto, @Nullable String shareNetworkId, @Nullable String host, @Nullable Map<String, String> metadata) {
      this.id = checkNotNull(id, "id");
      this.status = status;
      this.size = size;
      this.zone = zone;
      this.created = created;
      this.shareType = shareType;
      this.snapshotId = snapshotId;
      this.name = name;
      this.description = description;
      this.export = export;
      this.proto = proto;
      this.shareNetworkId = shareNetworkId;
      this.host = host;
      this.metadata = metadata == null ? ImmutableMap.<String, String>of() : ImmutableMap.copyOf(metadata);
   }

   /**
    * @return the id of this share
    */
   public String getId() {
      return this.id;
   }

   /**
    * @return the status of this share
    */
   public Share.Status getStatus() {
      return this.status;
   }

   /**
    * @return the size in GB of this share
    */
   public int getSize() {
      return this.size;
   }

   /**
    * @return the availabilityZone containing this share
    */
   @Nullable
   public String getZone() {
      return this.zone;
   }

   /**
    * @return the time this share was created
    */
   public Date getCreated() {
      return this.created;
   }

   /**
    * @return the share type of this share
    */
   @Nullable
   public String getShareType() {
      return this.shareType;
   }

   /**
    * @return the share's snapshot ID
    */
   @Nullable
   public String getSnapshotId() {
      return this.snapshotId;
   }

   /**
    * @return the name of this share
    */
   @Nullable
   public String getName() {
      return this.name;
   }

   /**
    * @return the description of this share
    */
   @Nullable
   public String getDescription() {
      return this.description;
   }

   /**
    * @return the share's export location
    */
   public String getExport() {
      return this.export;
   }

   /**
    * @return the share's protocol
    */
   public String getProto() {
      return this.proto;
   }

   /**
    * @return the share's share network id
    */
   public String getShareNetworkId() {
      return this.shareNetworkId;
   }

   /**
    * @return the share's host
    */
   public String getHost() {
      return this.host;
   }

   public Map<String, String> getMetadata() {
      return this.metadata;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, status, size, zone, created, shareType, snapshotId, name, description, export, proto, shareNetworkId, host, metadata);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Share that = Share.class.cast(obj);
      return Objects.equal(this.id, that.id)
               && Objects.equal(this.status, that.status)
               && Objects.equal(this.size, that.size)
               && Objects.equal(this.zone, that.zone)
               && Objects.equal(this.created, that.created)
               && Objects.equal(this.shareType, that.shareType)
               && Objects.equal(this.snapshotId, that.snapshotId)
               && Objects.equal(this.name, that.name)
               && Objects.equal(this.description, that.description)
               && Objects.equal(this.export, that.export)
               && Objects.equal(this.proto, that.proto)
               && Objects.equal(this.shareNetworkId, that.shareNetworkId)
               && Objects.equal(this.host, that.host)
               && Objects.equal(this.metadata, that.metadata);

   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id).add("status", status).add("size", size).add("zone", zone).add("created", created)
            .add("shareType", shareType).add("snapshotId", snapshotId).add("name", name).add("description", description)
            .add("export", export).add("proto", proto).add("shareNetworkId", shareNetworkId).add("host", host)
            .add("metadata", metadata);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}
