
package com.wakacommerce.common.value;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface Searchable<T extends Serializable> extends ValueAssignable<T> {

    Boolean getSearchable();

    void setSearchable(Boolean searchable);

}
