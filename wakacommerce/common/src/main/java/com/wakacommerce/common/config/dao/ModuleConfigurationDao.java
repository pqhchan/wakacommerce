package com.wakacommerce.common.config.dao;

import java.util.List;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.config.service.type.ModuleConfigurationType;

public interface ModuleConfigurationDao {

    public ModuleConfiguration readById(Long id);

    public ModuleConfiguration save(ModuleConfiguration config);

    public void delete(ModuleConfiguration config);

    public List<ModuleConfiguration> readAllByType(ModuleConfigurationType type);

    public List<ModuleConfiguration> readActiveByType(ModuleConfigurationType type);

    public List<ModuleConfiguration> readByType(Class<? extends ModuleConfiguration> type);

    public Long getCurrentDateResolution();

    public void setCurrentDateResolution(Long currentDateResolution);
}
