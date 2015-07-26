
package com.wakacommerce.core.web.catalog.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.wakacommerce.core.catalog.domain.Product;

import java.io.IOException;

public class ProductLinkTag extends CategoryLinkTag {

    private static final long serialVersionUID = 1L;

    private Product product;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println(getUrl(product));
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    protected String getUrl(Product product) {
        PageContext pageContext = (PageContext)getJspContext();
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        StringBuffer sb = new StringBuffer();
        sb.append("<a class=\"noTextUnderline\"  href=\"");
        sb.append(request.getContextPath());
        sb.append("/");
        sb.append(getCategory() == null ? product.getDefaultCategory().getGeneratedUrl() : getCategory().getGeneratedUrl());
        sb.append("?productId=" + product.getId());
        sb.append("\">");
        sb.append(product.getName());
        sb.append("</a>");

        return sb.toString();
    }

}
