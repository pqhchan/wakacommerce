
package com.wakacommerce.openadmin.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 */
public class BatchPersistencePackage implements Serializable {

    protected PersistencePackage[] persistencePackages;

    public PersistencePackage[] getPersistencePackages() {
        return persistencePackages;
    }

    public void setPersistencePackages(PersistencePackage[] persistencePackages) {
        this.persistencePackages = persistencePackages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        BatchPersistencePackage that = (BatchPersistencePackage) o;

        if (!Arrays.equals(persistencePackages, that.persistencePackages)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return persistencePackages != null ? Arrays.hashCode(persistencePackages) : 0;
    }
}
