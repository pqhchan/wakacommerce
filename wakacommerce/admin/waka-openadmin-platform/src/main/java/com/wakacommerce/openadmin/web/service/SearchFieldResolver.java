  

package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.exception.ServiceException;

/**
 * Resolves which field of an entity should be used for searching when targeting
 * an entity directly and not a specific field on that entity.
 * 
 * 
 */
public interface SearchFieldResolver {

    /**
     * Returns the name of the field to use for searching for the given entity classname.
     * 
     * @param className
     * @return the field name
     * @throws ServiceException
     */
    public String resolveField(String className) throws ServiceException;

}