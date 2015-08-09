
package com.wakacommerce.common.crossapp.service;

import java.util.List;

/**
 *
 * @ hui
 */
public interface CrossAppAdminAuthService {

    public String getRedirectUrlForSiteAuth(String forwardUrl, List<String> rolesToContrib);

    public String generateTokenForSiteAuth(Long adminUserId);

    public String generateTokenForSiteAuth(Long adminUserId, List<String> rolesToContrib);

}