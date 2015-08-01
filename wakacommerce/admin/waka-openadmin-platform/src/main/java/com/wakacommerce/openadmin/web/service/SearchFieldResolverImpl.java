/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2014 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.wakacommerce.openadmin.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.server.service.AdminEntityService;

import javax.annotation.Resource;

/**
 * @see SearchFieldResolver
 * 
 */
@Service("blSearchFieldResolver")
public class SearchFieldResolverImpl implements SearchFieldResolver {
    protected static final Log LOG = LogFactory.getLog(SearchFieldResolverImpl.class);

    @Resource(name = "blAdminEntityService")
    protected AdminEntityService service;
    
    @Override
    public String resolveField(String className) throws ServiceException {
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(className);
        ClassMetadata md = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        
        if (md.getPMap().containsKey("name")) {
            return "name";
        }

        if (md.getPMap().containsKey("friendlyName")) {
            return "friendlyName";
        }

        if (md.getPMap().containsKey("templateName")) {
            return "templateName";
        }
        
        return "id";
    }


}
