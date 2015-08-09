
package com.wakacommerce.openadmin.server.service.persistence.validation;


/**
 *
 * @ hui
 */
public class PropertyValidationResult extends GlobalValidationResult {

    public PropertyValidationResult(boolean valid, String errorMessage) {
        super(valid, errorMessage);
    }
    
    public PropertyValidationResult(boolean valid) {
        super(valid);
    }
    
}
