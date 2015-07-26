
package com.wakacommerce.common.enumeration.dao;

import com.wakacommerce.common.enumeration.domain.DataDrivenEnumeration;
import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValue;

public interface DataDrivenEnumerationDao {

    public DataDrivenEnumeration readEnumByKey(String enumKey);

    public DataDrivenEnumerationValue readEnumValueByKey(String enumKey, String enumValueKey);

}
