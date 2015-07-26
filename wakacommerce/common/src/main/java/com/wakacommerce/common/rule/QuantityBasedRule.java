
package com.wakacommerce.common.rule;

import java.io.Serializable;

/**
 * Represents a class containing an MVEL rule and an associated quantity.
 *
 *Jeff Fischer
 */
public interface QuantityBasedRule extends Serializable {

    /**
     * The quantity for which a match must be found using the rule. This generally
     * equates to order item quantity (e.g. 2 shirts matching the rule are required in order to receive a discount)
     *
     * @return the quantity of matches required
     */
    public Integer getQuantity();

    /**
     * The quantity for which a match must be found using the rule. This generally
     * equates to order item quantity (e.g. 2 shirts matching the rule are required in order to receive a discount)
     *
     * @param quantity the quantity of matches required
     */
    public void setQuantity(Integer quantity);

    /**
     * The rule in the form of an MVEL expression
     *
     * @return the rule as an MVEL string
     */
    public String getMatchRule();

    /**
     * Sets the match rule used to test this item.
     *
     * @param matchRule the rule as an MVEL string
     */
    public void setMatchRule(String matchRule);

    /**
     * The primary key value for this rule object
     *
     * @return the primary key value
     */
    public Long getId();

    /**
     * The primary key value for this rule object
     *
     * @param id the primary key value
     */
    public void setId(Long id);

}
