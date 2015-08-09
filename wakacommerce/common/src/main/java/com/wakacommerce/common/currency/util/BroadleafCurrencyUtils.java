
package com.wakacommerce.common.currency.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;


/**
 *
 * @ hui
 */
public class BroadleafCurrencyUtils {

    protected static final Map<String, NumberFormat> FORMAT_CACHE = new ConcurrentHashMap<String, NumberFormat>();

    public static final MathContext ROUND_FLOOR_MATH_CONTEXT = new MathContext(0, RoundingMode.FLOOR);

    public static Money getMoney(BigDecimal amount, BroadleafCurrency currency) {
        if (amount == null) {
            return null;
        }
        
        if (currency != null) {
            return new Money(amount, currency.getCurrencyCode());
        } else {
            return new Money(amount);
        }
    }

    public static Money getMoney(BroadleafCurrency currency) {
        if (currency != null) {
            return new Money(0,currency.getCurrencyCode());
        } else {
            return new Money();
        }
    }

    public static Currency getCurrency(Money money) {
        if (money == null) {
            return Money.defaultCurrency();
        }
        return (money.getCurrency() == null) ? Money.defaultCurrency() : money.getCurrency();
    }

    public static Currency getCurrency(BroadleafCurrency currency) {
        return (currency == null) ? Money.defaultCurrency() : Currency.getInstance(currency.getCurrencyCode());
    }

    public static Money getUnitAmount(Money difference) {
        Currency currency = BroadleafCurrencyUtils.getCurrency(difference);
        BigDecimal divisor = new BigDecimal(Math.pow(10, currency.getDefaultFractionDigits()));
        BigDecimal unitAmount = new BigDecimal("1").divide(divisor);

        if (difference.lessThan(BigDecimal.ZERO)) {
            unitAmount = unitAmount.negate();
        }
        return new Money(unitAmount, currency);
    }

    public static Money getUnitAmount(BroadleafCurrency blCurrency) {
        Currency currency = getCurrency(blCurrency);
        BigDecimal divisor = new BigDecimal(Math.pow(10, currency.getDefaultFractionDigits()));
        BigDecimal unitAmount = new BigDecimal("1").divide(divisor);
        return new Money(unitAmount, currency);
    }

    public static int calculateRemainder(Money totalAmount, int quantity) {
        if (totalAmount == null || totalAmount.isZero() || quantity == 0) {
            return 0;
        }

        // Use this to convert to a whole number (e.g. 1.05 becomes 105 in US currency).
        BigDecimal multiplier = new BigDecimal(10).pow(totalAmount.getAmount().scale());
        BigDecimal amount = totalAmount.getAmount().multiply(multiplier);

        BigDecimal remainder = amount.remainder(new BigDecimal(quantity), ROUND_FLOOR_MATH_CONTEXT);
        return remainder.toBigInteger().intValue();
    }

    public static NumberFormat getNumberFormatFromCache(Locale locale, Currency currency) {
        String key = locale.toString() + currency.getCurrencyCode();
        if (!FORMAT_CACHE.containsKey(key)) {
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            format.setCurrency(currency);
            FORMAT_CACHE.put(key, format);
        }
        return FORMAT_CACHE.get(key);
    }
}
