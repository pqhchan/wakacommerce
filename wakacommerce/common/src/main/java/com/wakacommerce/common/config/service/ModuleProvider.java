
package com.wakacommerce.common.config.service;

import com.wakacommerce.common.config.domain.ModuleConfiguration;

public interface ModuleProvider {

    public boolean canRespond(ModuleConfiguration config);


}
