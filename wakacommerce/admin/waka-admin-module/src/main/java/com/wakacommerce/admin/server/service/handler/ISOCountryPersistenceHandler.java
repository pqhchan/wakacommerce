
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.EmptyFilterValues;
import com.wakacommerce.openadmin.server.service.persistence.module.PersistenceModule;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPath;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.Restriction;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.predicate.PredicateProvider;
import com.wakacommerce.profile.core.domain.CountryImpl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @ hui
 */
@Component("blISOCountryPersistenceHandler")
public class ISOCountryPersistenceHandler extends CustomPersistenceHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(ISOCountryPersistenceHandler.class);

    @Override
    public Boolean canHandleFetch(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        try {
            Class testClass = Class.forName(ceilingEntityFullyQualifiedClassname);
            return ISOCountry.class.isAssignableFrom(testClass);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto,
                                  DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        FilterMapping filterMapping = new FilterMapping()
            .withFieldPath(new FieldPath().withTargetProperty("name"))
            .withDirectFilterValues(new EmptyFilterValues())
            .withRestriction(new Restriction()
                .withPredicateProvider(new PredicateProvider<Character, Character>() {
                    @Override
                    public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder, 
                            From root, String ceilingEntity, String fullPropertyName, Path<Character> explicitPath, 
                            List<Character> directValues) {
                        return builder.isNotNull(explicitPath);
                    }
                })
            );
        cto.getAdditionalFilterMappings().add(filterMapping);
        
        FilterMapping countryRestrictionMapping = new FilterMapping()
            .withDirectFilterValues(new EmptyFilterValues())
            .withRestriction(new Restriction()
                .withPredicateProvider(new PredicateProvider<Character, Character>() {
                    @Override
                    public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder, 
                            From root, String ceilingEntity, String fullPropertyName, Path<Character> explicitPath, 
                            List<Character> directValues) {
                        CriteriaQuery<Serializable> criteria = fieldPathBuilder.getCriteria();
                        
                        Root<CountryImpl> blcCountry = criteria.from(CountryImpl.class);
                        Predicate join = builder.equal(
                            root.get("alpha2").as(String.class), 
                            blcCountry.get("abbreviation").as(String.class)
                        );
                        
                        return join;
                    }
                })
            );
        cto.getAdditionalFilterMappings().add(countryRestrictionMapping);

        PersistenceModule myModule = helper.getCompatibleModule(persistencePackage.getPersistencePerspective().getOperationTypes().getFetchType());
        return myModule.fetch(persistencePackage, cto);
    }
}
