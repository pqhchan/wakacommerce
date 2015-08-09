
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.exception.SiteNotFoundException;
import com.wakacommerce.common.site.domain.Site;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface BroadleafSiteResolver  {

    @Deprecated
    public Site resolveSite(HttpServletRequest request) throws SiteNotFoundException;

    public Site resolveSite(WebRequest request) throws SiteNotFoundException;

    public Site resolveSite(final WebRequest request, final boolean allowNullSite) throws SiteNotFoundException;
}
