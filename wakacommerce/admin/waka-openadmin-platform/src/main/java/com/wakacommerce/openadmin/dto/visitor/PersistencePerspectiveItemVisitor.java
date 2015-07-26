
package com.wakacommerce.openadmin.dto.visitor;

import com.wakacommerce.openadmin.dto.AdornedTargetList;
import com.wakacommerce.openadmin.dto.ForeignKey;
import com.wakacommerce.openadmin.dto.MapStructure;
import com.wakacommerce.openadmin.dto.SimpleValueMapStructure;


public interface PersistencePerspectiveItemVisitor {

    public void visit(AdornedTargetList adornedTargetList);
    
    public void visit(MapStructure mapStructure);
    
    public void visit(SimpleValueMapStructure simpleValueMapStructure);
    
    public void visit(ForeignKey foreignKey);
    
}
