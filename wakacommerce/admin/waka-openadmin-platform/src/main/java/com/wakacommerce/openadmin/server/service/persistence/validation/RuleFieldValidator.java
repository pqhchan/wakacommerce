  
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.presentation.RuleIdentifier;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.rule.QuantityBasedRule;
import com.wakacommerce.common.sandbox.SandBoxHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldNotAvailableException;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.RuleFieldExtractionUtility;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.RuleFieldPersistenceProvider;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import com.wakacommerce.openadmin.web.rulebuilder.DataDTOToMVELTranslator;
import com.wakacommerce.openadmin.web.rulebuilder.MVELTranslationException;
import com.wakacommerce.openadmin.web.rulebuilder.dto.DataDTO;
import com.wakacommerce.openadmin.web.rulebuilder.dto.DataWrapper;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

/**
 * Validates that a rule can be parsed out successfully. Most of this comes from {@link RuleFieldPersistenceProvider}.
 *
 *     
 */
@Component("blRuleFieldValidator")
public class RuleFieldValidator implements PopulateValueRequestValidator {

    @Resource(name = "blRuleFieldExtractionUtility")
    protected RuleFieldExtractionUtility ruleFieldExtractionUtility;
    
    @Resource(name = "blSandBoxHelper")
    protected SandBoxHelper sandBoxHelper;
    
    @Override
    public PropertyValidationResult validate(PopulateValueRequest populateValueRequest, Serializable instance) {
        if (canHandleValidation(populateValueRequest)) {
            DataDTOToMVELTranslator translator = new DataDTOToMVELTranslator();
            EntityManager em = populateValueRequest.getPersistenceManager().getDynamicEntityDao().getStandardEntityManager();
            if (SupportedFieldType.RULE_SIMPLE.equals(populateValueRequest.getMetadata().getFieldType())) {
                
                //AntiSamy HTML encodes the rule JSON - pass the unHTMLEncoded version
                DataWrapper dw = ruleFieldExtractionUtility.convertJsonToDataWrapper(populateValueRequest.getProperty().getUnHtmlEncodedValue());
                if (dw != null && StringUtils.isNotEmpty(dw.getError())) {
                    return new PropertyValidationResult(false, "Could not serialize JSON from rule builder: " + dw.getError());
                }
                if (dw == null || StringUtils.isEmpty(dw.getError())) {
                    try {
                        String mvel = ruleFieldExtractionUtility.convertSimpleMatchRuleJsonToMvel(translator, RuleIdentifier.ENTITY_KEY_MAP.get(populateValueRequest.getMetadata().getRuleIdentifier()),
                                populateValueRequest.getMetadata().getRuleIdentifier(), dw);
                    } catch (MVELTranslationException e) {
                        return new PropertyValidationResult(false, getMvelParsingErrorMesage(dw, e));
                    }
                }
            }
            
            if (SupportedFieldType.RULE_WITH_QUANTITY.equals(populateValueRequest.getMetadata().getFieldType())) {
                Collection<QuantityBasedRule> existingRules;
                try {
                    existingRules = (Collection<QuantityBasedRule>) populateValueRequest.getFieldManager().getFieldValue
                            (instance, populateValueRequest.getProperty().getName());
                } catch (FieldNotAvailableException e) {
                    return new PropertyValidationResult(false, "Could not access rule field on Java object to set values");
                } catch (IllegalAccessException e) {
                    return new PropertyValidationResult(false, "Could not access rule field on Java object to set values");
                }
                
                String entityKey = RuleIdentifier.ENTITY_KEY_MAP.get(populateValueRequest.getMetadata().getRuleIdentifier());
                String jsonPropertyValue = populateValueRequest.getProperty().getUnHtmlEncodedValue();
                String fieldService = populateValueRequest.getMetadata().getRuleIdentifier();
                if (!StringUtils.isEmpty(jsonPropertyValue)) {
                    DataWrapper dw = ruleFieldExtractionUtility.convertJsonToDataWrapper(jsonPropertyValue);
                    if (dw != null && StringUtils.isNotEmpty(dw.getError())) {
                        return new PropertyValidationResult(false, "Could not serialize JSON from rule builder: " + dw.getError());
                    }
                    if (dw != null && StringUtils.isEmpty(dw.getError())) {
                        for (DataDTO dto : dw.getData()) {
                            if (dto.getId() != null) {
                                boolean foundIdToUpdate = false;
                                for (QuantityBasedRule quantityBasedRule : existingRules) {
                                    Long sandBoxVersionId = sandBoxHelper.getSandBoxVersionId(quantityBasedRule.getClass(), dto.getId());
                                    if (sandBoxVersionId == null) {
                                        sandBoxVersionId = dto.getId();
                                    }
                                    if (sandBoxVersionId.equals(quantityBasedRule.getId())) {
                                        foundIdToUpdate = true;
                                        try {
                                            String mvel = ruleFieldExtractionUtility.convertDTOToMvelString(translator, entityKey, dto, fieldService);
                                        } catch (MVELTranslationException e) {
                                            return new PropertyValidationResult(false, getMvelParsingErrorMesage(dw, e));
                                        }
                                    }
                                }
                                if (!foundIdToUpdate) {
                                    return new PropertyValidationResult(false, "Tried to update QuantityBasedReule with ID " + dto.getId() + " but that rule does not exist");
                                }
                            } else {
                                // This is a new rule, just validate that it parses successfully
                                try {
                                    ruleFieldExtractionUtility.convertDTOToMvelString(translator, entityKey, dto, fieldService);
                                } catch (MVELTranslationException e) {
                                    return new PropertyValidationResult(false, getMvelParsingErrorMesage(dw, e));
                                }
                            }
                        }
                    }
                }
            }
        }
        return new PropertyValidationResult(true);
    }
    
    protected String getMvelParsingErrorMesage(DataWrapper dw, MVELTranslationException e) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Problem translating rule builder, error code ");
        errorMessage.append(e.getCode());
        errorMessage.append(": ");
        errorMessage.append(e.getMessage());
        return errorMessage.toString();
    }
    
    protected boolean canHandleValidation(PopulateValueRequest populateValueRequest) {
        return populateValueRequest.getMetadata().getFieldType() == SupportedFieldType.RULE_WITH_QUANTITY ||
                populateValueRequest.getMetadata().getFieldType() == SupportedFieldType.RULE_SIMPLE;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1000;
    }
}
