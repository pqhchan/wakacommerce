
package com.wakacommerce.core.util.service;

import java.util.Map;

/**
 *
 * @ hui
 */
public interface ResourcePurgeService {

    void purgeCarts(Map<String, String> config);

    void purgeCustomers(final Map<String, String> config);

    Integer getPageSize();

    void setPageSize(Integer pageSize);
}
