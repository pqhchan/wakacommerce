
package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchResult;

@XmlRootElement(name = "searchResults")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SearchResultsWrapper extends BaseWrapper implements APIWrapper<SearchResult> {

    @XmlElement
    protected Integer page;

    /*
     * Indicates the requested or default page size.
     */
    @XmlElement
    protected Integer pageSize;

    /*
     * Indicates the actual results
     */
    @XmlElement
    protected Integer totalResults;

    /*
     * Indicates the number of pages
     */
    @XmlElement
    protected Integer totalPages;

    /*
     * List of products associated with a search
     */
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    protected List<ProductWrapper> products;
    
    /*
     * List of products associated with a search
     */
    @XmlElementWrapper(name = "skus")
    @XmlElement(name = "sku")
    protected List<SkuWrapper> skus;

    /*
     * List of available facets to be used for searching
     */
    @XmlElementWrapper(name = "searchFacets")
    @XmlElement(name = "searchFacet")
    protected List<SearchFacetWrapper> searchFacets;

    @Override
    public void wrapDetails(SearchResult model, HttpServletRequest request) {

        page = model.getPage();
        pageSize = model.getPageSize();
        totalResults = model.getTotalResults();
        totalPages = model.getTotalPages();

        if (model.getProducts() != null) {
            products = new ArrayList<ProductWrapper>();
            for (Product product : model.getProducts()) {
                ProductWrapper productSummary = (ProductWrapper) context.getBean(ProductWrapper.class.getName());
                productSummary.wrapSummary(product, request);
                this.products.add(productSummary);
            }
        }
        
        if (model.getSkus() != null) {
            skus = new ArrayList<SkuWrapper>();
            for (Sku sku : model.getSkus()) {
                SkuWrapper skuSummary = (SkuWrapper) context.getBean(SkuWrapper.class.getName());
                skuSummary.wrapSummary(sku, request);
                this.skus.add(skuSummary);
            }
        }

        if (model.getFacets() != null) {
            this.searchFacets = new ArrayList<SearchFacetWrapper>();
            for (SearchFacetDTO facet : model.getFacets()) {
                SearchFacetWrapper facetWrapper = (SearchFacetWrapper) context.getBean(SearchFacetWrapper.class.getName());
                facetWrapper.wrapSummary(facet, request);
                this.searchFacets.add(facetWrapper);
            }
        }
    }

    @Override
    public void wrapSummary(SearchResult model, HttpServletRequest request) {
        wrapDetails(model, request);
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

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<ProductWrapper> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWrapper> products) {
        this.products = products;
    }

    public List<SkuWrapper> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuWrapper> skus) {
        this.skus = skus;
    }

    public List<SearchFacetWrapper> getSearchFacets() {
        return searchFacets;
    }

    public void setSearchFacets(List<SearchFacetWrapper> searchFacets) {
        this.searchFacets = searchFacets;
    }
}
