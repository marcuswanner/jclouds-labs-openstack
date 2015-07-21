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

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.inject.Inject;

import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class CreateShareOptions implements MapBinder {
   public static final CreateShareOptions NONE = new CreateShareOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   protected String name;
   protected String description;
   protected String shareType;
   protected String availabilityZone;
   protected String snapshotId;
   protected String proto;
   protected String shareNetworkId;
   protected Integer size;
   protected Map<String, String> metadata = ImmutableMap.of();

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      Map<String, Object> image = Maps.newHashMap();
      image.putAll(postParams);
      if (name != null)
         image.put("name", name);
      if (description != null)
         image.put("description", description);
      if (shareType != null)
         image.put("share_type", shareType);
      if (availabilityZone != null)
         image.put("availability_zone", availabilityZone);
      if (snapshotId != null)
         image.put("snapshot_id", snapshotId);
      if (proto != null)
         image.put("share_proto", proto);
      if (shareNetworkId != null)
         image.put("share_network_id", shareNetworkId);
      if (size != null)
         image.put("size", size);
      if (!metadata.isEmpty())
         image.put("metadata", metadata);
      return jsonBinder.bindToRequest(request, ImmutableMap.of("share", image));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("CreateShare is a POST operation");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof CreateShareOptions)) return false;
      final CreateShareOptions other = CreateShareOptions.class.cast(object);
      return equal(shareType, other.shareType) && equal(availabilityZone, other.availabilityZone) && equal(snapshotId, other.snapshotId)
            && equal(name, other.name) && equal(description, other.description) && equal(metadata, other.metadata)
            && equal(proto, other.proto) && equal(shareNetworkId, other.shareNetworkId) && equal(size, other.size);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(shareType, availabilityZone, snapshotId, name, description, proto, shareNetworkId, size, metadata);
   }

   protected ToStringHelper string() {
      return toStringHelper("").add("shareType", shareType).add("availabilityZone", availabilityZone)
            .add("snapshotId", snapshotId).add("name", name).add("description", description).add("proto", proto)
            .add("shareNetworkId", shareNetworkId).add("size", size).add("metadata", metadata);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   /**
    * Custom cloud server metadata can also be supplied at launch time. This
    * metadata is stored in the API system where it is retrievable by querying
    * the API for server status. The maximum size of the metadata key and value
    * is each 255 bytes and the maximum number of key-value pairs that can be
    * supplied per share is 5.
    */
   public CreateShareOptions metadata(Map<String, String> metadata) {
      checkNotNull(metadata, "metadata");
      this.metadata = ImmutableMap.copyOf(metadata);
      return this;
   }

   /**
    * @param name The name of the Share
    */
   public CreateShareOptions name(String name) {
      this.name = name;
      return this;
   }

   /**
    * @param description A description of the Share
    */
   public CreateShareOptions description(String description) {
      this.description = description;
      return this;
   }

   /**
    * @see ShareTypeApi#list()
    *
    * @param shareType The type of Share to create
    */
   public CreateShareOptions shareType(String shareType) {
      this.shareType = shareType;
      return this;
   }

   /**
    * @param availabilityZone The optional availability zone in which to create a Share
    */
   public CreateShareOptions availabilityZone(String availabilityZone) {
      this.availabilityZone = availabilityZone;
      return this;
   }

   /**
    * @param snapshotId The optional snapshot from which to create a Share
    */
   public CreateShareOptions snapshotId(String snapshotId) {
      this.snapshotId = snapshotId;
      return this;
   }

   /**
    * @param proto The protocol to use when exporting the Share
    */
   public CreateShareOptions proto(String proto) {
      this.proto = proto;
      return this;
   }

   /**
    * @param shareNetworkId The share network from which to create a Share
    */
   public CreateShareOptions shareNetworkId(String shareNetworkId) {
      this.shareNetworkId = shareNetworkId;
      return this;
   }

   /**
    * @param size The share size in gigabytes
    */
   public CreateShareOptions size(Integer size) {
      this.size = size;
      return this;
   }

   public String getShareType() {
      return shareType;
   }

   public String getAvailabilityZone() {
      return availabilityZone;
   }

   public String getSnapshotId() {
      return snapshotId;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public String getProto() {
      return proto;
   }

   public String getShareNetworkId() {
      return shareNetworkId;
   }

   public Integer getSize() {
      return size;
   }

   public Map<String, String> getMetadata() {
      return metadata;
   }

   public static class Builder {
      /**
       * @see CreateShareOptions#getName()
       */
      public static CreateShareOptions name(String name) {
         return new CreateShareOptions().name(name);
      }
      /**
       * @see CreateShareOptions#getDescription()
       */
      public static CreateShareOptions description(String description) {
         return new CreateShareOptions().description(description);
      }

      /**
       * @see CreateShareOptions#getShareType()
       */
      public static CreateShareOptions shareType(String shareType) {
         return new CreateShareOptions().shareType(shareType);
      }

      /**
       * @see CreateShareOptions#getAvailabilityZone()
       */
      public static CreateShareOptions availabilityZone(String availabilityZone) {
         return new CreateShareOptions().availabilityZone(availabilityZone);
      }

      /**
       * @see CreateShareOptions#getSnapshotId()
       */
      public static CreateShareOptions snapshotId(String snapshotId) {
         return new CreateShareOptions().snapshotId(snapshotId);
      }

      /**
       * @see CreateShareOptions#getProto()
       */
      public static CreateShareOptions proto(String proto) {
         return new CreateShareOptions().proto(proto);
      }

      /**
       * @see CreateShareOptions#getShareNetworkId()
       */
      public static CreateShareOptions shareNetworkId(String shareNetworkId) {
         return new CreateShareOptions().shareNetworkId(shareNetworkId);
      }

      /**
       * @see CreateShareOptions#getSnapshotId()
       */
      public static CreateShareOptions size(Integer size) {
         return new CreateShareOptions().size(size);
      }

      /**
       * @see CreateShareOptions#getMetadata()
       */
      public static CreateShareOptions metadata(Map<String, String> metadata) {
         return new CreateShareOptions().metadata(metadata);
      }
   }

}
