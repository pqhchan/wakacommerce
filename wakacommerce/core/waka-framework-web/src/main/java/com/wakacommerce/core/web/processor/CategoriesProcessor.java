
package com.wakacommerce.core.web.processor;

import org.apache.commons.lang.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategoryXref;
import com.wakacommerce.core.catalog.service.CatalogService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

/**
 * A Thymeleaf processor that will add the desired categories to the model. It does this by
 * searching for the <b>parentCategory</b> by name> and adding up to <b>maxResults</b> subcategories under
 * the model attribute specified by <b>resultVar</b>
 * 
 * @param parentCategory (required) the name of the parent category to get subcategories from
 * @param resultVar (required) the model variable that the resulting list of categories should be set to
 * @param maxResults (optional) the maximum number of categories to return
 * 
 *apazzolini
 */
public class CategoriesProcessor extends AbstractModelVariableModifierProcessor {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Resource(name = "blCategoriesProcessorExtensionManager")
    protected CategoriesProcessorExtensionManager extensionManager;

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     */
    public CategoriesProcessor() {
        super("categories");
    }
    
    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        String resultVar = element.getAttributeValue("resultVar");
        String parentCategory = element.getAttributeValue("parentCategory");
        String unparsedMaxResults = element.getAttributeValue("maxResults");

        if (extensionManager != null) {
            ExtensionResultHolder holder = new ExtensionResultHolder();
            ExtensionResultStatusType result = extensionManager.getProxy().findAllPossibleChildCategories(parentCategory, unparsedMaxResults, holder);
            if (ExtensionResultStatusType.HANDLED.equals(result)) {
                addToModel(arguments, resultVar, holder.getResult());
                return;
            }
        }

        // TODO: Potentially write an algorithm that will pick the minimum depth category
        // instead of the first category in the list
        List<Category> categories = catalogService.findCategoriesByName(parentCategory);
        if (categories != null && categories.size() > 0) {
            // gets child categories in order ONLY if they are in the xref table and active
            List<CategoryXref> subcategories = categories.get(0).getChildCategoryXrefs();
            List<Category> results = Collections.emptyList();
            if (subcategories != null && !subcategories.isEmpty()) {
                results = new ArrayList<Category>(subcategories.size());
                if (StringUtils.isNotEmpty(unparsedMaxResults)) {
                    int maxResults = Integer.parseInt(unparsedMaxResults);
                    if (subcategories.size() > maxResults) {
                        subcategories = subcategories.subList(0, maxResults);
                    }
                }
                
                for (CategoryXref xref : subcategories) {
                    results.add(xref.getSubCategory());
                }
            }
            
            addToModel(arguments, resultVar, results);
        }
    }
}
