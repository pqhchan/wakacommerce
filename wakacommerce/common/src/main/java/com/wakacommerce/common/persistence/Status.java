
package com.wakacommerce.common.persistence;

/**
 *
 * @ hui
 */
public interface Status {

    public void setArchived(Character archived);

    public Character getArchived();

    public boolean isActive();

}
