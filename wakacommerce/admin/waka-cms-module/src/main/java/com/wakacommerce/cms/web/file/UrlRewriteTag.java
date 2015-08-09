 
package com.wakacommerce.cms.web.file;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.cms.file.service.StaticAssetService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @ hui
 */
public class UrlRewriteTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    // The following attribute is set in BroadleafProcessURLFilter
    public static final String REQUEST_DTO = "blRequestDTO";

    private String value;
    private String var;

    private static StaticAssetService staticAssetService;

    protected void initServices() {
        if (staticAssetService == null) {
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());          
            staticAssetService = (StaticAssetService) applicationContext.getBean("blStaticAssetService");
        }
    }

    protected boolean isRequestSecure() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        return ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
    } 

    public int doStartTag() throws JspException {
        initServices();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        
        String returnValue = staticAssetService.convertAssetPath(value, request.getContextPath(), isRequestSecure());

        if (var != null) {
            pageContext.setAttribute(var, returnValue);
        } else {
            try {
                pageContext.getOut().print(returnValue);
            } catch (IOException ioe) {
                throw new JspTagException(ioe.toString(), ioe);
            }
        }

        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {       
        var=null;
        return super.doEndTag();                 
    }

    @Override
    public void release() {
        var = null;
        super.release();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
