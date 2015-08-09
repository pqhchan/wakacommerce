
package com.wakacommerce.common.cache;

/**
 *
 * @ hui
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
