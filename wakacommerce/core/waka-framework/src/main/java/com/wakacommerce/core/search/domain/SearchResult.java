package com.wakacommerce.core.search.domain;

import java.util.List;

import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;

public class SearchResult {
    
    protected List<Product> products;
    protected List<Sku> skus;
    protected List<SearchFacetDTO> facets;
    
    protected Integer totalResults;
    protected Integer page;
    protected Integer pageSize;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public List<SearchFacetDTO> getFacets() {
        return facets;
    }

    public void setFacets(List<SearchFacetDTO> facets) {
        this.facets = facets;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Integer getStartResult() {
        return ((products == null || products.size() == 0) && (skus == null || skus.size() == 0)) ? 0 : ((page - 1) * pageSize) + 1;
    }
    
    public Integer getEndResult() {
        return Math.min(page * pageSize, totalResults);
    }
    
    public Integer getTotalPages() {
        return ((products == null || products.size() == 0) && (skus == null || skus.size() == 0)) ? 1 : (int) Math.ceil(totalResults * 1.0 / pageSize);
    }

}
