package com.wakacommerce.cms.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.file.service.StaticAssetPathService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class HrefUrlRewriteProcessor extends UrlRewriteProcessor {
    
    @Resource(name = "blStaticAssetPathService")
    protected StaticAssetPathService staticAssetPathService;

    private static final String LINK = "link";
    private static final String HREF = "href";

    public HrefUrlRewriteProcessor() {
        super(HREF);
    }

    @Override
    protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
        Map<String, String> attrs = new HashMap<String, String>();
        
        String elementName = element.getNormalizedName();
        String useCDN = element.getAttributeValue("useCDN");

        if (LINK.equals(elementName) || (useCDN != null && "true".equals(useCDN))) {
            attrs = super.getModifiedAttributeValues(arguments, element, attributeName);
            String srcAttr = attrs.remove("src");
            attrs.put(HREF, srcAttr);
        } else {
            attrs.put(HREF, element.getAttributeValue(attributeName));
        }
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
