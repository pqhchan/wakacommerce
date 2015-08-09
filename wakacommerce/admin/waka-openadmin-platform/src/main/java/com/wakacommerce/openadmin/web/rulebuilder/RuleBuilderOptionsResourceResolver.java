  

package com.wakacommerce.openadmin.web.rulebuilder;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import com.wakacommerce.common.resource.GeneratedResource;
import com.wakacommerce.openadmin.web.rulebuilder.enums.RuleBuilderEnumOptionsExtensionListener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@Component("blRuleBuilderOptionResourceResolver")
public class RuleBuilderOptionsResourceResolver implements ResourceResolver {

    protected static final String RULE_BUILDER_OPTIONS_JS_PATH="admin/components/ruleBuilder-options.js";


    @javax.annotation.Resource(name = "blRuleBuilderEnumOptionsExtensionListeners")
    protected List<RuleBuilderEnumOptionsExtensionListener> listeners = new ArrayList<RuleBuilderEnumOptionsExtensionListener>();

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {

        // Delegate to the chain if the request is not for ruildBuilder-options.js
        if(!requestPath.equalsIgnoreCase(RULE_BUILDER_OPTIONS_JS_PATH)){
            return chain.resolveResource(request,requestPath,locations);
        }
        // aggregate option values for all registered RuleBuilderEnumOptionsExtensionListener
        StringBuilder sb = new StringBuilder();
        for (RuleBuilderEnumOptionsExtensionListener listener : listeners) {
            sb.append(listener.getOptionValues()).append("\r\n");
        }
        return new GeneratedResource(sb.toString().getBytes(),requestPath) ;
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        if(resourcePath != RULE_BUILDER_OPTIONS_JS_PATH){
            return chain.resolveUrlPath(resourcePath,locations);
        }
        return RULE_BUILDER_OPTIONS_JS_PATH;
    }
}
