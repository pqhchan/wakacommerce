
package com.wakacommerce.openadmin.server.service.persistence.module.criteria;

/**
 *
 * @ hui
 */
public interface RestrictionFactory {

    Restriction getRestriction(String type, String propertyId);

}
