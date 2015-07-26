
package com.wakacommerce.profile.core.dao;


import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.util.dao.TypedQueryBuilder;
import com.wakacommerce.profile.core.domain.CustomerForgotPasswordSecurityToken;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 
 *bpolster
 *
 */
@Repository("blCustomerForgotPasswordSecurityTokenDao")
public class CustomerForgotPasswordSecurityTokenDaoImpl implements CustomerForgotPasswordSecurityTokenDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public CustomerForgotPasswordSecurityToken readToken(String token) {
        return (CustomerForgotPasswordSecurityToken) em.find(entityConfiguration.lookupEntityClass("com.wakacommerce.profile.core.domain.CustomerForgotPasswordSecurityToken"), token);        
    }

    @Override
    public List<CustomerForgotPasswordSecurityToken> readUnusedTokensByCustomerId(Long customerId) {
        TypedQuery<CustomerForgotPasswordSecurityToken> query = new TypedQueryBuilder<CustomerForgotPasswordSecurityToken>(CustomerForgotPasswordSecurityToken.class, "token")
                .addRestriction("token.customerId", "=", customerId)
                .addRestriction("token.tokenUsedFlag", "=", false)
                .toQuery(em);
        return query.getResultList();
    }

    @Override
    public CustomerForgotPasswordSecurityToken saveToken(CustomerForgotPasswordSecurityToken token) {
        return em.merge(token);
    }
}
