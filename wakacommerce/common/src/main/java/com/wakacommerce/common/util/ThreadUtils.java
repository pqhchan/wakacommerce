
package com.wakacommerce.common.util;

import java.util.Calendar;

/**
 *
 * @ hui
 */
public class ThreadUtils {

    public static void sleepUntil(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, min, sec);

        long msFuture = cal.getTime().getTime();
        long msNow = System.currentTimeMillis();
        long msSleep = msFuture - msNow;

        if (msSleep <= 0) {
            return;
        }

        try {
            Thread.sleep(msFuture - msNow);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
