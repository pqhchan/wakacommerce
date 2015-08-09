
package com.wakacommerce.openadmin.dto;

import com.wakacommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor;

/**
 *
 * @ hui
 */
public class SimpleValueMapStructure extends MapStructure {

    private static final long serialVersionUID = 1L;
    
    private String valuePropertyName;
    private String valuePropertyFriendlyName;
    
    public SimpleValueMapStructure() {
        super();
    }

    public SimpleValueMapStructure(String keyClassName, String keyPropertyName, String keyPropertyFriendlyName, String valueClassName, String valuePropertyName, String valuePropertyFriendlyName, String mapProperty, String mapKeyValueProperty) {
        super(keyClassName, keyPropertyName, keyPropertyFriendlyName, valueClassName, mapProperty, false, mapKeyValueProperty);
        this.valuePropertyFriendlyName = valuePropertyFriendlyName;
        this.valuePropertyName = valuePropertyName;
    }

    public String getValuePropertyName() {
        return valuePropertyName;
    }
    
    public void setValuePropertyName(String valuePropertyName) {
        this.valuePropertyName = valuePropertyName;
    }
    
    public String getValuePropertyFriendlyName() {
        return valuePropertyFriendlyName;
    }
    
    public void setValuePropertyFriendlyName(String valuePropertyFriendlyName) {
        this.valuePropertyFriendlyName = valuePropertyFriendlyName;
    }
    
    public void accept(PersistencePerspectiveItemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleValueMapStructure{");
        sb.append(super.toString());
        sb.append("valuePropertyName='").append(valuePropertyName).append('\'');
        sb.append(", valuePropertyFriendlyName='").append(valuePropertyFriendlyName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public PersistencePerspectiveItem clonePersistencePerspectiveItem() {
        SimpleValueMapStructure mapStructure = new SimpleValueMapStructure();
        mapStructure.setKeyClassName(getKeyClassName());
        mapStructure.setKeyPropertyName(getKeyPropertyName());
        mapStructure.setValuePropertyFriendlyName(getKeyPropertyFriendlyName());
        mapStructure.setValueClassName(getValueClassName());
        mapStructure.setMapProperty(getMapProperty());
        mapStructure.setDeleteValueEntity(getDeleteValueEntity());
        mapStructure.valuePropertyName = valuePropertyName;
        mapStructure.valuePropertyFriendlyName = valuePropertyFriendlyName;

        return mapStructure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        if (!super.equals(o)) return false;

        SimpleValueMapStructure that = (SimpleValueMapStructure) o;

        if (valuePropertyFriendlyName != null ? !valuePropertyFriendlyName.equals(that.valuePropertyFriendlyName) : that.valuePropertyFriendlyName != null)
            return false;
        if (valuePropertyName != null ? !valuePropertyName.equals(that.valuePropertyName) : that.valuePropertyName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (valuePropertyName != null ? valuePropertyName.hashCode() : 0);
        result = 31 * result + (valuePropertyFriendlyName != null ? valuePropertyFriendlyName.hashCode() : 0);
        return result;
    }
}
