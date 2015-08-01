 
package com.wakacommerce.cms.admin.server.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.RequestDTOImpl;
import com.wakacommerce.openadmin.dto.PersistencePackage;

@Component("blRequestDTOCustomPersistenceHandler")
public class RequestDTOCustomPersistenceHandler extends TimeDTOCustomPersistenceHandler {

    private static final Log LOG = LogFactory.getLog(RequestDTOCustomPersistenceHandler.class);

    @Override
    public Boolean canHandleInspect(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        return RequestDTOImpl.class.getName().equals(ceilingEntityFullyQualifiedClassname);
    }
}
