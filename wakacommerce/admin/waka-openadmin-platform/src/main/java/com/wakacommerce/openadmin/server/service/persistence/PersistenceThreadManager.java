
package com.wakacommerce.openadmin.server.service.persistence;

import org.springframework.stereotype.Service;

/**
 *Jeff Fischer
 */
@Service("blPersistenceThreadManager")
public class PersistenceThreadManager {

    public <T, G extends Throwable> T operation(TargetModeType targetModeType, Persistable<T, G> persistable) throws G {
        try {
            PersistenceManagerFactory.startPersistenceManager(targetModeType);
            return persistable.execute();
        } finally {
            PersistenceManagerFactory.endPersistenceManager();
        }
    }
}
