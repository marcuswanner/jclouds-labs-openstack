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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks;
import org.jclouds.Fallbacks.EmptyListOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.manila.v1.domain.ShareType;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.manila.v1.options.CreateShareTypeOptions;

import java.util.List;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.SkipEncoding;

/**
 * Provides access to the OpenStack Shared Filesystem (Manila) v1 Share Types API.
 *
 */
@SkipEncoding({'/', '='})
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/types")
public interface ShareTypeApi {
   /**
    * Returns a summary list of ShareTypes.
    *
    * @return The list of ShareTypes
    */
   @Named("shareType:list")
   @GET
   @SelectJson("share_types")
   @Fallback(EmptyListOnNotFoundOr404.class)
   List<ShareType> list();

   /**
    * Return data about the given ShareType.
    *
    * @param shareTypeId Id of the ShareType
    * @return Details of a specific ShareType
    */
   @Named("shareType:get")
   @GET
   @Path("/{id}")
   @SelectJson("share_type")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   ShareType get(@PathParam("id") String shareTypeId);

   /**
    * Creates a new ShareType
    *
    * @param shareTypeId Id of the ShareType
    * @param options See CreateShareTypeOptions
    * @return The new ShareType
    */
   @Named("shareType:create")
   @POST
   @SelectJson("share_type")
   @Produces(MediaType.APPLICATION_JSON)
   ShareType create(CreateShareTypeOptions... options);

   /**
    * Delete a ShareType. The ShareType status must be Available or Error.
    *
    * @param shareTypeId Id of the ShareType
    * @return true if successful, false otherwise
    */
   @Named("shareType:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String shareTypeId);
}
