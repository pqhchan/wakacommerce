
package com.wakacommerce.openadmin.dto.visitor;

import java.io.Serializable;

import com.wakacommerce.openadmin.dto.AdornedTargetCollectionMetadata;
import com.wakacommerce.openadmin.dto.BasicCollectionMetadata;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.MapMetadata;

/**
 *Jeff Fischer
 */
public class MetadataVisitorAdapter implements MetadataVisitor, Serializable {

    @Override
    public void visit(AdornedTargetCollectionMetadata metadata) {
        throw new IllegalArgumentException("Not supported in this context");
    }

    @Override
    public void visit(BasicFieldMetadata metadata) {
        throw new IllegalArgumentException("Not supported in this context");
    }

    @Override
    public void visit(BasicCollectionMetadata metadata) {
        throw new IllegalArgumentException("Not supported in this context");
    }

    @Override
    public void visit(MapMetadata metadata) {
        throw new IllegalArgumentException("Not supported in this context");
    }
}
