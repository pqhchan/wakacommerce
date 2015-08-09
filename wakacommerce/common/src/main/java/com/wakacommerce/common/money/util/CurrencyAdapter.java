
package com.wakacommerce.common.money.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Currency;

/**
 *
 * @ hui
 */
public class CurrencyAdapter extends XmlAdapter<String, Currency> {

    @Override
    public String marshal(Currency currency) throws Exception {
        return currency.toString();
    }

    @Override
    public Currency unmarshal(String currencyString) throws Exception {
        return Currency.getInstance(currencyString);
    }

}
