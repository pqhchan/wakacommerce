
package com.wakacommerce.openadmin.server.service.persistence.module.criteria;

/**
 * Factory that provide {@link com.wakacommerce.openadmin.server.service.persistence.module.criteria.Restriction} instances
 * based on the type provided ({@link com.wakacommerce.openadmin.server.service.persistence.module.criteria.RestrictionType}).
 *
 *Jeff Fischer
 */
public interface RestrictionFactory {

    Restriction getRestriction(String type, String propertyId);

}
