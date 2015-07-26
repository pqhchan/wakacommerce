
package com.wakacommerce.openadmin.dto.visitor;

import com.wakacommerce.openadmin.dto.AdornedTargetList;
import com.wakacommerce.openadmin.dto.ForeignKey;
import com.wakacommerce.openadmin.dto.MapStructure;
import com.wakacommerce.openadmin.dto.SimpleValueMapStructure;


public class PersistencePerspectiveItemVisitorAdapter implements PersistencePerspectiveItemVisitor {

    @Override
    public void visit(AdornedTargetList adornedTargetList) {
        //do nothing
    }

    @Override
    public void visit(MapStructure mapStructure) {
        //do nothing
    }

    @Override
    public void visit(SimpleValueMapStructure simpleValueMapStructure) {
        //do nothing
    }

    @Override
    public void visit(ForeignKey foreignKey) {
        //do nothing
    }
}
