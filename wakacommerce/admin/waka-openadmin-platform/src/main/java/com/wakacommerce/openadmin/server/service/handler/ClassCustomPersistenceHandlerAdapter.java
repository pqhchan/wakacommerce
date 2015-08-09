
package com.wakacommerce.openadmin.server.service.handler;

import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.openadmin.dto.PersistencePackage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ hui
 */
public class ClassCustomPersistenceHandlerAdapter extends CustomPersistenceHandlerAdapter {
    
    List<Class<?>> handledClasses = new ArrayList<Class<?>>();
    
    public ClassCustomPersistenceHandlerAdapter(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            handledClasses.add(clazz);
        }
    }
    
    protected boolean classMatches(PersistencePackage pkg) {
        String ceilingEntityFullyQualifiedClassname = pkg.getCeilingEntityFullyQualifiedClassname();
        for (Class<?> clazz : handledClasses) {
            if (clazz.getName().equals(ceilingEntityFullyQualifiedClassname)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isBasicOperation(PersistencePackage pkg) {
        return pkg.getPersistencePerspective().getOperationTypes().getAddType().equals(OperationType.BASIC);
    }
    
    protected boolean isMapOperation(PersistencePackage pkg) {
        return pkg.getPersistencePerspective().getOperationTypes().getAddType().equals(OperationType.MAP);
    }

    protected boolean isAdornedListperation(PersistencePackage pkg) {
        return pkg.getPersistencePerspective().getOperationTypes().getAddType().equals(OperationType.ADORNEDTARGETLIST);
    }

}
