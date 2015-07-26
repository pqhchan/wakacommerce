
package com.wakacommerce.common.config.service;

import java.util.List;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.config.service.type.ModuleConfigurationType;

public interface ModuleConfigurationService {

    public ModuleConfiguration findById(Long id);

    public ModuleConfiguration save(ModuleConfiguration config);

    public void delete(ModuleConfiguration config);

    public List<ModuleConfiguration> findActiveConfigurationsByType(ModuleConfigurationType type);

    public List<ModuleConfiguration> findAllConfigurationByType(ModuleConfigurationType type);

    public List<ModuleConfiguration> findByType(Class<? extends ModuleConfiguration> type);

}
