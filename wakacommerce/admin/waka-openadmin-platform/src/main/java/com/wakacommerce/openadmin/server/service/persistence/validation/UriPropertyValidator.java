  
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @ hui
 */
@Component("blUriPropertyValidator")
public class UriPropertyValidator extends ValidationConfigurationBasedPropertyValidator {

    protected static final Log LOG = LogFactory.getLog(UriPropertyValidator.class);

    protected String ERROR_KEY_BEGIN_WITH_SLASH = "uriPropertyValidatorMustBeginWithSlashError";
    protected String ERROR_KEY_CANNOT_END_WITH_SLASH = "uriPropertyValidatorCannotEndWithSlashError";
    protected String ERROR_KEY_CANNOT_CONTAIN_SPACES = "uriPropertyValidatorCannotContainSpacesError";

    protected boolean getIgnoreFullUrls() {
        return BLCSystemProperty.resolveBooleanSystemProperty("uriPropertyValidator.ignoreFullUrls");
    }

    protected boolean getRequireLeadingSlash() {
        return BLCSystemProperty.resolveBooleanSystemProperty("uriPropertyValidator.requireLeadingSlash");
    }

    protected boolean getAllowTrailingSlash() {
        return BLCSystemProperty.resolveBooleanSystemProperty("uriPropertyValidator.allowTrailingSlash");
    }

    public boolean isFullUrl(String url) {
        return (url.startsWith("http") || url.startsWith("ftp"));
    }

    protected boolean succeedForNullValues = true;
    
    @Override
    public PropertyValidationResult validate(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        
        if (value == null) {
            return new PropertyValidationResult(succeedForNullValues);
        }

        if (value.contains(" ")) {
            return new PropertyValidationResult(false, ERROR_KEY_CANNOT_CONTAIN_SPACES);
        }
        
        if (isFullUrl(value) && getIgnoreFullUrls()) {
            return new PropertyValidationResult(true);
        }

        if (getRequireLeadingSlash() && !value.startsWith("/")) {
            return new PropertyValidationResult(false, ERROR_KEY_BEGIN_WITH_SLASH);
        }

        if (!getAllowTrailingSlash() && value.endsWith("/") && value.length() > 1) {
            return new PropertyValidationResult(false, ERROR_KEY_CANNOT_END_WITH_SLASH);
        }

        return new PropertyValidationResult(true);
    }
    
    public boolean isSucceedForNullValues() {
        return succeedForNullValues;
    }

    public void setSucceedForNullValues(boolean succeedForNullValues) {
        this.succeedForNullValues = succeedForNullValues;
    }
}
