
package com.wakacommerce.common.extensibility.context.merge;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @ hui
 */
@Component("blMultiTenantMergeBeanStatusProvider")
public class MultiTenantMergeBeanStatusProvider implements MergeBeanStatusProvider {

    @Override
    public boolean isProcessingEnabled(Object bean, String beanName, ApplicationContext appCtx) {
        return appCtx.containsBean("blMultiTenantFilterClassTransformer");
    }

}
