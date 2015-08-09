package com.mycompany.controller.seo;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wakacommerce.common.sitemap.controller.BroadleafSiteMapController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Controller
public class SiteMapController extends BroadleafSiteMapController {

    @RequestMapping(value = { "/sitemap*.xml", "sitemap*.gz" })
    @ResponseBody
    public FileSystemResource retrieveSiteMapIndex(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return super.retrieveSiteMapFile(request, response);
    }
}
