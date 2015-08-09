
package com.wakacommerce.core.web.catalog.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;

import java.io.IOException;
import java.util.List;

/**
 *
 * @ hui
 */
public class SearchFilterTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private List<Product> products;
    private List<Category> categories;
    private String queryString;
    
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
//        if (products == null || products.size() == 0) { return SKIP_BODY; }
        if(products != null && products.size() > 0 ){           
            if (queryString != null && !"".equals(queryString)) {
                try {
                    out.println("<h3>Your Search</h3>");
                    out.println("<input type=\"text\"  class=\"searchQuery\" name=\"queryString\" id=\"queryString\" value='"+queryString+"' />");
                    out.println("<input type=\"hidden\"  name=\"originalQueryString\" id=\"originalQueryString\" value='"+queryString+"' />");
                } catch (IOException e) {
                }
            }
            return EVAL_BODY_INCLUDE;
        }
        if(categories != null && categories.size() > 0){
            if (queryString != null && !"".equals(queryString)) {
                try {
                    out.println("<h3>Your Search</h3>");
                    out.println("<input type=\"text\"  class=\"searchQuery\" name=\"queryString\" id=\"queryString\" value='"+queryString+"' />");
                    out.println("<input type=\"hidden\"  name=\"originalQueryString\" id=\"originalQueryString\" value='"+queryString+"' />");
                } catch (IOException e) {
                }
            }
            return EVAL_BODY_INCLUDE;           
        }
        
        return SKIP_BODY;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
