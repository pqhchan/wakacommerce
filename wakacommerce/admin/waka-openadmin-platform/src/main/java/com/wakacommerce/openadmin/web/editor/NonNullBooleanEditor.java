
package com.wakacommerce.openadmin.web.editor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;

/**
 *
 * @ hui
 */
public class NonNullBooleanEditor extends CustomBooleanEditor {

    public NonNullBooleanEditor() {
        super(false);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isBlank(text)) {
            setValue(Boolean.FALSE);
        } else {
            super.setAsText(text);
        }
    }

}
