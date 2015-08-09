  

package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.exception.ServiceException;

/**
 *
 * @ hui
 */
public interface SearchFieldResolver {

    public String resolveField(String className) throws ServiceException;

}