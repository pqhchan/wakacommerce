package com.wakacommerce.menu.copy;
/*
 * #%L
 * BroadleafCommerce Pricelist
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


import com.wakacommerce.common.copy.MultiTenantCopier;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.menu.domain.MenuImpl;

/**
 * Clone menu entities
 *
 * 
 */
public class MenuCopier extends MultiTenantCopier {

    @Override
    public void copyEntities(MultiTenantCopyContext context) throws Exception {
        Site fromSite = context.getFromSite();
        Catalog fromCatalog = context.getFromCatalog();

        copyEntitiesOfType(MenuImpl.class, fromSite, fromCatalog, context);

    }

}
