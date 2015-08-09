
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.http.HttpStatus;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.util.xml.ISO8601DateAdapter;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategoryAttribute;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchResult;
import com.wakacommerce.core.search.service.SearchService;
import com.wakacommerce.core.web.api.BroadleafWebServicesException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "category")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryWrapper extends BaseWrapper implements APIWrapper<Category> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String name;

    @XmlElement
    protected String description;

    @XmlElement
    protected Boolean active;

    @XmlElement
    protected String url;

    @XmlElement
    protected String urlKey;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeStartDate;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeEndDate;

    @XmlElement(name = "category")
    @XmlElementWrapper(name = "subcategories")
    protected List<CategoryWrapper> subcategories;

    @XmlElement(name = "product")
    @XmlElementWrapper(name = "products")
    protected List<ProductWrapper> products;

    @XmlElement(name = "categoryAttribute")
    @XmlElementWrapper(name = "categoryAttributes")
    protected List<CategoryAttributeWrapper> categoryAttributes;

    @Override
    public void wrapDetails(Category category, HttpServletRequest request) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.active = category.isActive();
        this.activeStartDate = category.getActiveStartDate();
        this.activeEndDate = category.getActiveEndDate();
        this.url = category.getUrl();
        this.urlKey = category.getUrlKey();

        if (category.getCategoryAttributes() != null && !category.getCategoryAttributes().isEmpty()) {
            categoryAttributes = new ArrayList<CategoryAttributeWrapper>();
            for (CategoryAttribute attribute : category.getCategoryAttributes()) {
                CategoryAttributeWrapper wrapper = (CategoryAttributeWrapper) context.getBean(CategoryAttributeWrapper.class.getName());
                wrapper.wrapSummary(attribute, request);
                categoryAttributes.add(wrapper);
            }
        }

        Integer productLimit = (Integer) request.getAttribute("productLimit");
        Integer productOffset = (Integer) request.getAttribute("productOffset");
        Integer subcategoryLimit = (Integer) request.getAttribute("subcategoryLimit");
        Integer subcategoryOffset = (Integer) request.getAttribute("subcategoryOffset");

        if (productLimit != null && productOffset == null) {
            productOffset = 1;
        }

        if (subcategoryLimit != null && subcategoryOffset == null) {
            subcategoryOffset = 1;
        }

        if (productLimit != null && productOffset != null) {
            SearchService searchService = getSearchService();
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setPage(productOffset);
            searchCriteria.setPageSize(productLimit);
            searchCriteria.setFilterCriteria(new HashMap<String, String[]>());
            try {
                SearchResult result = searchService.findExplicitSearchResultsByCategory(category, searchCriteria);
                List<Product> productList = result.getProducts();
                if (productList != null && !productList.isEmpty()) {
                    if (products == null) {
                        products = new ArrayList<ProductWrapper>();
                    }

                    for (Product p : productList) {
                        ProductWrapper productSummaryWrapper = (ProductWrapper) context.getBean(ProductWrapper.class.getName());
                        productSummaryWrapper.wrapSummary(p, request);
                        products.add(productSummaryWrapper);
                    }
                }
            } catch (ServiceException e) {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e);
            }
        }

        if (subcategoryLimit != null && subcategoryOffset != null) {
            subcategories = buildSubcategoryTree(subcategories, category, request);
        }
    }

    @Override
    public void wrapSummary(Category category, HttpServletRequest request) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.active = category.isActive();
    }


    protected List<CategoryWrapper> buildSubcategoryTree(List<CategoryWrapper> wrappers, Category root, HttpServletRequest request) {
        CatalogService catalogService = (CatalogService) context.getBean("blCatalogService");

        Integer subcategoryLimit = (Integer) request.getAttribute("subcategoryLimit");
        Integer subcategoryOffset = (Integer) request.getAttribute("subcategoryOffset");

        List<Category> subcategories = catalogService.findActiveSubCategoriesByCategory(root, subcategoryLimit, subcategoryOffset);
        if (subcategories !=null && !subcategories.isEmpty() && wrappers == null) {
            wrappers = new ArrayList<CategoryWrapper>();
        }

        for (Category c : subcategories) {
            CategoryWrapper subcategoryWrapper = (CategoryWrapper) context.getBean(CategoryWrapper.class.getName());
            subcategoryWrapper.wrapSummary(c, request);
            wrappers.add(subcategoryWrapper);
        }

        return wrappers;
    }

    protected SearchService getSearchService() {
        return (SearchService) context.getBean("blSearchService");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public Date getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(Date activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public Date getActiveEndDate() {
        return activeEndDate;
    }

    public void setActiveEndDate(Date activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    public List<CategoryWrapper> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryWrapper> subcategories) {
        this.subcategories = subcategories;
    }

    public List<ProductWrapper> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWrapper> products) {
        this.products = products;
    }

    public List<CategoryAttributeWrapper> getCategoryAttributes() {
        return categoryAttributes;
    }

    public void setCategoryAttributes(List<CategoryAttributeWrapper> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }
}
