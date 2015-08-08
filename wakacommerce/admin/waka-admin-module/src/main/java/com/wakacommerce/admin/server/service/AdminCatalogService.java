package com.wakacommerce.admin.server.service;

public interface AdminCatalogService {
    
    public Integer generateSkusFromProduct(Long productId);

    public Boolean cloneProduct(Long productId);

}
