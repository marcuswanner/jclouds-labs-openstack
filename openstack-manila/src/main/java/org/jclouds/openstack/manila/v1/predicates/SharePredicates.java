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
package org.jclouds.openstack.manila.v1.predicates;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.jclouds.util.Predicates2.retry;

import org.jclouds.openstack.manila.v1.domain.Share;
import org.jclouds.openstack.manila.v1.domain.Share.Status;
import org.jclouds.openstack.manila.v1.features.ShareApi;

import com.google.common.base.Predicate;

/**
 * Tests to see if share has reached status. This class is most useful when paired with a RetryablePredicate as
 * in the code below. This class can be used to block execution until the Share status has reached a desired state.
 * This is useful when your Share needs to be 100% ready before you can continue with execution.
 *
 * <pre>
 * {@code
 * Share share = shareApi.create(100);
 *
 * RetryablePredicate<String> awaitAvailable = RetryablePredicate.create(
 *    SharePredicates.available(shareApi), 600, 10, 10, TimeUnit.SECONDS);
 *
 * if (!awaitAvailable.apply(share.getId())) {
 *    throw new TimeoutException("Timeout on share: " + share);
 * }
 * }
 * </pre>
 *
 * You can also use the static convenience methods as so.
 *
 * <pre>
 * {@code
 * Share share = shareApi.create(100);
 *
 * if (!SharePredicates.awaitAvailable(shareApi).apply(share.getId())) {
 *    throw new TimeoutException("Timeout on share: " + share);
 * }
 * }
 * </pre>
 */
public class SharePredicates {
   /**
    * Wait until a Share is Available.
    *
    * @param shareApi The ShareApi in the region where your Share resides.
    * @return RetryablePredicate That will check the status every 5 seconds for a maxiumum of 5 minutes.
    */
   public static Predicate<Share> awaitAvailable(ShareApi shareApi) {
      StatusUpdatedPredicate statusPredicate = new StatusUpdatedPredicate(shareApi, Share.Status.AVAILABLE);
      return retry(statusPredicate, 300, 5, 5, SECONDS);
   }

   /**
    * Wait until a Share no longer exists.
    *
    * @param shareApi The ShareApi in the region where your Share resides.
    * @return RetryablePredicate That will check the whether the Share exists
    * every 5 seconds for a maxiumum of 5 minutes.
    */
   public static Predicate<Share> awaitDeleted(ShareApi shareApi) {
      DeletedPredicate deletedPredicate = new DeletedPredicate(shareApi);
      return retry(deletedPredicate, 300, 5, 5, SECONDS);
   }

   public static Predicate<Share> awaitStatus(
         ShareApi shareApi, Share.Status status, long maxWaitInSec, long periodInSec) {
      StatusUpdatedPredicate statusPredicate = new StatusUpdatedPredicate(shareApi, status);
      return retry(statusPredicate, maxWaitInSec, periodInSec, periodInSec, SECONDS);
   }

   private static class StatusUpdatedPredicate implements Predicate<Share> {
      private ShareApi shareApi;
      private Status status;

      public StatusUpdatedPredicate(ShareApi shareApi, Share.Status status) {
         this.shareApi = checkNotNull(shareApi, "shareApi must be defined");
         this.status = checkNotNull(status, "status must be defined");
      }

      /**
       * @return boolean Return true when the share reaches status, false otherwise
       * @throws IllegalStateException if the share reaches error status (non-recoverable)
       */
      @Override
      public boolean apply(Share share) throws IllegalStateException {
         checkNotNull(share, "share must be defined");
         Share shareUpdated = shareApi.get(share.getId());
         checkNotNull(shareUpdated, "Share %s not found.", share.getId());
         if (!status.equals(Status.ERROR) && shareUpdated.getStatus().equals(Status.ERROR)) {
            throw new IllegalStateException("share is in error state");
         }
         return status.equals(shareUpdated.getStatus());
      }
   }

   private static class DeletedPredicate implements Predicate<Share> {
      private ShareApi shareApi;

      public DeletedPredicate(ShareApi shareApi) {
         this.shareApi = checkNotNull(shareApi, "shareApi must be defined");
      }

      /**
       * @return boolean Return true when the snapshot is deleted, false otherwise
       */
      @Override
      public boolean apply(Share share) {
         checkNotNull(share, "share must be defined");

         return shareApi.get(share.getId()) == null;
      }
   }
}
