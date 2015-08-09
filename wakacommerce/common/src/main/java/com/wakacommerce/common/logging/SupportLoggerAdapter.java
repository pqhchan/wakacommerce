
package com.wakacommerce.common.logging;

/**
 *
 * @ hui
 */
public interface SupportLoggerAdapter {

    public String getName();

    public void setName(String name);

    public void support(String message);

    public void support(String message, Throwable t);

    public void lifecycle(LifeCycleEvent lifeCycleEvent, String message);


    public void debug(String message);

    public void debug(String message, Throwable t);

    public void error(String message);

    public void error(String message, Throwable t);

    public void fatal(String message);

    public void fatal(String message, Throwable t);

    public void info(String message);

    public void info(String message, Throwable t);

    public void warn(String message);

    public void warn(String message, Throwable t);

}
