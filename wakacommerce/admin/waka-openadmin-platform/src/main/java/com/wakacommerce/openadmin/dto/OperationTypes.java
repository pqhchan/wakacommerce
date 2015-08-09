
package com.wakacommerce.openadmin.dto;

import java.io.Serializable;

import com.wakacommerce.common.presentation.client.OperationType;

/**
 *
 * @ hui
 */
public class OperationTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    private OperationType fetchType = OperationType.BASIC;
    private OperationType removeType = OperationType.BASIC;
    private OperationType addType = OperationType.BASIC;
    private OperationType updateType = OperationType.BASIC;
    private OperationType inspectType = OperationType.BASIC;

    public OperationTypes() {
        //do nothing
    }

    public OperationTypes(OperationType fetchType, OperationType removeType, OperationType addType, OperationType updateType, OperationType inspectType) {
        this.removeType = removeType;
        this.addType = addType;
        this.updateType = updateType;
        this.fetchType = fetchType;
        this.inspectType = inspectType;
    }

    public OperationType getRemoveType() {
        return removeType;
    }

    public void setRemoveType(OperationType removeType) {
        this.removeType = removeType;
    }

    public OperationType getAddType() {
        return addType;
    }

    public void setAddType(OperationType addType) {
        this.addType = addType;
    }

    public OperationType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(OperationType updateType) {
        this.updateType = updateType;
    }

    public OperationType getFetchType() {
        return fetchType;
    }

    public void setFetchType(OperationType fetchType) {
        this.fetchType = fetchType;
    }

    public OperationType getInspectType() {
        return inspectType;
    }

    public void setInspectType(OperationType inspectType) {
        this.inspectType = inspectType;
    }

    public OperationTypes cloneOperationTypes() {
        OperationTypes operationTypes = new OperationTypes();
        operationTypes.setAddType(addType);
        operationTypes.setFetchType(fetchType);
        operationTypes.setInspectType(inspectType);
        operationTypes.setRemoveType(removeType);
        operationTypes.setUpdateType(updateType);

        return  operationTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        OperationTypes that = (OperationTypes) o;

        if (addType != that.addType) return false;
        if (fetchType != that.fetchType) return false;
        if (inspectType != that.inspectType) return false;
        if (removeType != that.removeType) return false;
        if (updateType != that.updateType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fetchType != null ? fetchType.hashCode() : 0;
        result = 31 * result + (removeType != null ? removeType.hashCode() : 0);
        result = 31 * result + (addType != null ? addType.hashCode() : 0);
        result = 31 * result + (updateType != null ? updateType.hashCode() : 0);
        result = 31 * result + (inspectType != null ? inspectType.hashCode() : 0);
        return result;
    }
}
