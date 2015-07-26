
package com.wakacommerce.common.exception;

/**
 * Interface indicating that the exception knows how to return the root cause message.
 * 
 *bpolster
 */
public interface RootCauseAccessor  {


    public Throwable getRootCause();

    public String getRootCauseMessage();
    
}
