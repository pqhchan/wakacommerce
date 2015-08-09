
package com.wakacommerce.common.web.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.enumeration.domain.DataDrivenEnumeration;
import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValue;
import com.wakacommerce.common.enumeration.service.DataDrivenEnumerationService;
import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;


/**
 *
 * @ hui
 */
public class DataDrivenEnumerationProcessor extends AbstractModelVariableModifierProcessor {

    @Resource(name = "blDataDrivenEnumerationService")
    protected DataDrivenEnumerationService enumService;

    public DataDrivenEnumerationProcessor() {
        super("enumeration");
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        String key = element.getAttributeValue("key");
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("No 'key' parameter was passed to find enumeration values");
        }
        
        DataDrivenEnumeration ddEnum = enumService.findEnumByKey(key);
        if (ddEnum == null) {
            throw new IllegalArgumentException("Could not find a data driven enumeration keyed by " + key);
        }
        List<DataDrivenEnumerationValue> enumValues = new ArrayList<DataDrivenEnumerationValue>(ddEnum.getEnumValues());
        
        final String sort = element.getAttributeValue("sort");
        if (StringUtils.isNotEmpty(sort)) {
            Collections.sort(enumValues, new Comparator<DataDrivenEnumerationValue>() {

                @Override
                public int compare(DataDrivenEnumerationValue arg0, DataDrivenEnumerationValue arg1) {
                    if (sort.equals("ASCENDING")) {
                        return arg0.getDisplay().compareTo(arg1.getDisplay());
                    } else {
                        return arg1.getDisplay().compareTo(arg0.getDisplay());
                    }
                }
            });
        }
        
        addToModel(arguments, "enumValues", enumValues);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

}
