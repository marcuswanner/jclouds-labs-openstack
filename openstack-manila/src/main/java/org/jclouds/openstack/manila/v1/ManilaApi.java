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
package org.jclouds.openstack.manila.v1;

import java.io.Closeable;
import java.util.Set;

import org.jclouds.location.Region;
import org.jclouds.location.functions.RegionToEndpoint;
import org.jclouds.openstack.manila.v1.domain.ShareNetwork;
import org.jclouds.openstack.manila.v1.domain.Snapshot;
import org.jclouds.openstack.manila.v1.domain.Share;
import org.jclouds.openstack.manila.v1.domain.ShareType;
import org.jclouds.openstack.manila.v1.features.ShareApi;
import org.jclouds.openstack.manila.v1.features.ShareNetworkApi;
import org.jclouds.openstack.manila.v1.features.ShareTypeApi;
import org.jclouds.openstack.manila.v1.features.SnapshotApi;
import org.jclouds.openstack.manila.v1.features.QuotaApi;
import org.jclouds.openstack.v2_0.features.ExtensionApi;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.EndpointParam;

import com.google.inject.Provides;

/**
 * Provides access to OpenStack Shared Filesystem (Manila) v1 API.
 */
public interface ManilaApi extends Closeable {

   /**
    * @return the Region codes configured
    */
   @Provides
   @Region
   Set<String> getConfiguredRegions();

   /**
    * Provides access to {@link Extension} features.
    */
   @Delegate
   ExtensionApi getExtensionApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to {@link Share} features.
    */
   @Delegate
   ShareApi getShareApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to {@link ShareType} features.
    */
   @Delegate
   ShareTypeApi getShareTypeApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to {@link ShareNetwork} features.
    */
   @Delegate
   ShareNetworkApi getShareNetworkApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to {@link Snapshot} features.
    */
   @Delegate
   SnapshotApi getSnapshotApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to quota features.
    */
   @Delegate
   QuotaApi getQuotaApi(@EndpointParam(parser = RegionToEndpoint.class) String region);
}
