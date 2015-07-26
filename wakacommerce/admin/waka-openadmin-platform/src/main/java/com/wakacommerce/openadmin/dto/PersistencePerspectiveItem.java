
package com.wakacommerce.openadmin.dto;

import java.io.Serializable;

import com.wakacommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor;

/**
 * Simple marker interface for persistence perspective members
 * 
 *jfischer
 *
 */
public interface PersistencePerspectiveItem extends Serializable {

    public void accept(PersistencePerspectiveItemVisitor visitor);

    public PersistencePerspectiveItem clonePersistencePerspectiveItem();

}
