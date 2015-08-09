
package com.wakacommerce.common.extension;

import com.wakacommerce.common.site.domain.SiteImpl;
import com.wakacommerce.common.web.WakaRequestContext;

/**
 *
 * @ hui
 */
public interface NativeMethodEntityExtensionHandler<T> extends ExtensionHandler {

    public void contributeClone(T from, T to);

    public void contributeEquals(T original, T test, ExtensionResultHolder<Boolean> result);

    public void contributeHashCode(T entity, int precomputedHashCode, ExtensionResultHolder<Integer> result);

}
