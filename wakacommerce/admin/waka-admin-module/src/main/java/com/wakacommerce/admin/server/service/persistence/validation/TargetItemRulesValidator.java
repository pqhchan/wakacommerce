
package com.wakacommerce.admin.server.service.persistence.validation;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.service.type.OfferType;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.service.persistence.validation.PropertyValidationResult;
import com.wakacommerce.openadmin.server.service.persistence.validation.PropertyValidator;
import com.wakacommerce.openadmin.server.service.persistence.validation.RequiredPropertyValidator;

import java.io.Serializable;
import java.util.Map;


/**
 * Validator that ensures that an offer of type {@link OfferType#ORDER_ITEM} has at least one rule for the target criteria
 *
 *     
 */
@Component("blTargetItemRulesValidator")
public class TargetItemRulesValidator implements PropertyValidator {

    @Override
    public PropertyValidationResult validate(Entity entity, Serializable instance, Map<String, FieldMetadata> entityFieldMetadata, Map<String, String> validationConfiguration, BasicFieldMetadata propertyMetadata, String propertyName, String value) {
        Offer offer = (Offer)instance;
        if (OfferType.ORDER_ITEM.equals(offer.getType())) {
            return new PropertyValidationResult(CollectionUtils.isNotEmpty(offer.getTargetItemCriteriaXref()),
                    RequiredPropertyValidator.ERROR_MESSAGE);
        } else {
            return new PropertyValidationResult(true);
        }
    }
}
