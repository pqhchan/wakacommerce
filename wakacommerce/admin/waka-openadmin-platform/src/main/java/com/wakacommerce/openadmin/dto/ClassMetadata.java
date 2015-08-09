
package com.wakacommerce.openadmin.dto;

import java.io.Serializable;
import java.util.Map;

import com.wakacommerce.common.util.BLCMapUtils;
import com.wakacommerce.common.util.TypedClosure;


/**
 *
 * @ hui
 */
public class ClassMetadata implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String ceilingType;
    private String securityCeilingType;
    private ClassTree polymorphicEntities;
    private Property[] properties;
    private String currencyCode = "USD";
    
    private Map<String, Property> pMap = null;

    public Map<String, Property> getPMap() {
        if (pMap == null) {
            pMap = BLCMapUtils.keyedMap(properties, new TypedClosure<String, Property>() {

                @Override
                public String getKey(Property value) {
                    return value.getName();
                }
            });
        }
        return pMap;
    }

    public String getCeilingType() {
        return ceilingType;
    }
    
    public void setCeilingType(String type) {
        this.ceilingType = type;
    }

    public String getSecurityCeilingType() {
        return securityCeilingType;
    }

    public void setSecurityCeilingType(String securityCeilingType) {
        this.securityCeilingType = securityCeilingType;
    }

    public ClassTree getPolymorphicEntities() {
        return polymorphicEntities;
    }

    public void setPolymorphicEntities(ClassTree polymorphicEntities) {
        this.polymorphicEntities = polymorphicEntities;
    }

    public Property[] getProperties() {
        return properties;
    }
    
    public void setProperties(Property[] property) {
        this.properties = property;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
