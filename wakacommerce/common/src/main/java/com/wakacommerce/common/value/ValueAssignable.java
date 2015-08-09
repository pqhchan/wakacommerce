
package com.wakacommerce.common.value;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface ValueAssignable<T extends Serializable> extends Serializable {

    T getValue();

    void setValue(T value);

    String getName();

    void setName(String name);
}
