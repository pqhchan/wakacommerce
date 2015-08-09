  
package com.wakacommerce.openadmin.server.security.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.validation.GlobalValidationResult;

/**
 *
 * @ hui
 */
public class AbstractRowLevelSecurityProvider implements RowLevelSecurityProvider {

    @Override
    public void addFetchRestrictions(AdminUser currentUser, String ceilingEntity, List<Predicate> restrictions, List<Order> sorts, Root entityRoot, CriteriaQuery criteria, CriteriaBuilder criteriaBuilder) {
        // intentionally unimplemented
    }

    @Override
    public Class<Serializable> getFetchRestrictionRoot(AdminUser currentUser, Class<Serializable> ceilingEntity, List<FilterMapping> filterMappings) {
        return null;
    }

    @Override
    public boolean canUpdate(AdminUser currentUser, Entity entity) {
        return true;
    }

    @Override
    public boolean canRemove(AdminUser currentUser, Entity entity) {
        return true;
    }

    @Override
    public GlobalValidationResult validateUpdateRequest(AdminUser currentUser, Entity entity, PersistencePackage persistencePackage) {
        return new GlobalValidationResult(true);
    }

    @Override
    public GlobalValidationResult validateRemoveRequest(AdminUser currentUser, Entity entity, PersistencePackage persistencePackage) {
        return new GlobalValidationResult(true);
    }

}
