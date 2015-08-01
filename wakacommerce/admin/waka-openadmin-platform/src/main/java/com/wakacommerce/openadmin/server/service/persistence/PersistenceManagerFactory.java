
package com.wakacommerce.openadmin.server.service.persistence;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
@Service("blPersistenceManagerFactory")
public class PersistenceManagerFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static final Map<TargetModeType, PersistenceManager> persistenceManagers = new HashMap<TargetModeType, PersistenceManager>();
    public static final String DEFAULTPERSISTENCEMANAGERREF = "blPersistenceManager";
    protected static String persistenceManagerRef = DEFAULTPERSISTENCEMANAGERREF;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PersistenceManagerFactory.applicationContext = applicationContext;
    }

    public static PersistenceManager getPersistenceManager() {
        if (PersistenceManagerContext.getPersistenceManagerContext() != null) {
            return PersistenceManagerContext.getPersistenceManagerContext().getPersistenceManager();
        }
        throw new IllegalStateException("PersistenceManagerContext is not set on ThreadLocal. If you want to use the " +
                "non-cached version, try getPersistenceManager(TargetModeType)");
    }

    public static PersistenceManager getPersistenceManager(TargetModeType targetModeType) {
        synchronized (persistenceManagers) {
            if (!persistenceManagers.containsKey(targetModeType)) {
                PersistenceManager persistenceManager = (PersistenceManager) applicationContext.getBean(persistenceManagerRef);
                persistenceManager.setTargetMode(targetModeType);
                persistenceManagers.put(targetModeType, persistenceManager);
            }
            return persistenceManagers.get(targetModeType);
        }
    }

    public static boolean isPersistenceManagerActive() {
        return applicationContext.containsBean(getPersistenceManagerRef());
    }

    public static void startPersistenceManager(TargetModeType targetModeType) {
        PersistenceManagerContext context = PersistenceManagerContext.getPersistenceManagerContext();
        if (context == null) {
            context = new PersistenceManagerContext();
            PersistenceManagerContext.addPersistenceManagerContext(context);
        }
        context.addPersistenceManager(getPersistenceManager(targetModeType));
    }

    public static void endPersistenceManager() {
        PersistenceManagerContext context = PersistenceManagerContext.getPersistenceManagerContext();
        if (context != null) {
            context.remove();
        }
    }

    public static String getPersistenceManagerRef() {
        return persistenceManagerRef;
    }

    public static void setPersistenceManagerRef(String persistenceManagerRef) {
        PersistenceManagerFactory.persistenceManagerRef = persistenceManagerRef;
    }
}
