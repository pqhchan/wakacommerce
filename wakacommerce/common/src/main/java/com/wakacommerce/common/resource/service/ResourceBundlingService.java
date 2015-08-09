
package com.wakacommerce.common.resource.service;

import org.springframework.core.io.Resource;

import com.wakacommerce.common.web.processor.ResourceBundleProcessor;

import java.util.List;

/**
 *
 * @ hui
 */
public interface ResourceBundlingService {

    public String resolveBundleResourceName(String requestedBundleName, String mappingPrefix, List<String> files);

    Resource resolveBundleResource(String versionedBundleResourceName);

    List<String> getAdditionalBundleFiles(String bundleName);

    boolean checkForRegisteredBundleFile(String versionedBundleName);

}
