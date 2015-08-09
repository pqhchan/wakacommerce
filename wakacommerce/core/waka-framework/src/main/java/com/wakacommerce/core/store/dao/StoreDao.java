
package com.wakacommerce.core.store.dao;

import java.util.List;

import com.wakacommerce.core.store.domain.Store;

public interface StoreDao {

    public Store readStoreById(Long id);

    public Store readStoreByStoreName(final String storeName);

    @Deprecated
    public Store readStoreByStoreCode(final String storeCode);

    public List<Store> readAllStores();

    public List<Store> readAllStoresByState(final String state);

    public Store save(Store store);

}
