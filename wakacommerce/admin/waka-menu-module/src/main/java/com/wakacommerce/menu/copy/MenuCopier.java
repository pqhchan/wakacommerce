package com.wakacommerce.menu.copy;



import com.wakacommerce.common.copy.MultiTenantCopier;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.menu.domain.MenuImpl;

/**
 *
 * @ hui
 */
public class MenuCopier extends MultiTenantCopier {

    @Override
    public void copyEntities(MultiTenantCopyContext context) throws Exception {
        Site fromSite = context.getFromSite();
        Catalog fromCatalog = context.getFromCatalog();

        copyEntitiesOfType(MenuImpl.class, fromSite, fromCatalog, context);

    }

}
