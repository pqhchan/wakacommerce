

package com.wakacommerce.core.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 *
 * @ hui
 */
@Service("blTemplateCacheKeyResolver")
public class SimpleCacheKeyResolver implements TemplateCacheKeyResolverService {

    @Override
    public String resolveCacheKey(Arguments arguments, Element element) {
        StringBuilder sb = new StringBuilder();
        sb.append(getStringValue(arguments, element, "cacheKey", true));
        sb.append(resolveTemplateName(arguments, element));
        sb.append(resolveLineNumber(arguments, element));
        return sb.toString();
    }
    

    protected String resolveTemplateName(Arguments arguments, Element element) {
        String templateName = getStringValue(arguments, element, "templateName", true);

        if (StringUtils.isEmpty(templateName)) {
            templateName = (String) element.getNodeProperty("templateName");
        }

        if (StringUtils.isEmpty(templateName)) {
            templateName = element.getDocumentName();
        }
        
        return templateName;
    }
    
    protected Integer resolveLineNumber(Arguments arguments, Element element) {
        Integer line = element.getLineNumber();
        return line == null ? 0 : line;
    }

    protected String getStringValue(Arguments arguments, Element element, String attrName, boolean removeAttribute) {
        if (element.hasAttribute(attrName)) {
            String cacheKeyParam = element.getAttributeValue(attrName);
            Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                    .parseExpression(arguments.getConfiguration(), arguments, cacheKeyParam);
            if (removeAttribute) {
                element.removeAttribute(attrName);
            }
            return expression.execute(arguments.getConfiguration(), arguments).toString();

        }
        return "";
    }
}
