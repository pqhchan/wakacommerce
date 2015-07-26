/*
 * #%L
 * BroadleafCommerce Admin Module
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.security.util.PasswordReset;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;

import javax.annotation.Resource;

/**
 * 
 *jfischer
 *
 */
@Component("blCustomerPasswordCustomPersistenceHandler")
public class CustomerPasswordCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
    
    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    @Override
    public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
        String[] customCriteria = persistencePackage.getCustomCriteria();
        return customCriteria != null && customCriteria.length > 0 && customCriteria[0].equals("passwordUpdate");
    }

    @Override
    public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity = persistencePackage.getEntity();
        Customer customer = customerService.readCustomerByUsername(entity.findProperty("username").getValue());
        if (StringUtils.isEmpty(customer.getEmailAddress())) {
            throw new ServiceException("Unable to update password because an email address is not available for this customer. An email address is required to send the customer the new system generated password.");
        }
        
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUsername(entity.findProperty("username").getValue());
        passwordReset.setPasswordChangeRequired(false);
        passwordReset.setEmail(customer.getEmailAddress());
        passwordReset.setPasswordLength(22);
        passwordReset.setSendResetEmailReliableAsync(false);
        
        customer = customerService.resetPassword(passwordReset);
        
        return entity;
    }
    
    
    
}
