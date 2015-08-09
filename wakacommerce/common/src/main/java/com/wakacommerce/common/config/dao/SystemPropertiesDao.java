package com.wakacommerce.common.config.dao;

import java.util.List;

import com.wakacommerce.common.config.domain.SystemProperty;

public interface SystemPropertiesDao {

    public SystemProperty saveSystemProperty(SystemProperty systemProperty);

    public void deleteSystemProperty(SystemProperty systemProperty);

    public List<SystemProperty> readAllSystemProperties();

    public SystemProperty readSystemPropertyByName(String name);

    public SystemProperty createNewSystemProperty();

    public SystemProperty readById(Long id);

    public void removeFromCache(SystemProperty systemProperty);
}
