
package com.wakacommerce.common.extension;



/**
 *
 * @ hui
 */
public abstract class AbstractExtensionHandler implements ExtensionHandler {

    protected int priority;
    protected boolean enabled = true;

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
