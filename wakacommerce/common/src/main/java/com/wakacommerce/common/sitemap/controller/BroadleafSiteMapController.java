

package com.wakacommerce.common.sitemap.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;

import com.wakacommerce.common.sitemap.service.SiteMapService;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller to generate and retrieve site map files.
 * 
 *  
 */
public class BroadleafSiteMapController {

    private static final Log LOG = LogFactory.getLog(BroadleafSiteMapController.class);

    @Resource(name = "blSiteMapService")
    protected SiteMapService siteMapService;

    /**
     * Retrieves a site map index file in XML format
     * 
     * @param request
     * @param response
     * @param model
     * @param fileName
     * @return
     */
    public FileSystemResource retrieveSiteMapFile(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        File siteMapFile = siteMapService.getSiteMapFile(getRequestURIWithoutContext(request));
        if (siteMapFile == null || !siteMapFile.exists()) {
            response.setStatus(404);
            return null;
        }
        return new FileSystemResource(siteMapFile);
    }

    protected String getRequestURIWithoutContext(HttpServletRequest request) {
        if (request.getContextPath() != null) {
            return request.getRequestURI().substring(request.getContextPath().length());
        } else {
            return request.getRequestURI();
        }
    }

}
