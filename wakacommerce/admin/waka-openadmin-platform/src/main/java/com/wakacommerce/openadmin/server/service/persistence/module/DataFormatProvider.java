
package com.wakacommerce.openadmin.server.service.persistence.module;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @ hui
 */
public interface DataFormatProvider {

    public SimpleDateFormat getSimpleDateFormatter();

    public DecimalFormat getDecimalFormatter();

    public String formatValue(Object value);

}
