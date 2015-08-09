
package com.wakacommerce.openadmin.server.service.module;

import java.util.Map;

import com.wakacommerce.openadmin.dto.PersistencePackage;

/**
 *
 * @ hui
 */
public interface ReconstituteForAddHandler {

    void reconstitutePreviousAddForReplay(Map<Class<?>, Map<String,String>> library, PersistencePackage replayPackage);

    void reconstitutePreviousAddForUpdateReplay(Map<Class<?>, Map<String, String>> library, PersistencePackage replayPackage);

}
