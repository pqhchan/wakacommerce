
package com.wakacommerce.common.exception;

/**
 *
 * @ hui
 */
public interface RootCauseAccessor  {


    public Throwable getRootCause();

    public String getRootCauseMessage();
    
}
