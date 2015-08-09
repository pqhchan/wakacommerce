
package com.wakacommerce.core.web.search;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.lang.ArrayUtils;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Product;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public class SearchFilterUtil {

    public static void filterProducts(List<Product> products,  Map<String, String[]>parameters, String[] allowedParameters) {
        for (String parameter : allowedParameters) {
            BeanToPropertyValueTransformer reader = new BeanToPropertyValueTransformer(parameter, true);
            if (parameters.containsKey(parameter)) { // we're doing a multi-select
                for (Iterator<Product> itr = products.iterator(); itr.hasNext(); ) {
                    Product product = itr.next();
                    if (!ArrayUtils.contains(parameters.get(parameter), reader.transform(product).toString())) {
                        itr.remove();
                    }
                }
            } else if (parameters.containsKey("min-"+parameter)) {
                String minMoney = parameters.get("min-" + parameter)[0];
                String maxMoney = parameters.get("max-" + parameter)[0];
                Money minimumMoney = new Money(minMoney.replaceAll("[^0-9.]", ""));
                Money maximumMoney = new Money(maxMoney.replaceAll("[^0-9.]", ""));
                for (Iterator<Product> itr = products.iterator(); itr.hasNext();) {
                    Product product = itr.next();
                    Money objectValue = (Money) reader.transform(product);
                    if (objectValue.lessThan(minimumMoney) || objectValue.greaterThan(maximumMoney)) {
                        itr.remove();
                    }
                }
            }
        }
    }
}
