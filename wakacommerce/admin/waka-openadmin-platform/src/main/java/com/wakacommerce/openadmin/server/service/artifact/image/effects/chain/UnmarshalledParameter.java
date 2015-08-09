
package com.wakacommerce.openadmin.server.service.artifact.image.effects.chain;

public class UnmarshalledParameter {
    
    private String name;
    private String value;
    private String type;
    private boolean applyFactor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isApplyFactor() {
        return applyFactor;
    }

    public void setApplyFactor(boolean applyFactor) {
        this.applyFactor = applyFactor;
    }

}
