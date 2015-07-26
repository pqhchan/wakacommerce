
package com.wakacommerce.core.web.controller.catalog;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.template.TemplateOverrideExtensionManager;
import com.wakacommerce.common.template.TemplateType;
import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.common.web.TemplateTypeAware;
import com.wakacommerce.common.web.controller.BroadleafAbstractController;
import com.wakacommerce.common.web.deeplink.DeepLinkService;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchResult;
import com.wakacommerce.core.search.service.SearchService;
import com.wakacommerce.core.web.catalog.CategoryHandlerMapping;
import com.wakacommerce.core.web.service.SearchFacetDTOService;
import com.wakacommerce.core.web.util.ProcessorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class works in combination with the CategoryHandlerMapping which finds a category based upon
 * the passed in URL.
 *
 *bpolster
 */
public class BroadleafCategoryController extends BroadleafAbstractController implements Controller, TemplateTypeAware {
    
    protected static String defaultCategoryView = "catalog/category";
    protected static String CATEGORY_ATTRIBUTE_NAME = "category";  
    protected static String PRODUCTS_ATTRIBUTE_NAME = "products";  
    protected static String SKUS_ATTRIBUTE_NAME = "skus";
    protected static String FACETS_ATTRIBUTE_NAME = "facets";  
    protected static String PRODUCT_SEARCH_RESULT_ATTRIBUTE_NAME = "result";  
    protected static String ACTIVE_FACETS_ATTRIBUTE_NAME = "activeFacets";  
    protected static String ALL_PRODUCTS_ATTRIBUTE_NAME = "blcAllDisplayedProducts";
    protected static String ALL_SKUS_ATTRIBUTE_NAME = "blcAllDisplayedSkus";
    
    @Resource(name = "blSearchService")
    protected SearchService searchService;
    
    @Resource(name = "blSearchFacetDTOService")
    protected SearchFacetDTOService facetService;
    
    @Autowired(required = false)
    @Qualifier("blCategoryDeepLinkService")
    protected DeepLinkService<Category> deepLinkService;

    @Resource(name = "blTemplateOverrideExtensionManager")
    protected TemplateOverrideExtensionManager templateOverrideManager;

    @Override
    @SuppressWarnings("unchecked")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        
        if (request.getParameterMap().containsKey("facetField")) {
            // If we receive a facetField parameter, we need to convert the field to the 
            // product search criteria expected format. This is used in multi-facet selection. We 
            // will send a redirect to the appropriate URL to maintain canonical URLs
            
            String fieldName = request.getParameter("facetField");
            List<String> activeFieldFilters = new ArrayList<String>();
            Map<String, String[]> parameters = new HashMap<String, String[]>(request.getParameterMap());
            
            for (Iterator<Entry<String,String[]>> iter = parameters.entrySet().iterator(); iter.hasNext();){
                Map.Entry<String, String[]> entry = iter.next();
                String key = entry.getKey();
                if (key.startsWith(fieldName + "-")) {
                    activeFieldFilters.add(key.substring(key.indexOf('-') + 1));
                    iter.remove();
                }
            }
            
            parameters.remove(SearchCriteria.PAGE_NUMBER);
            parameters.put(fieldName, activeFieldFilters.toArray(new String[activeFieldFilters.size()]));
            parameters.remove("facetField");
            
            String newUrl = ProcessorUtils.getUrl(request.getRequestURL().toString(), parameters);
            model.setViewName("redirect:" + newUrl);
        } else {
            // Else, if we received a GET to the category URL (either the user clicked this link or we redirected
            // from the POST method, we can actually process the results
            
            Category category = (Category) request.getAttribute(CategoryHandlerMapping.CURRENT_CATEGORY_ATTRIBUTE_NAME);
            assert(category != null);
            
            List<SearchFacetDTO> availableFacets = getSearchService().getCategoryFacets(category);
            SearchCriteria searchCriteria = facetService.buildSearchCriteria(request, availableFacets);
            
            String searchTerm = request.getParameter(SearchCriteria.QUERY_STRING);
            SearchResult result;
            if (StringUtils.isNotBlank(searchTerm)) {
                result = getSearchService().findSearchResultsByCategoryAndQuery(category, searchTerm, searchCriteria);
            } else {
                result = getSearchService().findSearchResultsByCategory(category, searchCriteria);
            }
            
            facetService.setActiveFacetResults(result.getFacets(), request);
            
            model.addObject(CATEGORY_ATTRIBUTE_NAME, category);
            model.addObject(PRODUCTS_ATTRIBUTE_NAME, result.getProducts());
            model.addObject(SKUS_ATTRIBUTE_NAME, result.getSkus());
            model.addObject(FACETS_ATTRIBUTE_NAME, result.getFacets());
            model.addObject(PRODUCT_SEARCH_RESULT_ATTRIBUTE_NAME, result);
            model.addObject("BLC_PAGE_TYPE", "category");
            if (result.getProducts() != null) {
                model.addObject(ALL_PRODUCTS_ATTRIBUTE_NAME, new HashSet<Product>(result.getProducts()));
            }
            
            if (result.getSkus() != null) {
                model.addObject(ALL_SKUS_ATTRIBUTE_NAME, new HashSet<Sku>(result.getSkus()));
            }

            addDeepLink(model, deepLinkService, category);
            
            String templatePath = null;
            
            // Use the categories custom template if available
            if (StringUtils.isNotBlank(category.getDisplayTemplate())) {
                templatePath = category.getDisplayTemplate();
            } else {
                // Otherwise, use the controller default.
                templatePath = getDefaultCategoryView();
            }

            // Allow extension managers to override.
            ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
            ExtensionResultStatusType extResult = templateOverrideManager.getProxy().getOverrideTemplate(erh, category);
            if (extResult != ExtensionResultStatusType.NOT_HANDLED) {
                templatePath = erh.getResult();
            }
            
            model.setViewName(templatePath);
        }
        return model;
    }

    public String getDefaultCategoryView() {
        return defaultCategoryView;
    }

    protected SearchService getSearchService() {
        return searchService;
    }

    @Override
    public String getExpectedTemplateName(HttpServletRequest request) {
        BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
        if (context != null) {
            Category category = (Category) context.getRequest().getAttribute(CATEGORY_ATTRIBUTE_NAME);
            if (category != null && category.getDisplayTemplate() != null) {
                return category.getDisplayTemplate();
            }
        }
        return getDefaultCategoryView();
    }

    @Override
    public TemplateType getTemplateType(HttpServletRequest request) {
        return TemplateType.CATEGORY;
    }

}
