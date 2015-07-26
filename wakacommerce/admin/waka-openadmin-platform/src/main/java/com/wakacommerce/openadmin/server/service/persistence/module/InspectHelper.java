
package com.wakacommerce.openadmin.server.service.persistence.module;

import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.MergedPropertyType;
import com.wakacommerce.openadmin.dto.PersistencePerspective;

import java.util.Map;

/**
 * 
 *jfischer
 *
 */
public interface InspectHelper {

    public ClassMetadata getMergedClassMetadata(Class<?>[] entities, Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties);
    
    public Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective);

    public PersistenceModule getCompatibleModule(OperationType operationType);
    
}
