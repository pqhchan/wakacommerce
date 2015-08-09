  
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.springframework.util.CollectionUtils;

import com.wakacommerce.openadmin.server.security.service.RowLevelSecurityService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ hui
 */
public class GlobalValidationResult {
    
    protected boolean valid;
    protected List<String> errorMessages = new ArrayList<String>();
    
    public GlobalValidationResult(boolean valid, String errorMessage) {
        setValid(valid);
        setErrorMessage(errorMessage);
    }
    
    public GlobalValidationResult(boolean valid) {
        setValid(valid);
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isNotValid() {
        return !valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return CollectionUtils.isEmpty(errorMessages) ? null : errorMessages.get(0);
    }

    @Deprecated
    public void setErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public void addErrorMessage(String errorMessageOrKey) {
        errorMessages.add(errorMessageOrKey);
    }
    
    public List<String> getErrorMessages() {
        return errorMessages;
    }
    
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    
}
