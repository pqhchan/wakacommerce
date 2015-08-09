
package com.wakacommerce.openadmin.dto.visitor;

import com.wakacommerce.openadmin.dto.AdornedTargetCollectionMetadata;
import com.wakacommerce.openadmin.dto.BasicCollectionMetadata;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.MapMetadata;

/**
 *
 * @ hui
 */
public interface MetadataVisitor {

    public void visit(BasicFieldMetadata metadata);

    public void visit(BasicCollectionMetadata metadata);

    public void visit(AdornedTargetCollectionMetadata metadata);

    public void visit(MapMetadata metadata);
}
