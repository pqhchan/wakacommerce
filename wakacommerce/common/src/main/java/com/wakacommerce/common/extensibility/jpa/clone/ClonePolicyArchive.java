
package com.wakacommerce.common.extensibility.jpa.clone;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Persistence for some collection fields is managed outside the admin pipeline. In such a case, the field (and any
 * subordinate fields managed in side field's class) should be marked with this annotation so that the system will know
 * to archive records appropriately during enterprise operations. Note, this annotation should only be applied to sandbox
 * aware fields.
 *
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ClonePolicyArchive {

}
