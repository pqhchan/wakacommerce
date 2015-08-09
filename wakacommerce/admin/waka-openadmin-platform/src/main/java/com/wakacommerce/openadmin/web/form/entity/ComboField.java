
package com.wakacommerce.openadmin.web.form.entity;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * @ hui
 */
public class ComboField extends Field {

    protected Map<String, String> options = new LinkedHashMap<String, String>();

    /* *********** */
    /* ADD METHODS */
    /* *********** */
    
    public void putOption(String key, String value) {
        options.put(key, value);
    }
    
    /* ************************** */
    /* CUSTOM GETTERS / SETTERS */
    /* ************************** */
    
    public void setOptions(String[][] options) {
        if (options != null) {
            for (String[] option : options) {
                putOption(option[0], option[1]);
            }
        }
    }
    
    public String getOption(String optionKey) {
        return getOptions().get(optionKey);
    }

    /* ************************** */
    /* STANDARD GETTERS / SETTERS */
    /* ************************** */

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

}
