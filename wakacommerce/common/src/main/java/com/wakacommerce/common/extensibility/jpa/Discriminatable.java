
package com.wakacommerce.common.extensibility.jpa;

import java.io.Serializable;

/**
 * Marker interface for a class that is included in one way or another in the multitenant architecture. If a class
 * does not implement this interface, there is no dispensation for it across sites.
 *
 *Jeff Fischer
 */
public interface Discriminatable extends Serializable {
}
