  
package com.wakacommerce.openadmin.server.service.persistence.module.criteria;

/**
 * Thrown when a problem converting a particular {@link com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPath}
 * from a {@link com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping} is detected
 * during JPA criteria translation for fetch operation.
 *
 * @see com.wakacommerce.openadmin.server.service.persistence.module.criteria.CriteriaTranslatorImpl
 * 
 */
public class CriteriaConversionException extends RuntimeException {

    protected FieldPath fieldPath;

    public CriteriaConversionException(String message, FieldPath fieldPath) {
        super(message);
        this.fieldPath = fieldPath;
    }

    public FieldPath getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(FieldPath fieldPath) {
        this.fieldPath = fieldPath;
    }
}
