
package com.wakacommerce.core.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.web.util.ProcessorUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Thymeleaf Processor that replaces the "href" attribute on an <a/> element, maintaining the current search criteria
 * of the request and adding (or replacing, if it exists) the page size parameter on the request.
 *
 *Joseph Fridye (jfridye)
 */
public class PaginationSizeLinkProcessor extends AbstractAttributeModifierAttrProcessor {

    public PaginationSizeLinkProcessor() {
        super("pagination-size-link");
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {

        Map<String, String> attributes = new HashMap<String, String>();

        HttpServletRequest request = WakaRequestContext.getWakaRequestContext().getRequest();

        String baseUrl = request.getRequestURL().toString();

        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());

        Integer pageSize = Integer.parseInt(element.getAttributeValue(attributeName));

        if (pageSize != null && pageSize > 1) {
            params.put(SearchCriteria.PAGE_SIZE_STRING, new String[]{pageSize.toString()});
        } else {
            params.remove(SearchCriteria.PAGE_SIZE_STRING);
        }

        String url = ProcessorUtils.getUrl(baseUrl, params);

        attributes.put("href", url);

        return attributes;

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
