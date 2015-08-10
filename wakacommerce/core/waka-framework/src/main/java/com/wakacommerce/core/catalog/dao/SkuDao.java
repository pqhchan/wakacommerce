package com.wakacommerce.core.catalog.dao;

import java.util.List;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuFee;

/**
 *
 * @ hui
 */
public interface SkuDao {

    public Sku readSkuById(Long skuId);

    public Sku readSkuByUpc(String upc);

    public Sku save(Sku sku);
    
    public SkuFee saveSkuFee(SkuFee fee);

    public Sku readFirstSku();

    public List<Sku> readAllSkus();

    public List<Sku> readSkusByIds(List<Long> ids);

    public void delete(Sku sku);

    public Sku create();

    public Long readCountAllActiveSkus();

    public List<Sku> readAllActiveSkus(int page, int pageSize);

    public Long getCurrentDateResolution();

    public void setCurrentDateResolution(Long currentDateResolution);

    public List<Sku> findSkuByURI(String key);
    
}
