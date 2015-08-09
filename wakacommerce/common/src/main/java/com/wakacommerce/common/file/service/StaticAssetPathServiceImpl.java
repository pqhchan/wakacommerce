package com.wakacommerce.common.file.service;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.view.ImportSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("blStaticAssetPathService")
public class StaticAssetPathServiceImpl implements StaticAssetPathService {

    @Value("${asset.server.url.prefix.internal}")
    protected String staticAssetUrlPrefix;

    @Value("${asset.server.url.prefix}")
    protected String staticAssetEnvironmentUrlPrefix;

    @Value("${asset.server.url.prefix.secure}")
    protected String staticAssetEnvironmentSecureUrlPrefix;

    @Override
    public String convertAllAssetPathsInContent(String content, boolean secureRequest) {
        String returnValue = content;

        if (StringUtils.isNotBlank(content) &&
                StringUtils.isNotBlank(getStaticAssetUrlPrefix()) &&
                StringUtils.isNotBlank(getStaticAssetEnvironmentUrlPrefix()) &&
                content.contains(getStaticAssetUrlPrefix())) {

            final String envPrefix;
            if (secureRequest) {
                envPrefix = getStaticAssetEnvironmentSecureUrlPrefix();
            } else {
                envPrefix = getStaticAssetEnvironmentUrlPrefix();
            }

            if (envPrefix != null) {
                String trailing = "";
                if (envPrefix.endsWith(File.separator)) {
                    trailing = File.separator;
                }
                returnValue = returnValue.replaceAll(getStaticAssetUrlPrefix()+trailing, envPrefix);
                //Catch any scenario where there is a leading "/" after the replacement
                returnValue = returnValue.replaceAll(File.separator + envPrefix, envPrefix);
            }

        }

        return returnValue;
    }

    @Override
    public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest) {
        String returnValue = assetPath;
        
        if (assetPath != null && getStaticAssetEnvironmentUrlPrefix() != null && ! "".equals(getStaticAssetEnvironmentUrlPrefix())) {
            String envPrefix;
            if (secureRequest) {
                envPrefix = getStaticAssetEnvironmentSecureUrlPrefix();
            } else {
                envPrefix = getStaticAssetEnvironmentUrlPrefix();
            }
            if (envPrefix != null) {
                // remove the starting "/" if it exists.
                if (returnValue.startsWith("/")) {
                    returnValue = returnValue.substring(1);
                }

                // Also, remove the "cmsstatic" from the URL before prepending the staticAssetUrlPrefix.
                if (returnValue.startsWith(getStaticAssetUrlPrefix())) {
                    returnValue = returnValue.substring(getStaticAssetUrlPrefix().trim().length());

                    // remove the starting "/" if it exists.
                    if (returnValue.startsWith("/")) {
                        returnValue = returnValue.substring(1);
                    }
                } else if (envPrefix.endsWith(getStaticAssetUrlPrefix() + "/")) {
                    envPrefix = envPrefix.substring(0, envPrefix.length() - getStaticAssetUrlPrefix().length() - 1);
                }
                returnValue = envPrefix + returnValue;
            }
        } else {
            if (returnValue != null && ! ImportSupport.isAbsoluteUrl(returnValue)) {
                if (! returnValue.startsWith("/")) {
                    returnValue = "/" + returnValue;
                }

                // Add context path
                if (contextPath != null && ! contextPath.equals("")) {
                    if (! contextPath.equals("/")) {
                        // Shouldn't be the case, but let's handle it anyway
                        if (contextPath.endsWith("/")) {
                            returnValue = returnValue.substring(1);
                        }
                        if (contextPath.startsWith("/")) {
                            returnValue = contextPath + returnValue;  // normal case
                        } else {
                            returnValue = "/" + contextPath + returnValue;
                        }
                    }
                }
            }
        }

        return returnValue;
    }

    @Override
    public String getStaticAssetUrlPrefix() {
        return staticAssetUrlPrefix;
    }

    @Override
    public void setStaticAssetUrlPrefix(String staticAssetUrlPrefix) {
        this.staticAssetUrlPrefix = staticAssetUrlPrefix;
    }

    @Override
    public String getStaticAssetEnvironmentUrlPrefix() {
        return fixEnvironmentUrlPrefix(staticAssetEnvironmentUrlPrefix);
    }

    @Override
    public void setStaticAssetEnvironmentUrlPrefix(String staticAssetEnvironmentUrlPrefix) {
        this.staticAssetEnvironmentUrlPrefix = staticAssetEnvironmentUrlPrefix;
    }

    @Override
    public String getStaticAssetEnvironmentSecureUrlPrefix() {
        if (StringUtils.isEmpty(staticAssetEnvironmentSecureUrlPrefix)) {
            if (!StringUtils.isEmpty(staticAssetEnvironmentUrlPrefix) && staticAssetEnvironmentUrlPrefix.indexOf("http:") >= 0) {
                staticAssetEnvironmentSecureUrlPrefix = staticAssetEnvironmentUrlPrefix.replace("http:", "https:");
            }
        }
        return fixEnvironmentUrlPrefix(staticAssetEnvironmentSecureUrlPrefix);
    }

    public void setStaticAssetEnvironmentSecureUrlPrefix(String staticAssetEnvironmentSecureUrlPrefix) {        
        this.staticAssetEnvironmentSecureUrlPrefix = staticAssetEnvironmentSecureUrlPrefix;
    }

    private String fixEnvironmentUrlPrefix(String urlPrefix) {
        if (urlPrefix != null) {
            urlPrefix = urlPrefix.trim();
            if ("".equals(urlPrefix)) {
                // The value was not set.
                urlPrefix = null;
            } else if (urlPrefix.equals(staticAssetUrlPrefix)) {
                // The value is the same as the default, so no processing needed.
                urlPrefix = null;
            }
        }

        if (urlPrefix != null && !urlPrefix.endsWith("/")) {
            urlPrefix = urlPrefix + "/";
        }
        return urlPrefix;
    }

}
