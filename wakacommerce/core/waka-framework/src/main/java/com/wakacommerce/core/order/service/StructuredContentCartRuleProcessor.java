
package com.wakacommerce.core.order.service;

import java.util.List;
import java.util.Map;

import com.wakacommerce.common.structure.dto.ItemCriteriaDTO;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.profile.core.domain.Customer;

/**
 * Created by bpolster.
 */
public class StructuredContentCartRuleProcessor extends AbstractCartRuleProcessor<StructuredContentDTO> {

    /**
     * Expects to find a valid "Customer" in the valueMap.
     * Uses the customer to locate the cart and then loops through the items in the current
     * cart and checks to see if the cart items rules are met.
     *
     * @param sc
     */
    @Override
    public boolean checkForMatch(StructuredContentDTO sc, Map<String, Object> valueMap) {
        List<ItemCriteriaDTO> itemCriterias = sc.getItemCriteriaDTOList();

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
