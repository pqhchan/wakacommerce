
package com.wakacommerce.common.crossapp.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @ hui
 */
public interface CrossAppAuthService {

    public static String AUTH_FROM_ADMIN_URL_PARAM = "blAuthToken";
    public static String AUTH_FROM_ADMIN_SESSION_VAR = "blAuthedFromAdmin";

    public void useSiteAuthToken(Long adminUserId, String token) throws IllegalArgumentException;

    public boolean isAuthedFromAdmin();

    public Long getCurrentAuthedAdminId();

    public boolean hasCsrPermission();

}