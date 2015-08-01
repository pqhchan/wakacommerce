
package com.wakacommerce.common.presentation.client;

/**
 * Describes the target of an admin operation. This can be a regular entity itself, or a collection
 * or map property.
 *
 *  
 *
 */
public enum OperationType {
    NONDESTRUCTIVEREMOVE,
    BASIC,
    ADORNEDTARGETLIST,
    MAP
}
