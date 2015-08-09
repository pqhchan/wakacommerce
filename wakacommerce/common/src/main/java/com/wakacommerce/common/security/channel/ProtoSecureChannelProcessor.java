
package com.wakacommerce.common.security.channel;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.SecureChannelProcessor;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

/**
 *
 * @ hui
 */
public class ProtoSecureChannelProcessor extends SecureChannelProcessor {

    @Override
    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
        Assert.isTrue((invocation != null) && (config != null), "Nulls cannot be provided");

        for (ConfigAttribute attribute : config) {
            if (supports(attribute)) {
                if (invocation.getHttpRequest().getHeader("X-Forwarded-Proto") != null
                        && "https".equalsIgnoreCase(invocation.getHttpRequest().getHeader("X-Forwarded-Proto"))) {
                    return;
                } else if (invocation.getHttpRequest().isSecure()) {
                    return;
                } else {
                    getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
                }
            }
        }
    }

}
