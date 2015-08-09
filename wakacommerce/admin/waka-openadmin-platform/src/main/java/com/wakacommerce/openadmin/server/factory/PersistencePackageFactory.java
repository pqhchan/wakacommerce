

package com.wakacommerce.openadmin.server.factory;

import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;

/**
 *
 * @ hui
 */
public interface PersistencePackageFactory {

    public PersistencePackage create(PersistencePackageRequest request);

}
