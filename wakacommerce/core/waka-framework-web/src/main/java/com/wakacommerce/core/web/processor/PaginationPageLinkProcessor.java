
package com.wakacommerce.core.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.web.util.ProcessorUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * A Thymeleaf processor that processes the value attribute on the element it's tied to
 * with a predetermined value based on the SearchFacetResultDTO object that is passed into this
 * processor.
 * 
 * 
 *  
 */
public class PaginationPageLinkProcessor extends AbstractAttributeModifierAttrProcessor {

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     */
    public PaginationPageLinkProcessor() {
        super("paginationpagelink");
    }
    
    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
        Map<String, String> attrs = new HashMap<String, String>();
        
        WakaRequestContext blcContext = WakaRequestContext.getWakaRequestContext();
        HttpServletRequest request = blcContext.getRequest();
        
        String baseUrl = request.getRequestURL().toString();
        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue(attributeName));
        Integer page = (Integer) expression.execute(arguments.getConfiguration(), arguments);
        if (page != null && page > 1) {
            params.put(SearchCriteria.PAGE_NUMBER, new String[] { page.toString() });
        } else {
            params.remove(SearchCriteria.PAGE_NUMBER);
        }
        
        String url = ProcessorUtils.getUrl(baseUrl, params);
        
        attrs.put("href", url);
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
