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
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import org.jclouds.openstack.manila.v1.domain.ShareNetwork;
import org.jclouds.openstack.manila.v1.domain.Snapshot;
import org.jclouds.openstack.manila.v1.domain.Share;
import org.jclouds.openstack.manila.v1.domain.ShareType;
import org.jclouds.openstack.manila.v1.options.CreateShareNetworkOptions;
import org.jclouds.openstack.manila.v1.options.CreateSnapshotOptions;
import org.jclouds.openstack.manila.v1.options.CreateShareOptions;
import org.jclouds.openstack.manila.v1.options.CreateShareTypeOptions;
import org.jclouds.openstack.manila.v1.predicates.SnapshotPredicates;
import org.jclouds.openstack.manila.v1.predicates.SharePredicates;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

/**
 * Tests behavior of ShareApi
 */
@Test(groups = "live", testName = "ShareApiLiveTest", singleThreaded = true)
public class ShareAndSnapshotApiLiveTest extends org.jclouds.openstack.manila.v1.internal.BaseManilaApiLiveTest {
   private static final String name = System.getProperty("user.name").replace('.', '-').toLowerCase();

   private String region;

   private ShareApi shareApi;
   private SnapshotApi snapshotApi;
   private ShareTypeApi shareTypeApi;
   private ShareNetworkApi shareNetworkApi;

   private Share testShare;
   private Snapshot testSnapshot;
   private ShareType testShareType;
   private ShareNetwork testShareNetwork;

   @BeforeClass(groups = {"integration", "live"})
   @Override
   public void setup() {
      super.setup();
      region = Iterables.getLast(api.getConfiguredRegions(), "nova");
      shareApi = api.getShareApi(region);
      snapshotApi = api.getSnapshotApi(region);
      shareTypeApi = api.getShareTypeApi(region);
      shareNetworkApi = api.getShareNetworkApi(region);
   }

   @AfterClass(groups = { "integration", "live" })
   @Override
   protected void tearDown() {
      if (testSnapshot != null) {
         assertTrue(snapshotApi.delete(testSnapshot.getId()));
         assertTrue(SnapshotPredicates.awaitDeleted(snapshotApi).apply(testSnapshot));
      }

      if (testShare != null) {
         assertTrue(shareApi.delete(testShare.getId()));
         assertTrue(SharePredicates.awaitDeleted(shareApi).apply(testShare));
      }

      if (testShareType != null) {
         assertTrue(shareTypeApi.delete(testShareType.getId()));
      }

      if (testShareNetwork != null) {
         assertTrue(shareNetworkApi.delete(testShareNetwork.getId()));
      }

      super.tearDown();
   }

   public void testCreateShareType() {
      CreateShareTypeOptions options = CreateShareTypeOptions.Builder
              .name("testShareType")
              .extraSpecs(ImmutableMap.of("driver_handles_share_servers", "True"));
      testShareType = shareTypeApi.create(options);

      assertEquals(testShareType.getName(), "testShareType");
      assertEquals(testShareType.getExtraSpecs().get("driver_handles_share_servers"), "True");
   }

   public void testCreateShareNetwork() {
      CreateShareNetworkOptions options = CreateShareNetworkOptions.Builder
              .name("testShareNetwork")
              .novaNetId(System.getProperty("test.openstack-manila.nova-net-id"));
      testShareNetwork = shareNetworkApi.create(options);

      assertEquals(testShareNetwork.getName(), "testShareNetwork");
      assertEquals(testShareNetwork.getNovaNetId(), System.getProperty("test.openstack-manila.nova-net-id"));
   }

   @Test(dependsOnMethods = {"testCreateShareType", "testCreateShareNetwork"})
   public void testCreateShare() {
      CreateShareOptions options = CreateShareOptions.Builder
            .name(name)
            .shareNetworkId(testShareNetwork.getId())
            .description("description of test share")
            .shareType("testShareType");
      testShare = shareApi.create("NFS", 1, options);

      assertEquals(1, testShare.getSize());
      assertTrue(SharePredicates.awaitAvailable(shareApi).apply(testShare));
   }

   @Test(dependsOnMethods = "testCreateShare")
   public void testListShares() {
      List<? extends Share> shares = shareApi.list();
      assertNotNull(shares);
      boolean foundIt = false;
      for (Share vol : shares) {
         Share details = shareApi.get(vol.getId());
         assertNotNull(details);
         if (Objects.equal(details.getId(), testShare.getId())) {
            foundIt = true;
            break;
         }
      }
      assertTrue(foundIt, "Failed to find the share we created in list() response");
   }

   @Test(dependsOnMethods = "testCreateShare")
   public void testListSharesInDetail() {
      List<? extends Share> shares = shareApi.listInDetail();
      assertNotNull(shares);
      boolean foundIt = false;
      for (Share share : shares) {
         Share details = shareApi.get(share.getId());
         assertNotNull(details);
         assertNotNull(details.getId());
         assertNotNull(details.getCreated());
         assertTrue(details.getSize() > -1);

         assertEquals(details.getId(), share.getId());
         assertEquals(details.getSize(), share.getSize());
         assertEquals(details.getName(), share.getName());
         assertEquals(details.getDescription(), share.getDescription());
         assertEquals(details.getCreated(), share.getCreated());
         if (Objects.equal(details.getId(), testShare.getId())) {
            foundIt = true;
            break;
         }
      }
      assertTrue(foundIt, "Failed to find the share we previously created in listInDetail() response");
   }

   @Test(dependsOnMethods = "testCreateShare")
   public void testCreateSnapshot() {
      testSnapshot = snapshotApi.create(
               testShare.getId(),
               CreateSnapshotOptions.Builder.name("jclouds-live-test").description(
                        "jclouds live test snapshot").force());
      assertNotNull(testSnapshot);
      assertNotNull(testSnapshot.getId());
      assertNotNull(testSnapshot.getStatus());
      assertTrue(testSnapshot.getSize() > -1);
      assertNotNull(testSnapshot.getCreated());

      assertTrue(SnapshotPredicates.awaitAvailable(snapshotApi).apply(testSnapshot));
   }

   @Test(dependsOnMethods = "testCreateSnapshot")
   public void testListSnapshots() {
      List<? extends Snapshot> snapshots = snapshotApi.listInDetail();
      assertNotNull(snapshots);
      boolean foundIt = false;
      for (Snapshot snap : snapshots) {
         Snapshot details = snapshotApi.get(snap.getId());
         if (Objects.equal(snap.getShareId(), testShare.getId())) {
            foundIt = true;
         }
         assertNotNull(details);
         assertEquals(details.getId(), snap.getId());
         assertEquals(details.getShareId(), snap.getShareId());
      }
     assertTrue(foundIt, "Failed to find the snapshot we previously created in listSnapshots() response");
   }

   @Test(dependsOnMethods = "testCreateSnapshot")
   public void testListSnapshotsInDetail() {
      List<? extends Snapshot> snapshots = snapshotApi.listInDetail();
      assertNotNull(snapshots);
      boolean foundIt = false;
      for (Snapshot snap : snapshots) {
         Snapshot details = snapshotApi.get(snap.getId());
         if (Objects.equal(snap.getShareId(), testShare.getId())) {
            foundIt = true;
            assertSame(details, testSnapshot);
         }
         assertSame(details, snap);
      }

      assertTrue(foundIt, "Failed to find the snapshot we created in listSnapshotsInDetail() response");
   }

   private void assertSame(Snapshot a, Snapshot b) {
      assertNotNull(a);
      assertNotNull(b);
      assertEquals(a.getId(), b.getId());
      assertEquals(a.getDescription(), b.getDescription());
      assertEquals(a.getName(), b.getName());
      assertEquals(a.getShareId(), b.getShareId());
   }
}
