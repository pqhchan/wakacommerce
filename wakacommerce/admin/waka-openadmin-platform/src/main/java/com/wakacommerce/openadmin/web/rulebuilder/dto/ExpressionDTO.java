
package com.wakacommerce.openadmin.web.rulebuilder.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;

/**
 *Elbert Bautista (elbertbautista)
 */
public class ExpressionDTO extends DataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;

    protected String operator;

    protected String value;

    protected String start;

    protected String end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
            ExpressionDTO that = (ExpressionDTO) obj;
            return super.equals(obj) && new EqualsBuilder()
                .append(name, that.name)
                .append(operator, that.operator)
                .append(value, that.value)
                .append(start, that.start)
                .append(end, that.end)
                .build();
        }
        return false;
    }
}
