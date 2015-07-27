
package com.wakacommerce.admin.web.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.openadmin.server.service.JSCompatibilityHelper;
import com.wakacommerce.openadmin.web.controller.AbstractAdminTranslationControllerExtensionHandler;
import com.wakacommerce.openadmin.web.controller.AdminTranslationControllerExtensionManager;
import com.wakacommerce.openadmin.web.form.TranslationForm;

/**
 *Andre Azzolini (apazzolini)
 */
@Component("blAdminProductTranslationExtensionHandler")
public class AdminProductTranslationExtensionHandler extends AbstractAdminTranslationControllerExtensionHandler {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Resource(name = "blAdminTranslationControllerExtensionManager")
    protected AdminTranslationControllerExtensionManager extensionManager;

    @PostConstruct
    public void init() {
        if (isEnabled()) {
            extensionManager.registerHandler(this);
        }
    }
    
    /**
     * If we are trying to translate a field on Product that starts with "defaultSku.", we really want to associate the
     * translation with Sku, its associated id, and the property name without "defaultSku."
     */
    @Override
    public ExtensionResultStatusType applyTransformation(TranslationForm form) {
        String defaultSkuPrefix = "defaultSku.";
        String unencodedPropertyName = JSCompatibilityHelper.unencode(form.getPropertyName());
        if (form.getCeilingEntity().equals(Product.class.getName()) && unencodedPropertyName.startsWith(defaultSkuPrefix)) {
            Product p = catalogService.findProductById(Long.parseLong(form.getEntityId()));
            form.setCeilingEntity(Sku.class.getName());
            form.setEntityId(String.valueOf(p.getDefaultSku().getId()));
            form.setPropertyName(unencodedPropertyName.substring(defaultSkuPrefix.length()));
        }
        
        return ExtensionResultStatusType.HANDLED;
    }
    
}
