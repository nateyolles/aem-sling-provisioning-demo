/*
 * Copyright 2018 Nathaniel Yolles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nateyolles.aem.slingprovisioningmodeldemo.core.services.impl;

import static org.apache.sling.api.resource.ResourceResolverFactory.SUBSERVICE;

import com.nateyolles.aem.slingprovisioningmodeldemo.core.services.JcrTitleService;

import java.util.Collections;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    service = JcrTitleService.class
)
public class JcrTitleServiceImpl implements JcrTitleService {

    private static final String SERVICE_ACCOUNT_NAME = "sample-service-name";
    private static final String RESOURCE_PATH = "/conf/global/settings/workflow";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public String getJcrTitle() {
        String title = null;

        Map<String, Object> authInfo = Collections.singletonMap(SUBSERVICE, SERVICE_ACCOUNT_NAME);

        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo)) {
            Resource resource = resourceResolver.getResource(RESOURCE_PATH);

            if (resource != null) {
                ValueMap properties = resource.getValueMap();
                title = properties.get("jcr:title", "jcr:title not found.");
            } else {
                title = "Resource not found.";
            }
        } catch (LoginException e) {
            title = "Could not get Service Resource Resolver.";
        }

        return title;
    }
}
