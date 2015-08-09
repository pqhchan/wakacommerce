
package com.wakacommerce.common.web.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.resource.service.ResourceBundlingService;
import com.wakacommerce.common.util.BLCSystemProperty;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @ hui
 */
public class ResourceBundleProcessor extends AbstractElementProcessor {
    
    @Resource(name = "blResourceBundlingService")
    protected ResourceBundlingService bundlingService;
    
    protected boolean getBundleEnabled() {
        return BLCSystemProperty.resolveBooleanSystemProperty("bundle.enabled");
    }

    public ResourceBundleProcessor() {
        super("bundle");
    }
    
    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected ProcessorResult processElement(Arguments arguments, Element element) {
        String name = element.getAttributeValue("name");
        String mappingPrefix = element.getAttributeValue("mapping-prefix");
        boolean async = element.hasAttribute("async");
        boolean defer = element.hasAttribute("defer");
        NestableNode parent = element.getParent();
        List<String> files = new ArrayList<String>();
        for (String file : element.getAttributeValue("files").split(",")) {
            files.add(file.trim());
        }
        List<String> additionalBundleFiles = bundlingService.getAdditionalBundleFiles(name);
        if (additionalBundleFiles != null) {
            files.addAll(additionalBundleFiles);
        }
        
        if (getBundleEnabled()) {
            String bundleResourceName = bundlingService.resolveBundleResourceName(name, mappingPrefix, files);
            String bundleUrl = getBundleUrl(arguments, bundleResourceName);
            Element e = getElement(bundleUrl, async, defer);
            parent.insertAfter(element, e);
        } else {
            for (String file : files) {
                file = file.trim();
                Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                        .parseExpression(arguments.getConfiguration(), arguments, "@{'" + mappingPrefix + file + "'}");
                String value = (String) expression.execute(arguments.getConfiguration(), arguments);
                Element e = getElement(value, async, defer);
                parent.insertBefore(element, e);
            }
        }
        
        parent.removeChild(element);
        return ProcessorResult.OK;
    }

    protected String getBundleUrl(Arguments arguments, String bundleName) {
        String bundleUrl = bundleName;

        if (!StringUtils.startsWith(bundleUrl, "/")) {
            bundleUrl = "/" + bundleUrl;
        }

        IWebContext context = (IWebContext) arguments.getContext();
        HttpServletRequest request = context.getHttpServletRequest();
        String contextPath = request.getContextPath();

        if (StringUtils.isNotEmpty(contextPath)) {
            bundleUrl = contextPath + bundleUrl;
        }

        return bundleUrl;
    }

    protected Element getScriptElement(String src, boolean async, boolean defer) {
        Element e = new Element("script");
        e.setAttribute("type", "text/javascript");
        e.setAttribute("src", src);
        if (async) {
            e.setAttribute("async", true, null);
        }
        if (defer) {
            e.setAttribute("defer", true, null);
        }
        return e;
    }
    
    protected Element getLinkElement(String src) {
        Element e = new Element("link");
        e.setAttribute("rel", "stylesheet");
        e.setAttribute("href", src);
        return e;
    }
    
    protected Element getElement(String src, boolean async, boolean defer) {
        if (src.contains(";")) {
            src = src.substring(0, src.indexOf(';'));
        }
        
        if (src.endsWith(".js")) {
            return getScriptElement(src, async, defer);
        } else if (src.endsWith(".css")) {
            return getLinkElement(src);
        } else {
            throw new IllegalArgumentException("Unknown extension for: " + src + " - only .js and .css are supported");
        }
    }
}
