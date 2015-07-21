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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.jclouds.date.DateService;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.manila.v1.domain.Share;
import org.jclouds.openstack.manila.v1.options.CreateShareOptions;
import org.jclouds.rest.ResourceNotFoundException;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

/**
 * Tests ShareApi Guice wiring and parsing
 */
@Test(groups = "unit", testName = "ShareApiExpectTest")
public class ShareApiExpectTest extends org.jclouds.openstack.manila.v1.internal.BaseManilaApiExpectTest {
   private DateService dateService = new SimpleDateFormatDateService();

   public void testListSharesInDetail() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares/detail");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/share_list_details.json")).build()
      ).getShareApi("RegionOne");

      Set<? extends Share> shares = api.listInDetail().toSet();
      assertEquals(shares, ImmutableSet.of(testShareDetailed()));
   }

   public void testListSharesInDetailFail() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares/detail");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(404).build()
      ).getShareApi("RegionOne");

      Set<? extends Share> shares = api.listInDetail().toSet();
      assertTrue(shares.isEmpty());
   }

   public void testCreateShare() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint)
                  .method("POST")
                  .payload(payloadFromResourceWithContentType("/share_create.json", MediaType.APPLICATION_JSON))
                  .build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/share_create_response.json")).build()
      ).getShareApi("RegionOne");

      CreateShareOptions options = CreateShareOptions.Builder
            .name("jclouds-test-share")
            .shareType("default")
            .description("description of test share");
      Share share = api.create("NFS", 1, options);
      assertEquals(share, testShareCreate());
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testCreateShareFail() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint)
               .endpoint(endpoint)
               .method("POST")
                  .payload(payloadFromResourceWithContentType("/share_create.json", MediaType.APPLICATION_JSON))
               .build(),
            HttpResponse.builder().statusCode(404).payload(payloadFromResource("/share_create_response.json")).build()
      ).getShareApi("RegionOne");

      CreateShareOptions options = CreateShareOptions.Builder
            .name("jclouds-test-share")
              .description("description of test share")
            .shareType("default");
      api.create("NFS", 1, options);
   }

   public void testGetShare() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares/60761c60-0f56-4499-b522-ff13e120af10");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/share_get.json")).build()
      ).getShareApi("RegionOne");

      Share share = api.get("60761c60-0f56-4499-b522-ff13e120af10");
      assertEquals(share, testShare());
      // double-check equals()
      assertEquals(share.getName(), "test");
      assertEquals(share.getZone(), "nova");
      assertEquals(share.getStatus(), Share.Status.AVAILABLE);
      assertEquals(share.getDescription(), "This is a test share");
   }

   public void testGetShareFail() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares/60761c60-0f56-4499-b522-ff13e120af10");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(404).build()
      ).getShareApi("RegionOne");

      assertNull(api.get("60761c60-0f56-4499-b522-ff13e120af10"));
   }

   public void testDeleteShare() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares/60761c60-0f56-4499-b522-ff13e120af10");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).method("DELETE").build(),
            HttpResponse.builder().statusCode(202).build()
      ).getShareApi("RegionOne");

      assertTrue(api.delete("60761c60-0f56-4499-b522-ff13e120af10"));
   }

   public void testDeleteShareFail() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/shares/60761c60-0f56-4499-b522-ff13e120af10");
      ShareApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).method("DELETE").build(),
            HttpResponse.builder().statusCode(404).build()
      ).getShareApi("RegionOne");

      assertFalse(api.delete("60761c60-0f56-4499-b522-ff13e120af10"));
   }

   protected Share testShareCreate() {
      return Share.builder()
            .id("60761c60-0f56-4499-b522-ff13e120af10")
            .proto("NFS")
            .size(1)
            .name("jclouds-test-share")
            .zone("nova")
            .status(Share.Status.CREATING)
            .shareType("default")
            .description("description of test share")
            .created(dateService.iso8601DateParse("2012-10-29T20:53:28.000000"))
            .build();
   }

   protected Share testShare() {
      return Share.builder()
            .id("60761c60-0f56-4499-b522-ff13e120af10")
            .proto("NFS")
            .size(1)
            .name("test")
            .zone("nova")
            .status(Share.Status.AVAILABLE)
            .shareType("default")
            .description("This is a test share")
            .created(dateService.iso8601DateParse("2012-10-29T20:53:28.000000"))
            .build();
   }

   protected Share testShareDetailed() {
      return Share.builder()
            .id("60761c60-0f56-4499-b522-ff13e120af10")
            .proto("NFS")
            .size(1)
            .name("test")
            .zone("nova")
            .status(Share.Status.AVAILABLE)
            .shareType("default")
            .description("This is a test share")
            .created(dateService.iso8601DateParse("2012-10-29T20:53:28.000000"))
            .build();
   }
}
