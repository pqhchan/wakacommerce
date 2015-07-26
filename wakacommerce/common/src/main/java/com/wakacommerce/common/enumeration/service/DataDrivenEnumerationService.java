
package com.wakacommerce.common.enumeration.service;

import com.wakacommerce.common.enumeration.domain.DataDrivenEnumeration;
import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValue;

public interface DataDrivenEnumerationService {

    public DataDrivenEnumeration findEnumByKey(String enumKey);

    public DataDrivenEnumerationValue findEnumValueByKey(String enumKey, String enumValueKey);

}
