
package com.wakacommerce.core.search.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @ hui
 */
public class CatalogStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Map<Long, Set<Long>> parentCategoriesByProduct = new HashMap<Long, Set<Long>>();
    protected Map<Long, Set<Long>> parentCategoriesByCategory = new HashMap<Long, Set<Long>>();
    protected Map<Long, List<Long>> productsByCategory = new HashMap<Long, List<Long>>();
    protected Map<String, BigDecimal> displayOrdersByCategoryProduct = new HashMap<String, BigDecimal>();

    public Map<Long, Set<Long>> getParentCategoriesByProduct() {
        return parentCategoriesByProduct;
    }

    public void setParentCategoriesByProduct(Map<Long, Set<Long>> parentCategoriesByProduct) {
        this.parentCategoriesByProduct = parentCategoriesByProduct;
    }

    public Map<Long, Set<Long>> getParentCategoriesByCategory() {
        return parentCategoriesByCategory;
    }

    public void setParentCategoriesByCategory(Map<Long, Set<Long>> parentCategoriesByCategory) {
        this.parentCategoriesByCategory = parentCategoriesByCategory;
    }

    public Map<Long, List<Long>> getProductsByCategory() {
        return productsByCategory;
    }

    public void setProductsByCategory(Map<Long, List<Long>> productsByCategory) {
        this.productsByCategory = productsByCategory;
    }

    public Map<String, BigDecimal> getDisplayOrdersByCategoryProduct() {
        return displayOrdersByCategoryProduct;
    }

    public void setDisplayOrdersByCategoryProduct(Map<String, BigDecimal> displayOrdersByCategoryProduct) {
        this.displayOrdersByCategoryProduct = displayOrdersByCategoryProduct;
    }

}
