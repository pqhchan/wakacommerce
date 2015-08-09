package com.wakacommerce.common.copy;

/**
 *
 * @ hui
 */
public interface MultiTenantCloneable<T> {

    public <G extends T> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException;
}
