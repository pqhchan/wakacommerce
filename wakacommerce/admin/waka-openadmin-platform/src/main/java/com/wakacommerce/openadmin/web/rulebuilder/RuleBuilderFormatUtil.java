
package com.wakacommerce.openadmin.web.rulebuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wakacommerce.common.util.FormatUtil;
import com.wakacommerce.common.web.WakaRequestContext;

/**
 *
 * @ hui
 */
public class RuleBuilderFormatUtil {

    public static final String COMPATIBILITY_FORMAT = "MM/dd/yy HH:mm a Z";
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm";

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(WakaRequestContext.getWakaRequestContext().getTimeZone());
        return formatter.format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        Date parsedDate;
        try {
            parsedDate = FormatUtil.getTimeZoneFormat().parse(date);
        } catch (ParseException e) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(COMPATIBILITY_FORMAT);
                formatter.setTimeZone(WakaRequestContext.getWakaRequestContext().getTimeZone());
                parsedDate = formatter.parse(date);
            } catch (ParseException e1) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                    formatter.setTimeZone(WakaRequestContext.getWakaRequestContext().getTimeZone());
                    parsedDate = formatter.parse(date);
                } catch (ParseException e2) {
                    throw e;
                }
            }
        }
        return parsedDate;
    }
}
