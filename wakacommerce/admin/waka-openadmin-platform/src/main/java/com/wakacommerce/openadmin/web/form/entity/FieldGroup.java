package com.wakacommerce.openadmin.web.form.entity;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FieldGroup {

    protected String title;
    protected Integer order;
    /* 替换排好顺序的字段，这里的字段的order属性直接表示最终位置，即希望被插入的位置 */
    protected Set<Field> alternateOrderedFields = new HashSet<Field>();
    protected Set<Field> fields = new HashSet<Field>();
    protected Boolean isVisible;

    public Boolean getIsVisible() {
        if (isVisible != null) {
            return isVisible;
        }
        for (Field f : getFields()) {
            if (f.getIsVisible()) {
                return true;
            }
        }
        return false;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
    
    public FieldGroup withTitle(String title) {
        setTitle(title);
        return this;
    }
    
    public FieldGroup withOrder(Integer order) {
        setOrder(order);
        return this;
    }

    public boolean addField(Field field) {
        if (field.getAlternateOrdering()) {
            return alternateOrderedFields.add(field);
        } else {
            return fields.add(field);
        }
    }

    public boolean removeField(Field field) {
        if (field.getAlternateOrdering()) {
            return alternateOrderedFields.remove(field);
        } else {
            return fields.remove(field);
        }
    }

    public Set<Field> getFields() {
        List<Field> myFields = new ArrayList<Field>();
        myFields.addAll(fields);
        Collections.sort(myFields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return new CompareToBuilder()
                    .append(o1.getOrder(), o2.getOrder())
                    .append(o1.getFriendlyName(), o2.getFriendlyName())
                    .append(o1.getName(), o2.getName())
                    .toComparison();
            }
        });
        if (!alternateOrderedFields.isEmpty()) {
            List<Field> mapFieldsList = new ArrayList<Field>(alternateOrderedFields);
            Collections.sort(mapFieldsList, new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return new CompareToBuilder()
                        .append(o1.getOrder(), o2.getOrder())
                        .append(o1.getFriendlyName(), o2.getFriendlyName())
                        .append(o1.getName(), o2.getName())
                        .toComparison();
                }
            });
            /* order属性小于等于零的alternate ordered fields会直接按顺序插到表头处 */
            List<Field> smallOrderFields = new ArrayList<Field>();
            for (Field mapField : mapFieldsList) {
                if (mapField.getOrder() <= 0) {
                    smallOrderFields.add(mapField);
                }
            }
            myFields.addAll(0, smallOrderFields);
            /* order属性大于零的alternate ordered fields会插入到表的 order-1 位置处 */
            for (Field mapField : mapFieldsList) {
                if (mapField.getOrder() <= 0) {
                    continue;
                }
                if (mapField.getOrder() < myFields.size() + 1) {
                    myFields.add(mapField.getOrder() - 1, mapField);
                    continue;
                }
                myFields.add(mapField);
            }
        }

        //排好顺序后，不允许对列表进行修改
        return Collections.unmodifiableSet(new LinkedHashSet<Field>(myFields));
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

}
