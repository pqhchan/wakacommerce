
package com.wakacommerce.core.web.layout.tags;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.core.catalog.service.CatalogService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ProductLookupTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;
    private String var;
    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public int doStartTag() throws JspException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        CatalogService catalogService = (CatalogService) applicationContext.getBean("blCatalogService");
        pageContext.setAttribute(var, catalogService.findProductById(productId));
        return EVAL_PAGE;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
