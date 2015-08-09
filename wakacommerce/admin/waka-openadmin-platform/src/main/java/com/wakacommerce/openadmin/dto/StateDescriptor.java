
package com.wakacommerce.openadmin.dto;

import java.util.Map;

/**
 *
 * @ hui
 */
public interface StateDescriptor {

    Property[] getProperties();

    Property findProperty(String name);

    Map<String, Property> getPMap();

}
