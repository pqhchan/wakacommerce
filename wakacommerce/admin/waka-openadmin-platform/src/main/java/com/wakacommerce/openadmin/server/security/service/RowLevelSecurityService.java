  
package com.wakacommerce.openadmin.server.security.service;

import java.util.List;

/**
 *
 * @ hui
 */
public interface RowLevelSecurityService extends RowLevelSecurityProvider {

    public List<RowLevelSecurityProvider> getProviders();
}
