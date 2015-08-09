
package com.wakacommerce.common.web;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.exception.SiteNotFoundException;
import com.wakacommerce.common.site.domain.Site;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class NullBroadleafSiteResolver implements BroadleafSiteResolver {

    @Override
    public Site resolveSite(HttpServletRequest request) {
        return resolveSite(new ServletWebRequest(request));
    }
    
    @Override
    public Site resolveSite(WebRequest request) {
        return resolveSite(request, false);
    }

    @Override
    public Site resolveSite(WebRequest request, boolean allowNullSite) throws SiteNotFoundException {
        return null;
    }
    
}
