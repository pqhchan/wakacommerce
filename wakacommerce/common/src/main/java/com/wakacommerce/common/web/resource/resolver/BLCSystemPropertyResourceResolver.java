package com.wakacommerce.common.web.resource.resolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import com.wakacommerce.common.resource.GeneratedResource;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.BaseUrlResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@Component("blSystemPropertyJSResolver")
public class BLCSystemPropertyResourceResolver extends AbstractResourceResolver implements Ordered {

    protected static final Log LOG = LogFactory.getLog(BLCSystemPropertyResourceResolver.class);

    protected static final String BLC_SYSTEM_PROPERTY_FILE = "BLC-system-property.js";
    protected static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private int order = BroadleafResourceResolverOrder.BLC_SYSTEM_PROPERTY_RESOURCE_RESOLVER;

    @javax.annotation.Resource(name = "blBaseUrlResolver")
    BaseUrlResolver urlResolver;

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
            ResourceResolverChain chain) {
        return chain.resolveUrlPath(resourceUrlPath, locations);
    }
    
    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {

        Resource resource = chain.resolveResource(request, requestPath, locations);

        if (requestPath.equalsIgnoreCase(BLC_SYSTEM_PROPERTY_FILE)) {
            try {
                resource = convertResource(resource, requestPath);
            } catch (IOException ioe) {
                LOG.error("Exception modifying " + BLC_SYSTEM_PROPERTY_FILE, ioe);
            }
        }

        return resource;
    }

    protected Resource convertResource(Resource origResource, String resourceFileName) throws IOException {
        byte[] bytes = FileCopyUtils.copyToByteArray(origResource.getInputStream());
        String content = new String(bytes, DEFAULT_CHARSET);
        
        String newContent = content;
        if (! StringUtils.isEmpty(content)) {
            String regexKey = "\\\"BLC_PROP:(.*)\\\"";

            Pattern p = Pattern.compile(regexKey);
            Matcher m = p.matcher(content);
            while (m.find()) {
                String matchedPlaceholder = m.group(0);
                String propertyName = m.group(1);

                String propVal = BLCSystemProperty.resolveSystemProperty(propertyName);
                if (!StringUtils.isEmpty(propVal)) {
                    propVal = "";
                }

                newContent = newContent.replaceAll(matchedPlaceholder, '"' + propVal + '"');
            }
        }
        
        return new GeneratedResource(newContent.getBytes(), resourceFileName);
    }
    
    protected String addVersion(String requestPath, String version) {
        String baseFilename = StringUtils.stripFilenameExtension(requestPath);
        String extension = StringUtils.getFilenameExtension(requestPath);
        return baseFilename + version + "." + extension;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
