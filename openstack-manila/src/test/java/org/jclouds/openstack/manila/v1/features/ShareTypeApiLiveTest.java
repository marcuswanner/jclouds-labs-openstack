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

import static org.testng.Assert.assertNotNull;

import java.util.Set;

import org.jclouds.openstack.manila.v1.domain.ShareType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;

/**
 * Tests behavior of ShareTypeApi.
 */
@Test(groups = "live", testName = "ShareTypeApiLiveTest", singleThreaded = true)
public class ShareTypeApiLiveTest extends org.jclouds.openstack.manila.v1.internal.BaseManilaApiLiveTest {
   private ShareTypeApi shareTypeApi;
   private String region;

   @BeforeGroups(groups = {"integration", "live"})
   @Override
   public void setup() {
      super.setup();
      region = Iterables.getLast(api.getConfiguredRegions(), "nova");
      shareTypeApi = api.getShareTypeApi(region);
   }

   @AfterClass(groups = { "integration", "live" })
   @Override
   protected void tearDown() {
      super.tearDown();
   }

   public void testListAndGetShareTypes() {
      Set<? extends ShareType> shareTypes = shareTypeApi.list().toSet();
      assertNotNull(shareTypes);

      for (ShareType vt : shareTypes) {
         ShareType details = shareTypeApi.get(vt.getId());
         assertNotNull(details);
      }
   }
}
