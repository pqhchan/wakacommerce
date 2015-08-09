
package com.wakacommerce.common.util;

import org.apache.commons.collections.Predicate;

import java.lang.reflect.ParameterizedType;


/**
 *
 * @ hui
 */
@SuppressWarnings("unchecked")
public abstract class TypedPredicate<T> implements Predicate {
    
    protected Class<T> clazz;
    
    public TypedPredicate() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public boolean evaluate(Object value) {
        if (value == null || clazz.isAssignableFrom(value.getClass())) {
            return eval((T) value);
        }
        return false;
    }
    
    public abstract boolean eval(T value);

}
