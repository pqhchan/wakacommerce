  
package com.wakacommerce.openadmin.server.service.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.common.presentation.client.PersistencePerspectiveItemType;
import com.wakacommerce.openadmin.dto.AdornedTargetList;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.service.persistence.extension.ArchiveStatusPersistenceEventHandlerExtensionManager;
import com.wakacommerce.openadmin.server.service.persistence.module.EmptyFilterValues;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPath;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.Restriction;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.predicate.PredicateProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 *
 * @ hui
 */
@Component("blArchiveStatusPersistenceEventHandler")
public class ArchiveStatusPersistenceEventHandler extends PersistenceManagerEventHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(ArchiveStatusPersistenceEventHandler.class);

    @Resource(name = "blArchiveStatusPersistenceEventHandlerExtensionManager")
    protected ArchiveStatusPersistenceEventHandlerExtensionManager extensionManager;
    
    @Override
    public PersistenceManagerEventHandlerResponse preFetch(PersistenceManager persistenceManager, PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException {
        try {
            Class<?>[] entityClasses = persistenceManager.getDynamicEntityDao()
                    .getAllPolymorphicEntitiesFromCeiling(Class.forName(persistencePackage.getCeilingEntityFullyQualifiedClassname()));
            AtomicBoolean isArchivable = new AtomicBoolean(false);
            for (Class<?> entity : entityClasses) {
                AtomicBoolean test = new AtomicBoolean(true);
                extensionManager.getProxy().isArchivable(entity, test);
                if (!test.get()) {
                    isArchivable.set(false);
                    break;
                }

                if (Status.class.isAssignableFrom(entity)) {
                    isArchivable.set(true);
                }
            }
            if (isArchivable.get() && !persistencePackage.getPersistencePerspective().getShowArchivedFields()) {
                String targetPropertyName = "archiveStatus.archived";
                if (persistencePackage.getPersistencePerspectiveItems().containsKey(PersistencePerspectiveItemType.ADORNEDTARGETLIST)) {
                    AdornedTargetList atl = (AdornedTargetList) persistencePackage.getPersistencePerspectiveItems().get(PersistencePerspectiveItemType.ADORNEDTARGETLIST);
                    targetPropertyName = atl.getTargetObjectPath() + "." + targetPropertyName;
                }
                FilterMapping filterMapping = new FilterMapping()
                    .withFieldPath(new FieldPath().withTargetProperty(targetPropertyName))
                    .withDirectFilterValues(new EmptyFilterValues())
                    .withRestriction(new Restriction()
                            .withPredicateProvider(new PredicateProvider<Character, Character>() {
                                @Override
                                public Predicate buildPredicate(CriteriaBuilder builder,
                                                                FieldPathBuilder fieldPathBuilder,
                                                                From root, String ceilingEntity,
                                                                String fullPropertyName, Path<Character> explicitPath,
                                                                List<Character> directValues) {
                                    return builder.or(builder.equal(explicitPath, 'N'), builder.isNull(explicitPath));
                                }
                            })
                    );
                cto.getAdditionalFilterMappings().add(filterMapping);
            }
            return new PersistenceManagerEventHandlerResponse().
                    withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.HANDLED);
        } catch (ClassNotFoundException e) {
            LOG.error("Could not find the class " + persistencePackage.getCeilingEntityFullyQualifiedClassname() + " to "
                    + "compute polymorphic entity types for. Assuming that the entity is not archivable");
            return new PersistenceManagerEventHandlerResponse().
                    withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
        }
    }
    
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
    
}
