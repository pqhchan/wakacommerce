 
package com.wakacommerce.cms.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.cms.file.service.StaticAssetService;
import com.wakacommerce.common.file.service.StaticAssetPathService;
import com.wakacommerce.common.web.WakaRequestContext;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class UrlRewriteProcessor extends AbstractAttributeModifierAttrProcessor {
    
    @Resource(name = "blStaticAssetPathService")
    protected StaticAssetPathService staticAssetPathService;

    public UrlRewriteProcessor() {
        this("src");
    }
    
    protected UrlRewriteProcessor(final String attributeName) {
        super(attributeName);
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    protected boolean isRequestSecure(HttpServletRequest request) {
        return ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
    } 

    
    @Override
    protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
        Map<String, String> attrs = new HashMap<String, String>();
        HttpServletRequest request = WakaRequestContext.getWakaRequestContext().getRequest();
        
        boolean secureRequest = true;
        if (request != null) {
            secureRequest = isRequestSecure(request);
        }
        
        String elementValue = element.getAttributeValue(attributeName);

        if (elementValue.startsWith("/")) {
            elementValue = "@{ " + elementValue + " }";
        }
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, elementValue);
        String assetPath = (String) expression.execute(arguments.getConfiguration(), arguments);
        
        // We are forcing an evaluation of @{} from Thymeleaf above which will automatically add a contextPath, no need to
        // add it twice
        assetPath = staticAssetPathService.convertAssetPath(assetPath, null, secureRequest);
        
        attrs.put("src", assetPath);
        
        return attrs;
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return ModificationType.SUBSTITUTION;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return true;
    }

    @Override
    protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String attributeName) {
        return false;
    }
}
