
package com.wakacommerce.common.template;

/**
 * Interface used to indicate that an entity contains a path to a preferred display template.
 * 
 *bpolster
 *
 */
public interface TemplatePathContainer {

    /**
     * Returns the path to a display template.
     * @return
     */
    String getDisplayTemplate();
}
