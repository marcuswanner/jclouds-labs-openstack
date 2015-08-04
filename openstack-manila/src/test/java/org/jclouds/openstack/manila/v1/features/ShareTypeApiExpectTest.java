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
import static org.testng.Assert.assertNull;

import java.net.URI;
import java.util.List;
import java.util.Arrays;

import org.jclouds.date.DateService;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.manila.v1.domain.ShareType;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Tests Guice wiring and parsing of ShareTypeApi
 */
@Test(groups = "unit", testName = "ShareTypeApiExpectTest")
public class ShareTypeApiExpectTest extends org.jclouds.openstack.manila.v1.internal.BaseManilaApiExpectTest {
   private DateService dateService = new SimpleDateFormatDateService();

   public void testListShareTypes() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/types");
      ShareTypeApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/share_type_list_simple.json")).build()
      ).getShareTypeApi("RegionOne");

      List<? extends ShareType> types = api.list();
      assertEquals(types, Arrays.asList(testShareType()));
   }

   public void testGetShareType() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/types/1");
      ShareTypeApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/share_type_get.json")).build()
      ).getShareTypeApi("RegionOne");

      ShareType type = api.get("1");
      assertEquals(type, testShareType());
   }

   public void testGetShareTypeFailNotFound() {
      URI endpoint = URI.create("http://172.16.0.1:8786/v1/50cdb4c60374463198695d9f798fa34d/types/X");
      ShareTypeApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(404).build()
      ).getShareTypeApi("RegionOne");

      assertNull(api.get("X"));
   }

   public ShareType testShareType() {
      return ShareType.builder()
            .id("1")
            .name("jclouds-test-1")
            .extraSpecs(ImmutableMap.of("test", "value1", "test1", "wibble"))
            .build();
   }
}
