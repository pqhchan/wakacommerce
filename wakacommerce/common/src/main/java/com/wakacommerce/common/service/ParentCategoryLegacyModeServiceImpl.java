
package com.wakacommerce.common.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *Jeff Fischer
 */
@Service("blParentCategoryLegacyModeService")
public class ParentCategoryLegacyModeServiceImpl implements ApplicationContextAware, ParentCategoryLegacyModeService {

    public static final String USE_LEGACY_DEFAULT_CATEGORY_MODE = "use.legacy.default.category.mode";
    private static ApplicationContext applicationContext;
    private static ParentCategoryLegacyModeService service;

    @Value("${" + USE_LEGACY_DEFAULT_CATEGORY_MODE + ":false}")
    protected boolean useLegacyDefaultCategoryMode = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean isLegacyMode() {
        return useLegacyDefaultCategoryMode;
    }

    public static ParentCategoryLegacyModeService getLegacyModeService() {
        if (applicationContext == null) {
            return null;
        }
        if (service == null) {
            service = (ParentCategoryLegacyModeService) applicationContext.getBean("blParentCategoryLegacyModeService");
        }
        return service;
    }
}
