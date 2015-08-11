package com.wakacommerce.core.web.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @ hui
 */
public class BroadleafWebServicesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String UNKNOWN_ERROR = "com.wakacommerce.core.web.api.BroadleafWebServicesException.unknownError";
    public static final String NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.notFound";
    public static final String PRODUCT_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.productNotFound";
    public static final String CATEGORY_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.categoryNotFound";
    public static final String SKU_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.skuNotFound";
    public static final String SEARCH_ERROR = "com.wakacommerce.core.web.api.BroadleafWebServicesException.errorExecutingSearch";
    public static final String SEARCH_QUERY_EMPTY = "com.wakacommerce.core.web.api.BroadleafWebServicesException.searchQueryEmpty";
    public static final String SEARCH_QUERY_MALFORMED = "com.wakacommerce.core.web.api.BroadleafWebServicesException.searchQueryMalformed";
    public static final String INVALID_CATEGORY_ID = "com.wakacommerce.core.web.api.BroadleafWebServicesException.invalidCategoryId";
    public static final String CART_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.cartNotFound";
    public static final String CART_ITEM_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.cartItemNotFound";
    public static final String CART_PRICING_ERROR = "com.wakacommerce.core.web.api.BroadleafWebServicesException.cartPricingError";
    public static final String UPDATE_CART_ERROR = "com.wakacommerce.core.web.api.BroadleafWebServicesException.updateCartError";
    public static final String PROMO_CODE_MAX_USAGES = "com.wakacommerce.core.web.api.BroadleafWebServicesException.promoCodeMaxUsages";
    public static final String PROMO_CODE_INVALID = "com.wakacommerce.core.web.api.BroadleafWebServicesException.promoCodeInvalid";
    public static final String PROMO_CODE_EXPIRED = "com.wakacommerce.core.web.api.BroadleafWebServicesException.promoCodeExpired";
    public static final String PROMO_CODE_ALREADY_ADDED = "com.wakacommerce.core.web.api.BroadleafWebServicesException.promoCodeAlreadyAdded";
    public static final String FULFILLMENT_GROUP_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.fulfillmentGroupNotFound";
    public static final String FULFILLMENT_OPTION_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.fulfillmentOptionNotFound";
    public static final String CUSTOMER_NOT_FOUND = "com.wakacommerce.core.web.api.BroadleafWebServicesException.customerNotFound";
    public static final String CHECKOUT_PROCESSING_ERROR = "com.wakacommerce.core.web.api.BroadleafWebServicesException.checkoutProcessingError";
    public static final String CONTENT_TYPE_NOT_SUPPORTED = "com.wakacommerce.core.web.api.BroadleafWebServicesException.contentTypeNotSupported";
    public static final String QUERY_PARAMETER_NOT_PRESENT = "com.wakacommerce.core.web.api.BroadleafWebServicesException.queryParameterNotPresent";

    protected int httpStatusCode = 500;

    protected Map<String, Object[]> messages;

    protected Map<String, String> translatedMessages;

    protected Locale locale;

    public BroadleafWebServicesException(int httpStatusCode, Locale locale, Map<String, Object[]> messages, Throwable cause) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
        this.locale = locale;
        this.messages = messages;
    }

    public static BroadleafWebServicesException build(int httpStatusCode) {
        return build(httpStatusCode, null, null, null);
    }

    public static BroadleafWebServicesException build(int httpStatusCode, Throwable t) {
        return build(httpStatusCode, null, null, t);
    }

    public static BroadleafWebServicesException build(int httpStatusCode, Locale locale) {
        return build(httpStatusCode, locale, null, null);
    }

    public static BroadleafWebServicesException build(int httpStatusCode, Locale locale, Throwable t) {
        return build(httpStatusCode, locale, null, t);
    }

    public static BroadleafWebServicesException build(int httpStatusCode, Locale locale, Map<String, Object[]> messages) {
        return build(httpStatusCode, locale, messages, null);
    }

    public static BroadleafWebServicesException build(int httpStatusCode, Locale locale, Map<String, Object[]> messages, Throwable cause) {
        return new BroadleafWebServicesException(httpStatusCode, locale, messages, cause);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public Map<String, Object[]> getMessages() {
        if (this.messages == null) {
            this.messages = new HashMap<String, Object[]>();
        }
        return this.messages;
    }

    public Map<String,String> getTranslatedMessages() {
        if (this.translatedMessages == null) {
            this.translatedMessages = new HashMap<String, String>();
        }
        return this.translatedMessages;
    }

    public BroadleafWebServicesException addTranslatedMessage(String key, String message) {
        getTranslatedMessages().put(key, message);
        return this;
    }

    public BroadleafWebServicesException addMessage(String key) {
        return addMessage(key, null);
    }

    public BroadleafWebServicesException addMessage(String key, Object param) {
        if (param != null) {
            return addMessage(key, new Object[] { param });
        } else {
            return addMessage(key, new Object[0]);
        }
    }

    public BroadleafWebServicesException addMessage(String key, Object[] params) {
        getMessages().put(key, params);
        return this;
    }

    public Locale getLocale() {
        return this.locale;
    }
}
