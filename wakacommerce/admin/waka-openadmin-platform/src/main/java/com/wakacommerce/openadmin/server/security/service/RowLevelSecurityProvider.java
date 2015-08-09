  
package com.wakacommerce.openadmin.server.security.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.CriteriaTranslatorEventHandler;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.CriteriaTranslatorImpl;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.validation.GlobalValidationResult;
import com.wakacommerce.openadmin.server.service.persistence.validation.PropertyValidator;
import com.wakacommerce.openadmin.web.form.entity.DefaultEntityFormActions;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.service.FormBuilderServiceImpl;

/**
 *
 * @ hui
 */
public interface RowLevelSecurityProvider {

    public void addFetchRestrictions(AdminUser currentUser, String ceilingEntity, List<Predicate> restrictions, List<Order> sorts,
            Root entityRoot,
            CriteriaQuery criteria,
            CriteriaBuilder criteriaBuilder);

    public Class<Serializable> getFetchRestrictionRoot(AdminUser currentUser, Class<Serializable> ceilingEntity, List<FilterMapping> filterMappings);

    public boolean canUpdate(AdminUser currentUser, Entity entity);

    public boolean canRemove(AdminUser currentUser, Entity entity);

    public GlobalValidationResult validateUpdateRequest(AdminUser currentUser, Entity entity, PersistencePackage persistencePackage);

    public GlobalValidationResult validateRemoveRequest(AdminUser currentUser, Entity entity, PersistencePackage persistencePackage);

}
