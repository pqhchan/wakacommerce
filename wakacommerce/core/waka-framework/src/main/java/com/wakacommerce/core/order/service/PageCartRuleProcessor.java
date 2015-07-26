
package com.wakacommerce.core.order.service;

import java.util.List;
import java.util.Map;

import com.wakacommerce.common.page.dto.PageDTO;
import com.wakacommerce.common.structure.dto.ItemCriteriaDTO;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.profile.core.domain.Customer;

/**
 * Created by bpolster.
 */
public class PageCartRuleProcessor extends AbstractCartRuleProcessor<PageDTO> {

    /**
     * Expects to find a valid "Customer" in the valueMap.
     * Uses the customer to locate the cart and then loops through the items in the current
     * cart and checks to see if the cart items rules are met.
     *
     * @param sc
     */
    @Override
    public boolean checkForMatch(PageDTO page, Map<String, Object> valueMap) {
        List<ItemCriteriaDTO> itemCriterias = page.getItemCriteriaDTOList();

        if (itemCriterias != null && itemCriterias.size() > 0) {
            Order order = lookupOrderForCustomer((Customer) valueMap.get("customer"));

            if (order == null || order.getOrderItems() == null || order.getOrderItems().size() < 1) {
                return false;
            }

            for (ItemCriteriaDTO itemCriteria : itemCriterias) {
                if (! checkItemCriteria(itemCriteria, order.getOrderItems())) {
                    // Item criteria check failed.
                    return false;
                }
            }
        }

        return true;
    }

}
