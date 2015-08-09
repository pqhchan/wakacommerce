package com.wakacommerce.cms.web.processor;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.Assignation;
import org.thymeleaf.standard.expression.AssignationSequence;
import org.thymeleaf.standard.expression.AssignationUtils;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.google.common.primitives.Ints;
import com.wakacommerce.cms.file.service.StaticAssetService;
import com.wakacommerce.cms.structure.domain.StructuredContentType;
import com.wakacommerce.cms.structure.service.StructuredContentService;
import com.wakacommerce.cms.web.deeplink.ContentDeepLinkServiceImpl;
import com.wakacommerce.common.RequestDTO;
import com.wakacommerce.common.TimeDTO;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.deeplink.DeepLink;
import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class ContentProcessor extends AbstractModelVariableModifierProcessor {

    protected final Log LOG = LogFactory.getLog(getClass());
    public static final String REQUEST_DTO = "blRequestDTO";
    public static final String BLC_RULE_MAP_PARAM = "blRuleMap";
    
    @Resource(name = "blStructuredContentService")
    protected StructuredContentService structuredContentService;
    
    @Resource(name = "blStaticAssetService")
    protected StaticAssetService staticAssetService;

    @Resource(name = "blContentProcessorExtensionManager")
    protected ContentProcessorExtensionManager extensionManager;
    
    @Resource(name = "blContentDeepLinkService")
    protected ContentDeepLinkServiceImpl contentDeepLinkService;
    
    public ContentProcessor() {
        super("content");
    }
    
    public ContentProcessor(String elementName) {
        super(elementName);
    }
    
    @Override
    public int getPrecedence() {
        return 10000;
    }
    
    protected String getAttributeValue(Element element, String valueName, String defaultValue) {
        String returnValue = element.getAttributeValue(valueName);
        if (returnValue == null) {
            return defaultValue;
        } else {
            return returnValue;
        }
    }   

    @Override
    protected void modifyModelAttributes(final Arguments arguments, Element element) {        
        String contentType = element.getAttributeValue("contentType");
        String contentName = element.getAttributeValue("contentName");
        String maxResultsStr = element.getAttributeValue("maxResults");

        if (StringUtils.isEmpty(contentType) && StringUtils.isEmpty(contentName)) {
            throw new IllegalArgumentException("The content processor must have a non-empty attribute value for 'contentType' or 'contentName'");
        }

        Integer maxResults = null;
        if (maxResultsStr != null) {
            maxResults = Ints.tryParse(maxResultsStr);
        }
        if (maxResults == null) {
            maxResults = Integer.MAX_VALUE;
        }
        
        String contentListVar = getAttributeValue(element, "contentListVar", "contentList");
        String contentItemVar = getAttributeValue(element, "contentItemVar", "contentItem");
        String numResultsVar = getAttributeValue(element, "numResultsVar", "numResults");
        
        String fieldFilters = element.getAttributeValue("fieldFilters");
        final String sorts = element.getAttributeValue("sorts");

        IWebContext context = (IWebContext) arguments.getContext();     
        HttpServletRequest request = context.getHttpServletRequest();   
        WakaRequestContext blcContext = WakaRequestContext.getWakaRequestContext();
        
        Map<String, Object> mvelParameters = buildMvelParameters(request, arguments, element);
        SandBox currentSandbox = blcContext.getSandBox();

        List<StructuredContentDTO> contentItems;
        StructuredContentType structuredContentType = null;
        if (contentType != null ) {
            structuredContentType = structuredContentService.findStructuredContentTypeByName(contentType);
        }

        contentItems = getContentItems(
        		contentName, 
        		maxResults, 
        		request, 
        		mvelParameters, 
        		currentSandbox, 
        		structuredContentType, 
        		arguments, 
        		element
        );
        
        if (contentItems.size() > 0) {
            
            // sort the resulting list by the configured property sorts on the tag
            if (StringUtils.isNotEmpty(sorts)) {
                Collections.sort(contentItems, new Comparator<StructuredContentDTO>() {
                    @Override
                    public int compare(StructuredContentDTO o1, StructuredContentDTO o2) {
                        AssignationSequence sortAssignments = AssignationUtils.parseAssignationSequence(arguments.getConfiguration(), arguments, sorts, false);
                        CompareToBuilder compareBuilder = new CompareToBuilder();
                        for (Assignation sortAssignment : sortAssignments) {
                            String property = sortAssignment.getLeft().getStringRepresentation();
                            
                            Object val1 = o1.getPropertyValue(property);
                            Object val2 = o2.getPropertyValue(property);
                            
                            if (sortAssignment.getRight().execute(arguments.getConfiguration(), arguments).equals("ASCENDING")) {
                                compareBuilder.append(val1, val2);
                            } else {
                                compareBuilder.append(val2, val1);
                            }
                        }
                        return compareBuilder.toComparison();
                    }
                });
            }
            
            List<Map<String, Object>> contentItemFields = new ArrayList<Map<String, Object>>();          
            
            for (StructuredContentDTO item : contentItems) {
                if (StringUtils.isNotEmpty(fieldFilters)) {
                    AssignationSequence assignments = AssignationUtils.parseAssignationSequence(arguments.getConfiguration(), arguments, fieldFilters, false);
                    boolean valid = true;
                    for (Assignation assignment : assignments) {
                        
                        if (ObjectUtils.notEqual(assignment.getRight().execute(arguments.getConfiguration(), arguments),
                                                item.getValues().get(assignment.getLeft().getStringRepresentation()))) {
                            LOG.info("Excluding content " + item.getId()  + " based on the property value of " + assignment.getLeft().getStringRepresentation());
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        contentItemFields.add(item.getValues());
                    }
                } else {
                    contentItemFields.add(item.getValues());
                }
            }

            Map<String, Object> contentItem = null;
            if (contentItemFields.size() > 0) {
                contentItem = contentItemFields.get(0);
            }

            addToModel(arguments, contentItemVar, contentItem);
            addToModel(arguments, contentListVar, contentItemFields);
            addToModel(arguments, numResultsVar, contentItems.size());
        } else {
            if (LOG.isInfoEnabled()) {
                LOG.info("**************************The contentItems is null*************************");
            }
            addToModel(arguments, contentItemVar, null);
            addToModel(arguments, contentListVar, null);
            addToModel(arguments, numResultsVar, 0);
        }       
        
        String deepLinksVar = element.getAttributeValue("deepLinks");
        if (StringUtils.isNotBlank(deepLinksVar) && contentItems.size() > 0 ) {
            List<DeepLink> links = contentDeepLinkService.getLinks(contentItems.get(0));
            extensionManager.getProxy().addExtensionFieldDeepLink(links, arguments, element);
            extensionManager.getProxy().postProcessDeepLinks(links);
            addToModel(arguments, deepLinksVar, links);
        }
    }

    protected List<StructuredContentDTO> getContentItems(
    		String contentName, 
    		Integer maxResults, 
    		HttpServletRequest request,
            Map<String, Object> mvelParameters,
            SandBox currentSandbox,
            StructuredContentType structuredContentType,
            Arguments arguments, 
            Element element) {
        
    	List<StructuredContentDTO> contentItems;
        if (structuredContentType == null) {
            contentItems = structuredContentService.lookupStructuredContentItemsByName(contentName, maxResults, mvelParameters, isSecure(request));
        } else {
            if (contentName == null || "".equals(contentName)) {
                contentItems = structuredContentService.lookupStructuredContentItemsByType(structuredContentType, maxResults, mvelParameters, isSecure(request));
            } else {
                contentItems = structuredContentService.lookupStructuredContentItemsByName(structuredContentType, contentName, maxResults, mvelParameters, isSecure(request));
            }
        }

        //add additional fields to the model
        extensionManager.getProxy().addAdditionalFieldsToModel(arguments, element);

        return contentItems;
    }

    protected Map<String, Object> buildMvelParameters(HttpServletRequest request, Arguments arguments, Element element) {
        TimeZone timeZone = WakaRequestContext.getWakaRequestContext().getTimeZone();

        final TimeDTO timeDto;
        if (timeZone != null) {
            timeDto = new TimeDTO(SystemTime.asCalendar(timeZone));
        } else {
            timeDto = new TimeDTO();
        }

        RequestDTO requestDto = (RequestDTO) request.getAttribute(REQUEST_DTO);

        Map<String, Object> mvelParameters = new HashMap<String, Object>();
        mvelParameters.put("time", timeDto);
        mvelParameters.put("request", requestDto);

        String productString = element.getAttributeValue("product");

        if (productString != null) {
            final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(arguments.getConfiguration());
            Expression expression = (Expression) expressionParser.parseExpression(arguments.getConfiguration(), arguments, productString);
            Object product = expression.execute(arguments.getConfiguration(), arguments);

            if (product != null) {
                mvelParameters.put("product", product);
            }
        }

        String categoryString = element.getAttributeValue("category");

        if (categoryString != null) {
            final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(arguments.getConfiguration());
            Expression expression = (Expression) expressionParser.parseExpression(arguments.getConfiguration(), arguments, productString);
            Object category = expression.execute(arguments.getConfiguration(), arguments);
            if (category != null) {
                mvelParameters.put("category", category);
            }
        }

        @SuppressWarnings("unchecked")
        Map<String,Object> blcRuleMap = (Map<String,Object>) request.getAttribute(BLC_RULE_MAP_PARAM);
        if (blcRuleMap != null) {
            for (String mapKey : blcRuleMap.keySet()) {
                mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
            }
        }

        return mvelParameters;
    }   
    
    public boolean isSecure(HttpServletRequest request) {
        boolean secure = false;
        if (request != null) {
             secure = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
        }
        return secure;
    }
    
}
