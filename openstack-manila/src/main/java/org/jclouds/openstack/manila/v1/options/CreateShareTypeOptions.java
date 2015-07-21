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

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

public class CreateShareTypeOptions implements MapBinder {
   public static final CreateShareTypeOptions NONE = new CreateShareTypeOptions();

   @Inject
   private BindToJsonPayload jsonBinder;

   private String name;
   private Map<String, String> extraSpecs = ImmutableMap.of();

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      Map<String, Object> data = Maps.newHashMap(postParams);
      data.put("name", name);
      if (!extraSpecs.isEmpty())
         data.put("extra_specs", extraSpecs);
      return jsonBinder.bindToRequest(request, ImmutableMap.of("share_type", data));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object toBind) {
      throw new IllegalStateException("CreateShareType is a POST operation");
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (!(object instanceof CreateShareTypeOptions)) return false;
      final CreateShareTypeOptions other = CreateShareTypeOptions.class.cast(object);
      return equal(name, other.name) && equal(extraSpecs, other.extraSpecs);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, extraSpecs);
   }

   protected ToStringHelper string() {
      return toStringHelper("").add("name", name).add("extraSpecs", extraSpecs);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public CreateShareTypeOptions name(String name) {
      this.name = name;
      return this;
   }

   public CreateShareTypeOptions extraSpecs(Map<String, String> extraSpecs) {
      this.extraSpecs = ImmutableMap.copyOf(extraSpecs);
      return this;
   }

   public String getName() {
      return name;
   }

   public Map<String, String> getExtraSpecs() {
      return extraSpecs;
   }

   public static class Builder {
      /**
       * @see CreateShareTypeOptions#getName()
       */
      public static CreateShareTypeOptions name(String name) {
         return new CreateShareTypeOptions().name(name);
      }

      /**
       * @see CreateShareTypeOptions#getExtraSpecs()
       */
      public static CreateShareTypeOptions extraSpecs(Map<String, String> extraSpecs) {
         return new CreateShareTypeOptions().extraSpecs(extraSpecs);
      }
   }
}
