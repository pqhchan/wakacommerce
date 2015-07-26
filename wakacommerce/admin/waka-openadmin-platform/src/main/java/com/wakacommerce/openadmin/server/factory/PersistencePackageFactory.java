

package com.wakacommerce.openadmin.server.factory;

import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;

/**
 * Responsible for creating different persistence packages for different operations
 * 
 *Andre Azzolini (apazzolini)
 */
public interface PersistencePackageFactory {

    /**
     * Creates a persistence package for the given request. Different request types require different combinations
     * of attributes, which are generally self explanatory.
     * 
     * @param request
     * @return the persistence package
     * 
     * @see PersistencePackageRequest
     * @see PersistencePackageRequest.Type
     */
    public PersistencePackage create(PersistencePackageRequest request);

}
