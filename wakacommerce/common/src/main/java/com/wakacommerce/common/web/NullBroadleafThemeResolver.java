
package com.wakacommerce.common.web;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.domain.Theme;
import com.wakacommerce.common.site.domain.ThemeDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Returns null for the Site (typical for non-multi-site implementations of
 * Broadleaf Commerce.
 *
 *bpolster
 */
public class NullBroadleafThemeResolver implements BroadleafThemeResolver {
    private final Theme theme = new ThemeDTO();

    @Override
    public Theme resolveTheme(HttpServletRequest request, Site site) {
        return resolveTheme(new ServletWebRequest(request));
    }
    
    @Override
    public Theme resolveTheme(WebRequest request) {
        return theme;
    }
}
