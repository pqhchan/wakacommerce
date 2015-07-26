
package com.wakacommerce.openadmin.server.security.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.util.dao.TypedQueryBuilder;
import com.wakacommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken;

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
@Repository("blForgotPasswordSecurityTokenDao")
public class ForgotPasswordSecurityTokenDaoImpl implements ForgotPasswordSecurityTokenDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public ForgotPasswordSecurityToken readToken(String token) {
        return (ForgotPasswordSecurityToken) em.find(entityConfiguration.lookupEntityClass("com.wakacommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken"), token);        
    }

    @Override
    public List<ForgotPasswordSecurityToken> readUnusedTokensByAdminUserId(Long adminUserId) {
        TypedQuery<ForgotPasswordSecurityToken> query = new TypedQueryBuilder<ForgotPasswordSecurityToken>(ForgotPasswordSecurityToken.class, "token")
                .addRestriction("token.adminUserId", "=", adminUserId)
                .addRestriction("token.tokenUsedFlag", "=", false)
                .toQuery(em);
        return query.getResultList();
    }

    @Override
    public ForgotPasswordSecurityToken saveToken(ForgotPasswordSecurityToken token) {
        return em.merge(token);
    }
}
