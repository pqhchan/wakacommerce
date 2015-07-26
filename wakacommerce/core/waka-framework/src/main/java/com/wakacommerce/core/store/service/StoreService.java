
package com.wakacommerce.core.store.service;

import java.util.List;
import java.util.Map;

import com.wakacommerce.core.store.domain.Store;
import com.wakacommerce.profile.core.domain.Address;

public interface StoreService {

    public Store readStoreById(Long id);

    public Store readStoreByStoreName(String storeName);

    /**
     * @deprecated use {@link #readStoreByStoreName(String)} instead.
     *
     * @param storeCode
     * @return
     */
    @Deprecated
    public Store readStoreByStoreCode(String storeCode);

    public Store saveStore(Store store);

    public Map<Store,Double> findStoresByAddress(Address searchAddress, double distance);

    public List<Store> readAllStores();

    public List<Store> readAllStoresByState(String state);

}
