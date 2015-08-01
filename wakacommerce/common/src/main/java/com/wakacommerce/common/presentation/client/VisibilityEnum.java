
package com.wakacommerce.common.presentation.client;

/**
 * Created by IntelliJ IDEA.
 * User:   
 * Date: 9/27/11
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public enum VisibilityEnum {
    HIDDEN_ALL,
    VISIBLE_ALL,
    FORM_HIDDEN,
    GRID_HIDDEN,
    NOT_SPECIFIED,
    /**
     * This will ensure that the field is shown on the the entity form regardless of whether or not this field is
     * actually a member of the entity. Mainly used in {@link CustomPersistenceHandler}s for psuedo-fields that are built
     * manually and you still want user input from (like selecting {@link ProductOption}s to associate to a {@link Sku}
     */
    FORM_EXPLICITLY_SHOWN
}
