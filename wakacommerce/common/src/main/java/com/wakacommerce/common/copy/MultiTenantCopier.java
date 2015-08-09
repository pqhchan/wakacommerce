
package com.wakacommerce.common.copy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;

import com.wakacommerce.common.exception.ExceptionHelper;
import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.service.GenericEntityService;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.util.StreamCapableTransactionalOperationAdapter;
import com.wakacommerce.common.util.StreamingTransactionCapableUtil;
import com.wakacommerce.common.util.tenant.IdentityExecutionUtils;
import com.wakacommerce.common.util.tenant.IdentityOperation;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.EnforceEnterpriseCollectionBehaviorState;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @ hui
 */
public abstract class MultiTenantCopier implements Ordered {
    protected static final Log LOG = LogFactory.getLog(MultiTenantCopier.class);
    
    @Resource(name = "blGenericEntityService")
    protected GenericEntityService genericEntityService;
    
    @Resource(name = "blMultiTenantCopierExtensionManager")
    protected MultiTenantCopierExtensionManager extensionManager;

    @Resource(name="blStreamingTransactionCapableUtil")
    protected StreamingTransactionCapableUtil transUtil;
    
    protected int order = 0;

    public abstract void copyEntities(MultiTenantCopyContext context) throws Exception;

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    protected <T, G extends Exception> void persistCopyObjectTree(CopyOperation<T,G> copyOperation, Class<T> clazz, T original, MultiTenantCopyContext context) throws G {
        try {
            //don't persist if there is already an equivalent present
            if (context.getEquivalentId(clazz.getName(), genericEntityService.getIdentifier(original)) != null) {
                return;
            }

            context.clearOriginalIdentifiers();
            genericEntityService.clearAutoFlushMode();
            Object copy = copyOperation.execute(original);
            WakaRequestContext.getWakaRequestContext().setEnforceEnterpriseCollectionBehaviorState(EnforceEnterpriseCollectionBehaviorState.FALSE);
            persistCopyObjectTreeInternal(copy, new HashSet<Integer>(), context);
            genericEntityService.flush();
        } catch (Exception e) {
            LOG.error("Unable to persist the copy object tree", e);
            throw ExceptionHelper.refineException(e);
        } finally {
            WakaRequestContext.getWakaRequestContext().setEnforceEnterpriseCollectionBehaviorState(EnforceEnterpriseCollectionBehaviorState.TRUE);
            context.clearOriginalIdentifiers();
            genericEntityService.enableAutoFlushMode();
        }
    }

    protected void persistCopyObjectTreeInternal(Object copy, Set<Integer> library, MultiTenantCopyContext context) {
        if (library.contains(System.identityHashCode(copy))) {
            return;
        }
        library.add(System.identityHashCode(copy));
        List<Object[]> collections = new ArrayList<Object[]>();
        Field[] allFields = context.getAllFields(copy.getClass());
        for (Field field : allFields) {
            if (field.getName().equals("embeddableSiteDiscriminator")) {
                continue;
            }
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                Object newTarget;
                try {
                    newTarget = field.get(copy);
                } catch (IllegalAccessException e) {
                    throw ExceptionHelper.refineException(e);
                }
                if (newTarget != null) {
                    if (field.getAnnotation(ManyToOne.class) != null || field.getAnnotation(OneToOne.class) != null) {
                        if (library.contains(System.identityHashCode(newTarget))) {
                            persistNode(newTarget, context);
                            continue;
                        }
                        persistCopyObjectTreeInternal(newTarget, library, context);
                    } else if (field.getAnnotation(ManyToMany.class) != null || field.getAnnotation(OneToMany.class) != null) {
                        collections.add(new Object[]{field, newTarget});
                    } else if (field.getType().getAnnotation(Embeddable.class) != null && MultiTenantCloneable.class.isAssignableFrom(field.getType())) {
                        persistCopyObjectTreeInternal(newTarget, library, context);
                    }
                }
            }
        }
        if (copy.getClass().getAnnotation(Embeddable.class) == null) {
            persistNode(copy, context);
        }
        for (Object[] collectionItem : collections) {
            if (collectionItem[1] instanceof Collection) {
                Collection newCollection = (Collection) collectionItem[1];
                for (Object member : newCollection) {
                    persistCopyObjectTreeInternal(member, library, context);
                }
            } else if (collectionItem[1] instanceof Map) {
                Map newMap = (Map) collectionItem[1];
                for (Object key : newMap.keySet()) {
                    persistCopyObjectTreeInternal(newMap.get(key), library, context);
                }
            } else {
                throw new IllegalArgumentException(String.format("During copy object persistence, " +
                        "an unrecognized type was detected for a OneToMany or ManyToMany field. The system currently only " +
                        "recognizes Collection and Map. (%s.%s)", copy.getClass().getName(), ((Field) collectionItem[0]).getName()));
            }
        }
    }

    protected void persistNode(final Object copy, MultiTenantCopyContext context) {
        if (!genericEntityService.sessionContains(copy) && !genericEntityService.idAssigned(copy)) {
            Object original = genericEntityService.readGenericEntity(copy.getClass().getName(), context.removeOriginalIdentifier(copy));
            extensionManager.getProxy().transformCopy(context, original, copy);
            extensionManager.getProxy().prepareForSave(context, original, copy);

            IdentityExecutionUtils.runOperationByIdentifier(new IdentityOperation<Void, RuntimeException>() {
                @Override
                public Void execute() {
                    genericEntityService.persist(copy);
                    return null;
                }
            }, context.getToSite(), context.getToSite(), context.getToCatalog());

            context.storeEquivalentMapping(original.getClass().getName(), context.getIdentifier(original), context.getIdentifier(copy));
        }
    }

    protected <T extends MultiTenantCloneable> void copyEntitiesOfType(final Class<T> clazz, final Site fromSite, final Catalog fromCatalog, final MultiTenantCopyContext context)
            throws ServiceException, CloneNotSupportedException {
        genericEntityService.flush();
        genericEntityService.clear();
        transUtil.runStreamingTransactionalOperation(new StreamCapableTransactionalOperationAdapter() {
            @Override
            public Object[] retrievePage(int startPos, int pageSize) {
                try {
                    Object[] temp = new Object[1];
                    List<T> results = readAll(clazz, pageSize, startPos, fromSite, fromCatalog);
                    temp[0] = results;
                    return temp;
                } catch (ServiceException e) {
                    throw ExceptionHelper.refineException(e);
                }
            }

            @Override
            public void pagedExecute(Object[] param) throws Throwable {
                try {
                    List<T> results = (List<T>) param[0];
                    for (T result : results) {
                        persistCopyObjectTree(new CopyOperation<T, CloneNotSupportedException>() {
                            @Override
                            public T execute(T original) throws CloneNotSupportedException {
                                return (T) original.createOrRetrieveCopyInstance(context).getClone();
                            }
                        }, clazz, result, context);
                    }
                } finally {
                    genericEntityService.clear();
                }
            }

            @Override
            public Long retrieveTotalCount() {
                try {
                    return readCount(clazz, fromSite, fromCatalog);
                } catch (ServiceException e) {
                    throw ExceptionHelper.refineException(e);
                }
            }

            @Override
            public boolean shouldRetryOnTransactionLockAcquisitionFailure() {
                return true;
            }
        }, RuntimeException.class);
    }

    protected <T> T save(MultiTenantCopyContext context, final T object) throws ServiceException {
        return IdentityExecutionUtils.runOperationByIdentifier(new IdentityOperation<T, ServiceException>() {
            @Override
            public T execute() throws ServiceException {
                return genericEntityService.save(object);
            }
        }, context.getToSite(), context.getToSite(), context.getToCatalog());
    }

    protected <T> Long readCount(final Class<T> clazz, Site site, Catalog catalog) throws ServiceException {
        return IdentityExecutionUtils.runOperationByIdentifier(new IdentityOperation<Long, ServiceException>() {
            @Override
            public Long execute() throws ServiceException {
                return genericEntityService.readCountGenericEntity(clazz);
            }
        }, site, site, catalog);
    }

    protected <T> List<T> readAll(Class<T> clazz, Site site, Catalog catalog) throws ServiceException {
        return readAll(clazz, Integer.MAX_VALUE, 0, site, catalog);
    }

    protected List<Long> readAllIds(final Class<?> clazz, Site site, Catalog catalog) throws ServiceException {
        return IdentityExecutionUtils.runOperationByIdentifier(new IdentityOperation<List<Long>, ServiceException>() {
            @Override
            public List<Long> execute() throws ServiceException {
                return genericEntityService.readAllGenericEntityId(clazz);
            }
        }, site, site, catalog);
    }

    protected <T> List<T> readAll(final Class<T> clazz, final int limit, final int offset, Site site, 
            Catalog catalog) throws ServiceException {
        return IdentityExecutionUtils.runOperationByIdentifier(new IdentityOperation<List<T>, ServiceException>() {
            @Override
            public List<T> execute() throws ServiceException {
                return genericEntityService.readAllGenericEntity(clazz, limit, offset);
            }
        }, site, site, catalog);
    }

}
