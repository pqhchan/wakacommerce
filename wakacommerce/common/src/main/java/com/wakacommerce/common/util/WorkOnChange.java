
package com.wakacommerce.common.util;

import java.util.Collection;

/**
 * Encapsulate some amount of work to perform whenever a change aware collection is modified.
 *
 * @see com.wakacommerce.common.util.BLCCollectionUtils#createChangeAwareCollection(WorkOnChange, java.util.Collection)
 * 
 */
public interface WorkOnChange {

    /**
     * An implementation of this method will be called whenever a change is detected on a change aware collection. The implementation
     * should contain whatever code is necessary to respond to the collection change.
     *
     * @param changed the un-proxied collection that was modified
     */
    void doWork(Collection changed);

}
