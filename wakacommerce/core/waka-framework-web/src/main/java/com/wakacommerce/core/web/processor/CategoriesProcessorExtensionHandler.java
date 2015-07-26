
package com.wakacommerce.core.web.processor;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Category;

import java.util.List;

/**
 *Jeff Fischer
 */
public interface CategoriesProcessorExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType findAllPossibleChildCategories(String parentCategory, String maxResults, ExtensionResultHolder<List<Category>> resultHolder);

}
