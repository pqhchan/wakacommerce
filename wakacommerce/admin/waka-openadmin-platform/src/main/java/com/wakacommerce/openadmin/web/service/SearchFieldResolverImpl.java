  

package com.wakacommerce.openadmin.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.server.service.AdminEntityService;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blSearchFieldResolver")
public class SearchFieldResolverImpl implements SearchFieldResolver {
    protected static final Log LOG = LogFactory.getLog(SearchFieldResolverImpl.class);

    @Resource(name = "blAdminEntityService")
    protected AdminEntityService service;
    
    @Override
    public String resolveField(String className) throws ServiceException {
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(className);
        ClassMetadata md = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        
        if (md.getPMap().containsKey("name")) {
            return "name";
        }

        if (md.getPMap().containsKey("friendlyName")) {
            return "friendlyName";
        }

        if (md.getPMap().containsKey("templateName")) {
            return "templateName";
        }
        
        return "id";
    }


}
