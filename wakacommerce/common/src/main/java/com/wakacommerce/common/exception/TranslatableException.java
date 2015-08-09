
package com.wakacommerce.common.exception;

import org.springframework.context.NoSuchMessageException;

import com.wakacommerce.common.web.WakaRequestContext;

/**
 *
 * @ hui
 */
public abstract class TranslatableException extends Exception {

    private static final long serialVersionUID = 1L;

    protected int code;
    protected Object[] messageParams = null;

    public TranslatableException(int code, String message) {
        this(code, message, null);
    }

    public TranslatableException(int code, String message, Object[] messageParams) {
        super(message);
        this.code = code;
        this.messageParams = messageParams;
    }

    public int getCode() {
        return code;
    }

    public String getMessageKey() {
        return getClass().getSimpleName() + "_" + code;
    }

    public Object[] getMessageParameters() {
        return this.messageParams;
    }

    @Override
    public String getLocalizedMessage() {
        String response = getMessage();
        try {
            String exCode = getMessageKey();
            WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
            if (context != null && context.getMessageSource() != null) {
                response = context.getMessageSource().getMessage(exCode, this.messageParams, getMessage(), context.getJavaLocale());
                if (response.equals(exCode)) {
                    response = getMessage();
                }
            }
        } catch (NoSuchMessageException e) {
            response = getMessage();
        }
        return response;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
