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

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.EmptyFluentIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.manila.v1.domain.Share;
import org.jclouds.openstack.manila.v1.options.CreateShareOptions;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.SkipEncoding;

import com.google.common.collect.FluentIterable;

/**
 * Provides access to the Share API.
 *
 * This API strictly handles creating and managing Shares.
 *
 */
@SkipEncoding({'/', '='})
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/shares")
public interface ShareApi {
   /**
    * Returns a summary list of Shares.
    *
    * @return The list of Shares
    */
   @Named("share:list")
   @GET
   @SelectJson("shares")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends Share> list();

   /**
    * Returns a detailed list of Shares.
    *
    * @return The list of Shares
    */
   @Named("share:list")
   @GET
   @Path("/detail")
   @SelectJson("shares")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends Share> listInDetail();

   /**
    * Return data about the given Share.
    *
    * @param shareId Id of the Share
    * @return Details of a specific Share
    */
   @Named("share:get")
   @GET
   @Path("/{id}")
   @SelectJson("share")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Share get(@PathParam("id") String shareId);

   /**
    * Creates a new Share
    *
    * @param shareId Id of the Share
    * @param options See CreateShareOptions
    * @return The new Share
    */
   @Named("share:create")
   @POST
   @SelectJson("share")
   @Produces(MediaType.APPLICATION_JSON)
   @MapBinder(CreateShareOptions.class)
   Share create(@PayloadParam("share_proto") String proto, @PayloadParam("size") int sizeGB, CreateShareOptions... options);

   /**
    * Delete a Share. The Share status must be Available or Error.
    *
    * @param shareId Id of the Share
    * @return true if successful, false otherwise
    */
   @Named("share:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String shareId);
}
