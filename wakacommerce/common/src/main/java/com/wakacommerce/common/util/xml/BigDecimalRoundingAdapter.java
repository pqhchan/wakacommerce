
package com.wakacommerce.common.util.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @ hui
 */
public class BigDecimalRoundingAdapter extends XmlAdapter<String, BigDecimal> {

    @Override
    public BigDecimal unmarshal(String s) throws Exception {
        return new BigDecimal(s);
    }

    @Override
    public String marshal(BigDecimal bigDecimal) throws Exception {
        return bigDecimal.setScale(2, RoundingMode.UP).toString();
    }
}
