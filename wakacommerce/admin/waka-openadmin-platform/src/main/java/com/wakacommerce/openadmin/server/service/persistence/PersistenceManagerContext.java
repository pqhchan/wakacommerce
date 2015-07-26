
package com.wakacommerce.openadmin.server.service.persistence;

import java.util.Stack;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;

/**
 *Jeff Fischer
 */
public class PersistenceManagerContext {

    private static final ThreadLocal<PersistenceManagerContext> BROADLEAF_PERSISTENCE_MANAGER_CONTEXT = ThreadLocalManager.createThreadLocal(PersistenceManagerContext.class, false);

    public static PersistenceManagerContext getPersistenceManagerContext() {
        return BROADLEAF_PERSISTENCE_MANAGER_CONTEXT.get();
    }

    public static void addPersistenceManagerContext(PersistenceManagerContext persistenceManagerContext) {
        BROADLEAF_PERSISTENCE_MANAGER_CONTEXT.set(persistenceManagerContext);
    }

    private static void clear() {
        BROADLEAF_PERSISTENCE_MANAGER_CONTEXT.remove();
    }

    private final Stack<PersistenceManager> persistenceManager = new Stack<PersistenceManager>();

    public void addPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager.add(persistenceManager);
    }

    public PersistenceManager getPersistenceManager() {
        return !persistenceManager.empty()?persistenceManager.peek():null;
    }

    public void remove() {
        if (!persistenceManager.empty()) {
            persistenceManager.pop();
        }
        if (persistenceManager.empty()) {
            PersistenceManagerContext.clear();
        }
    }
}
