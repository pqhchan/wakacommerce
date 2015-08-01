
package com.wakacommerce.common.persistence;

/**
 * Interface that denotes whether or not an entity is archived. Usually, entities that implement this interface also only
 * undergo soft-deletes.
 * 
 * 
 */
public interface Status {

    public void setArchived(Character archived);

    public Character getArchived();

    public boolean isActive();

}
