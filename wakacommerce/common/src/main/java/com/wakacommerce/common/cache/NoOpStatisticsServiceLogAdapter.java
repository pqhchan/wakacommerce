
package com.wakacommerce.common.cache;

/**
 * An implementation of StatisticsServiceLogAdapter that does nothing.
 * If your application, requires a specific logging framework then the custom application can
 * implement the appropriate setLevel() methods necessary to activate and disable logging.
 *
 *Elbert Bautista (elbertbautista)
 */
public class NoOpStatisticsServiceLogAdapter implements StatisticsServiceLogAdapter {

    @Override
    public void activateLogging(Class clazz) {
        //do nothing
    }

    @Override
    public void disableLogging(Class clazz) {
        //do nothing
    }

}
