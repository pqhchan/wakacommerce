
package com.wakacommerce.openadmin.server.security.dao;

import java.util.List;

import com.wakacommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken;

/**
 *
 * @ hui
 */
public interface ForgotPasswordSecurityTokenDao {
    public ForgotPasswordSecurityToken readToken(String token);
    public List<ForgotPasswordSecurityToken> readUnusedTokensByAdminUserId(Long adminId);
    public ForgotPasswordSecurityToken saveToken(ForgotPasswordSecurityToken token);
}
