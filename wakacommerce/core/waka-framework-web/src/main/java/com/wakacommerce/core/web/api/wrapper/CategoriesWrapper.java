
package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.Category;

/**
 * This is a JAXB wrapper class for wrapping a collection of categories.
 */
@XmlRootElement(name = "categories")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoriesWrapper extends BaseWrapper implements APIWrapper<List<Category>> {

    @XmlElement(name = "category")
    protected List<CategoryWrapper> categories = new ArrayList<CategoryWrapper>();

    @Override
    public void wrapDetails(List<Category> cats, HttpServletRequest request) {
        for (Category category : cats) {
            CategoryWrapper wrapper = (CategoryWrapper) context.getBean(CategoryWrapper.class.getName());
            wrapper.wrapSummary(category, request);
            categories.add(wrapper);
        }
    }

    @Override
    public void wrapSummary(List<Category> cats, HttpServletRequest request) {
        wrapDetails(cats, request);
    }

    
    /**
     * @return the categories
     */
    public List<CategoryWrapper> getCategories() {
        return categories;
    }

    
    /**
     * @param categories the categories to set
     */
    public void setCategories(List<CategoryWrapper> categories) {
        this.categories = categories;
    }
}
