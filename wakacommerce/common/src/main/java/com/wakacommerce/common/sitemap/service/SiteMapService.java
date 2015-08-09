

package com.wakacommerce.common.sitemap.service;

import java.io.File;
import java.io.IOException;

import com.wakacommerce.common.sitemap.exception.SiteMapException;

/**
 *
 * @ hui
 */
public interface SiteMapService {

    SiteMapGenerationResponse generateSiteMap() throws IOException, SiteMapException;

    File getSiteMapFile(String fileName) throws SiteMapException, IOException;
}
