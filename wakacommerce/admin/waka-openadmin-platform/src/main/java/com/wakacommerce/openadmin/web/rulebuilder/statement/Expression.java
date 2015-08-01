
package com.wakacommerce.openadmin.web.rulebuilder.statement;

import com.wakacommerce.openadmin.web.rulebuilder.BLCOperator;

/**
 *  
 *  
 */
public class Expression {
    protected String field;
    protected BLCOperator operator;
    protected String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field.trim();
    }

    public BLCOperator getOperator() {
        return operator;
    }

    public void setOperator(BLCOperator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value.trim();
    }
}
