package com.wakacommerce.core.catalog.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

public class ProductOptionValidationStrategyType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, ProductOptionValidationStrategyType> TYPES = new LinkedHashMap<String, ProductOptionValidationStrategyType>();

    public static final ProductOptionValidationStrategyType ADD_ITEM = new ProductOptionValidationStrategyType("ADD_ITEM", 1000, "添加项目时验证");
    public static final ProductOptionValidationStrategyType SUBMIT_ORDER = new ProductOptionValidationStrategyType("SUBMIT_ORDER", 2000, "提交时验证");

    public static ProductOptionValidationStrategyType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public ProductOptionValidationStrategyType() {
        //do nothing
    }

    public ProductOptionValidationStrategyType(final String type, final Integer rank, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
        this.rank = rank;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        ProductOptionValidationStrategyType other = (ProductOptionValidationStrategyType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
