
package com.wakacommerce.common.resource.service;

import org.springframework.core.io.Resource;

/**
 *
 * @ hui
 */
public interface ResourceMinificationService {

    public byte[] minify(String filename, byte[] bytes);

    public boolean getEnabled();

    boolean getAllowSingleMinification();

    Resource minify(Resource originalResource);

    Resource minify(Resource originalResource, String filename);

}
