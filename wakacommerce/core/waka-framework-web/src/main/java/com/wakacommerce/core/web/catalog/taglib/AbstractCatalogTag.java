
package com.wakacommerce.core.web.catalog.taglib;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.core.catalog.service.CatalogService;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public abstract class AbstractCatalogTag extends SimpleTagSupport {
    private static final long serialVersionUID = 1L;

    //TODO scc: test if @Resource will somehow work with this reference
    protected CatalogService catalogService;

    protected CatalogService getCatalogService() {
        if (catalogService == null) {
            PageContext pageContext = (PageContext)getJspContext();
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
            catalogService = (CatalogService) applicationContext.getBean("blCatalogService");
        }
        return catalogService;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public static String toVariableName(String key) {
        return key.replace('.', '_');
    }
}
