
package com.wakacommerce.openadmin.dto;

import java.io.Serializable;

/**
 *Jeff Fischer
 */
public class BatchDynamicResultSet implements Serializable {

    protected DynamicResultSet[] dynamicResultSets;

    public DynamicResultSet[] getDynamicResultSets() {
        return dynamicResultSets;
    }

    public void setDynamicResultSets(DynamicResultSet[] dynamicResultSets) {
        this.dynamicResultSets = dynamicResultSets;
    }
}
