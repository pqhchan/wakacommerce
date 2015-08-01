package com.wakacommerce.common.config.dao;

import java.util.List;

import com.wakacommerce.common.config.domain.SystemProperty;

public interface SystemPropertiesDao {

    public SystemProperty saveSystemProperty(SystemProperty systemProperty);

    public void deleteSystemProperty(SystemProperty systemProperty);

    public List<SystemProperty> readAllSystemProperties();

    public SystemProperty readSystemPropertyByName(String name);

    public SystemProperty createNewSystemProperty();

    /**
     * Reads a SystemProperty by its internal database id
     * 
     * @param id
     * @return the {@link SystemProperty}
     */
    public SystemProperty readById(Long id);

    /**
     * Removes the SystemProperty from the null-capable cache.
     *
     * @param systemProperty the property instance
     */
    public void removeFromCache(SystemProperty systemProperty);
}
