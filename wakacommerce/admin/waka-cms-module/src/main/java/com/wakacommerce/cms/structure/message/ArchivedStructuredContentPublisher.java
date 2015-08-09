 
package com.wakacommerce.cms.structure.message;

import com.wakacommerce.cms.structure.domain.StructuredContent;

/**
 *
 * @ hui
 */
public interface ArchivedStructuredContentPublisher {
    void processStructuredContentArchive(StructuredContent sc, String baseTypeKey, String baseNameKey);
}
