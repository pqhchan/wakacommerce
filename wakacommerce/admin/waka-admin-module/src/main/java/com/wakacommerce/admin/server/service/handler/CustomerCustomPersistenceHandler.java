
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;
import com.wakacommerce.profile.core.dao.RoleDao;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;

import java.util.Map;

import javax.annotation.Resource;

/**
 *jfischer
 */
@Component("blCustomerCustomPersistenceHandler")
public class CustomerCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(CustomerCustomPersistenceHandler.class);

    @Value("${use.email.for.site.login:true}")
    protected boolean useEmailForLogin;

    @Resource(name="blCustomerService")
    protected CustomerService customerService;
    
    @Resource(name="blRoleDao")
    protected RoleDao roleDao;

    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        return persistencePackage.getCeilingEntityFullyQualifiedClassname() != null && persistencePackage.getCeilingEntityFullyQualifiedClassname().equals(Customer.class.getName());
    }

    @Override
    public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
        return persistencePackage.getCeilingEntityFullyQualifiedClassname() != null && persistencePackage.getCeilingEntityFullyQualifiedClassname().equals(Customer.class.getName());
    }

    @Override
    public Boolean canHandleRemove(PersistencePackage persistencePackage) {
        return persistencePackage.getCeilingEntityFullyQualifiedClassname() != null && persistencePackage.getCeilingEntityFullyQualifiedClassname().equals(Customer.class.getName());
    }
    
    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity  = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Customer adminInstance = (Customer) Class.forName(entity.getType()[0]).newInstance();
            adminInstance.setId(customerService.findNextCustomerId());
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Customer.class.getName(), persistencePerspective);
            adminInstance = (Customer) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

            if (useEmailForLogin) {
                adminInstance.setUsername(adminInstance.getEmailAddress());
            }
            
            Entity errorEntity = validateUniqueUsername(entity, adminInstance);
            if (errorEntity != null) {
                return errorEntity;
            }
            
            adminInstance = dynamicEntityDao.merge(adminInstance);
            customerService.createRegisteredCustomerRoles(adminInstance);

            Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

            return adminEntity;
        } catch (Exception e) {
            LOG.error("Unable to execute persistence activity", e);
            throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
        }
    }

    @Override
    public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Customer.class.getName(), persistencePerspective);
            Object primaryKey = helper.getPrimaryKey(entity, adminProperties);
            Customer adminInstance = (Customer) dynamicEntityDao.retrieve(Class.forName(entity.getType()[0]), primaryKey);

            String passwordBefore = adminInstance.getPassword();
            adminInstance.setPassword(null);
            adminInstance = (Customer) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
            adminInstance.setPassword(passwordBefore);

            if (useEmailForLogin) {
                adminInstance.setUsername(adminInstance.getEmailAddress());
            }

            adminInstance = customerService.saveCustomer(adminInstance);
            Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

            return adminEntity;

        } catch (Exception e) {
            throw new ServiceException("Unable to update entity for " + entity.getType()[0], e);
        }
    }
    
    /**
     * Validates that a Customer does not have their username duplicated
     * 
     * @param entity
     * @param adminInstance
     * @return the original entity with a validation error on it or null if no validation failure
     */
    protected Entity validateUniqueUsername(Entity entity, Customer adminInstance) {
        if (customerService.readCustomerByUsername(adminInstance.getUsername()) != null) {
            entity.addValidationError("emailAddress", "nonUniqueUsernameError");
            return entity;
        }
        return null;
    }
    
    @Override
    public void remove(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
    	Entity entity = persistencePackage.getEntity();
    	try {
			roleDao.removeCustomerRolesByCustomerId(Long.parseLong(entity.findProperty("id").getValue()));
			helper.getCompatibleModule(OperationType.BASIC).remove(persistencePackage);
		} catch (Exception e) {
			LOG.error("Unable to execute persistence activity", e);
            throw new ServiceException("Unable to remove entity for " + entity.getType()[0], e);
		}
    }
}
