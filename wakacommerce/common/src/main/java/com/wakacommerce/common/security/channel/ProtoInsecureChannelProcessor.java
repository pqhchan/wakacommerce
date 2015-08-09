
package com.wakacommerce.common.security.channel;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

/**
 *
 * @ hui
 */
public class ProtoInsecureChannelProcessor extends InsecureChannelProcessor {

    @Override
    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
        if ((invocation == null) || (config == null)) {
            throw new IllegalArgumentException("Nulls cannot be provided");
        }

        for (ConfigAttribute attribute : config) {
            if (supports(attribute)) {
                if (invocation.getHttpRequest().getHeader("X-Forwarded-Proto") != null
                        && "https".equalsIgnoreCase(invocation.getHttpRequest().getHeader("X-Forwarded-Proto"))) {
                    //We can't rely entirely on "!invocation.getHttpRequest().isSecure()" because many times, 
                    //when SSL terminates somewhere else, the proxied request will not be secure.
                    //In this case, someone may have gone to a secured page, and then tried to go back to an unsecured page.
                    getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
                } else if (invocation.getHttpRequest().getHeader("X-Forwarded-Proto") != null
                        && "http".equalsIgnoreCase(invocation.getHttpRequest().getHeader("X-Forwarded-Proto"))) {
                    return;
                } else if (!invocation.getHttpRequest().isSecure()) {
                    return;
                } else {
                    getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
                }
            }
        }
    }
}
