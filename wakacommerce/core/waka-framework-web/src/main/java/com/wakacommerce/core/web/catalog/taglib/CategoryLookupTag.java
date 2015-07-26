
package com.wakacommerce.core.web.catalog.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.core.catalog.domain.Category;

import javax.servlet.jsp.JspException;

public class CategoryLookupTag extends AbstractCatalogTag {

    private static final Log LOG = LogFactory.getLog(CategoryTag.class);
    private static final long serialVersionUID = 1L;
    private String var;

    private String categoryName;

    @Override
    public void doTag() throws JspException {
        catalogService = super.getCatalogService();

        Category category = catalogService.findCategoryByName(categoryName);

        if(category == null && LOG.isDebugEnabled()){
            LOG.debug("The category returned was null for categoryName: " + categoryName);
        }

        getJspContext().setAttribute(var, category);
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}

