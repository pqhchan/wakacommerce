
package com.wakacommerce.common.config.service;

import com.wakacommerce.common.config.domain.SystemProperty;
import com.wakacommerce.common.config.service.type.SystemPropertyFieldType;


/**
 *
 * @ hui
 */
public interface SystemPropertiesService {

    String resolveSystemProperty(String name);

    String resolveSystemProperty(String name, String defaultValue);

    int resolveIntSystemProperty(String name);
    
    int resolveIntSystemProperty(String name, int defaultValue);

    boolean resolveBooleanSystemProperty(String name);

    boolean resolveBooleanSystemProperty(String name, boolean defaultValue);

    long resolveLongSystemProperty(String name);
    
    long resolveLongSystemProperty(String name, long defaultValue);

    public boolean isValueValidForType(String value, SystemPropertyFieldType type);

    public void removeFromCache(SystemProperty systemProperty);

    public SystemProperty findById(Long id);


}
