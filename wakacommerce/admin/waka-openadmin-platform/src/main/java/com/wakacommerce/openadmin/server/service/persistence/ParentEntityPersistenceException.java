  
package com.wakacommerce.openadmin.server.service.persistence;

/**
 *
 * @ hui
 */
public class ParentEntityPersistenceException extends PersistenceException {

    public ParentEntityPersistenceException(Throwable cause) {
        super(cause);
    }

    public ParentEntityPersistenceException(String message) {
        super(message);
    }

    public ParentEntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
