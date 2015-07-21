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
package org.jclouds.openstack.manila.v1.features;

import com.google.common.collect.FluentIterable;
import org.jclouds.Fallbacks;
import org.jclouds.Fallbacks.EmptyFluentIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.manila.v1.domain.ShareNetwork;
import org.jclouds.openstack.manila.v1.options.CreateShareNetworkOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.annotations.MapBinder;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * Provides access to the OpenStack Shared Filesystem (Manila) v1 Share Networks API.
 *
 */
@SkipEncoding({'/', '='})
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/share-networks")
public interface ShareNetworkApi {
   /**
    * Returns a summary list of ShareNetworks.
    *
    * @return The list of ShareNetworks
    */
   @Named("shareNetwork:list")
   @GET
   @SelectJson("share_networks")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends ShareNetwork> list();

   /**
    * Return data about the given ShareNetwork.
    *
    * @param shareNetworkId Id of the ShareNetwork
    * @return Details of a specific ShareNetwork
    */
   @Named("shareNetwork:get")
   @GET
   @Path("/{id}")
   @SelectJson("share_network")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   ShareNetwork get(@PathParam("id") String shareNetworkId);

   /**
    * Creates a new ShareNetwork
    *
    * @param shareNetworkId Id of the ShareNetwork
    * @param options See CreateShareNetworkOptions
    * @return The new ShareNetwork
    */
   @Named("shareNetwork:create")
   @POST
   @SelectJson("share_network")
   @Produces(MediaType.APPLICATION_JSON)
   @MapBinder(CreateShareNetworkOptions.class)
   ShareNetwork create(CreateShareNetworkOptions... options);

   /**
    * Delete a ShareNetwork. The ShareNetwork status must be Available or Error.
    *
    * @param shareNetworkId Id of the ShareNetwork
    * @return true if successful, false otherwise
    */
   @Named("shareNetwork:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String shareNetworkId);
}
