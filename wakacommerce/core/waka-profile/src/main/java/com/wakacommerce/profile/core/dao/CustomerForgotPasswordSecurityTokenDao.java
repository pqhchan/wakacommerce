
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerForgotPasswordSecurityToken;


/**
 * 
 * 
 *
 */
public interface CustomerForgotPasswordSecurityTokenDao {
    public CustomerForgotPasswordSecurityToken readToken(String token);
    public List<CustomerForgotPasswordSecurityToken> readUnusedTokensByCustomerId(Long customerId);
    public CustomerForgotPasswordSecurityToken saveToken(CustomerForgotPasswordSecurityToken token);
}
