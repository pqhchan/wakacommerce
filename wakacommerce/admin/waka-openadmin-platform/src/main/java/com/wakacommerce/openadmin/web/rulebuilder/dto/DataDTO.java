
package com.wakacommerce.openadmin.web.rulebuilder.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *Elbert Bautista (elbertbautista)
 */
public class DataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    protected Integer quantity;

    protected String groupOperator;

    protected ArrayList<DataDTO> groups = new ArrayList<DataDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getGroupOperator() {
        return groupOperator;
    }

    public void setGroupOperator(String groupOperator) {
        this.groupOperator = groupOperator;
    }

    public ArrayList<DataDTO> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<DataDTO> groups) {
        this.groups = groups;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
            DataDTO that = (DataDTO) obj;
            return new EqualsBuilder()
                .append(id, that.id)
                .append(quantity, that.quantity)
                .append(groupOperator, that.groupOperator)
                .append(groups.toArray(), that.groups.toArray())
                .build();
        }
        return false;
    }
}
