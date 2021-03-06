
package com.wakacommerce.core.web.api;

import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.wakacommerce.common.util.ApplicationContextHolder;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.web.api.wrapper.ErrorMessageWrapper;
import com.wakacommerce.core.web.api.wrapper.ErrorWrapper;

/**
 * <p>
 * Handles exceptions that can occur in the Broadleaf REST APIs. Specifically, this will serialize exceptions into
 * consumable JSON or XML so that clients that utilize the API don't have to treat exception responses as special cases.
 *
 *  
 *     
 */
public class BroadleafSpringRestExceptionMapper {

    private static final Log LOG = LogFactory.getLog(BroadleafSpringRestExceptionMapper.class);

    protected String messageKeyPrefix = BroadleafWebServicesException.class.getName() + '.';

    @Resource(name = "messageSource")
    protected MessageSource messageSource;

    protected ApplicationContext context = ApplicationContextHolder.getApplicationContext();

    @ExceptionHandler(BroadleafWebServicesException.class)
    public @ResponseBody ErrorWrapper handleBroadleafWebServicesException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        ErrorWrapper errorWrapper = (ErrorWrapper) context.getBean(ErrorWrapper.class.getName());
        BroadleafWebServicesException blcException = (BroadleafWebServicesException) ex;
        Locale locale = null;
        WakaRequestContext requestContext = WakaRequestContext.getWakaRequestContext();
        if (requestContext != null) {
            locale = requestContext.getJavaLocale();
        }

        if (ex.getCause() != null) {
            LOG.error("An error occured invoking a REST service.", ex.getCause());
        }
        errorWrapper.setHttpStatusCode(blcException.getHttpStatusCode());
        response.setStatus(resolveResponseStatusCode(ex, errorWrapper));
        if (blcException.getLocale() != null) {
            locale = blcException.getLocale();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }

        if (blcException.getMessages() != null && !blcException.getMessages().isEmpty()) {
            Set<String> keys = blcException.getMessages().keySet();
            for (String key : keys) {
                ErrorMessageWrapper errorMessageWrapper = (ErrorMessageWrapper) context.getBean(ErrorMessageWrapper.class.getName());
                errorMessageWrapper.setMessageKey(resolveClientMessageKey(key));
                errorMessageWrapper.setMessage(messageSource.getMessage(key, blcException.getMessages().get(key), key, locale));
                errorWrapper.getMessages().add(errorMessageWrapper);
            }
        } else {
            ErrorMessageWrapper errorMessageWrapper = (ErrorMessageWrapper) context.getBean(ErrorMessageWrapper.class.getName());
            errorMessageWrapper.setMessageKey(resolveClientMessageKey(BroadleafWebServicesException.UNKNOWN_ERROR));
            errorMessageWrapper.setMessage(messageSource.getMessage(BroadleafWebServicesException.UNKNOWN_ERROR, null,
                    BroadleafWebServicesException.UNKNOWN_ERROR, locale));
            errorWrapper.getMessages().add(errorMessageWrapper);
        }

        return errorWrapper;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public @ResponseBody ErrorWrapper handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        ErrorWrapper errorWrapper = (ErrorWrapper) context.getBean(ErrorWrapper.class.getName());
        Locale locale = null;
        WakaRequestContext requestContext = WakaRequestContext.getWakaRequestContext();
        if (requestContext != null) {
            locale = requestContext.getJavaLocale();
        }

        LOG.error("An error occured invoking a REST service", ex);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        errorWrapper.setHttpStatusCode(HttpStatus.SC_NOT_FOUND);
        response.setStatus(resolveResponseStatusCode(ex, errorWrapper));
        ErrorMessageWrapper errorMessageWrapper = (ErrorMessageWrapper) context.getBean(ErrorMessageWrapper.class.getName());
        errorMessageWrapper.setMessageKey(resolveClientMessageKey(BroadleafWebServicesException.NOT_FOUND));
        errorMessageWrapper.setMessage(messageSource.getMessage(BroadleafWebServicesException.NOT_FOUND, null,
                BroadleafWebServicesException.NOT_FOUND, locale));
        errorWrapper.getMessages().add(errorMessageWrapper);

        return errorWrapper;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public @ResponseBody ErrorWrapper handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        ErrorWrapper errorWrapper = (ErrorWrapper) context.getBean(ErrorWrapper.class.getName());
        Locale locale = null;
        WakaRequestContext requestContext = WakaRequestContext.getWakaRequestContext();
        if (requestContext != null) {
            locale = requestContext.getJavaLocale();
        }

        LOG.error("An error occured invoking a REST service", ex);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        errorWrapper.setHttpStatusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
        response.setStatus(resolveResponseStatusCode(ex, errorWrapper));
        ErrorMessageWrapper errorMessageWrapper = (ErrorMessageWrapper) context.getBean(ErrorMessageWrapper.class.getName());
        errorMessageWrapper.setMessageKey(resolveClientMessageKey(BroadleafWebServicesException.CONTENT_TYPE_NOT_SUPPORTED));
        errorMessageWrapper.setMessage(messageSource.getMessage(BroadleafWebServicesException.CONTENT_TYPE_NOT_SUPPORTED,
                new String[] {request.getContentType()}, BroadleafWebServicesException.CONTENT_TYPE_NOT_SUPPORTED, locale));
        errorWrapper.getMessages().add(errorMessageWrapper);

        return errorWrapper;
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody ErrorWrapper handleMissingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ErrorWrapper errorWrapper = (ErrorWrapper) context.getBean(ErrorWrapper.class.getName());
        Locale locale = null;
        String parameterType = null;
        String parameterName = null;
        WakaRequestContext requestContext = WakaRequestContext.getWakaRequestContext();
        if (requestContext != null) {
            locale = requestContext.getJavaLocale();
        }
        if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException castedException = (MissingServletRequestParameterException)ex;
            parameterType = castedException.getParameterType();
            parameterName = castedException.getParameterName();
        }

        LOG.error("An error occured invoking a REST service", ex);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (parameterType == null) {
            parameterType = "String";
        }
        if(parameterName == null) {
            parameterName = "[unknown name]";
        }
        errorWrapper.setHttpStatusCode(HttpStatus.SC_BAD_REQUEST);
        response.setStatus(resolveResponseStatusCode(ex, errorWrapper));
        ErrorMessageWrapper errorMessageWrapper = (ErrorMessageWrapper) context.getBean(ErrorMessageWrapper.class.getName());
        errorMessageWrapper.setMessageKey(resolveClientMessageKey(BroadleafWebServicesException.QUERY_PARAMETER_NOT_PRESENT));
        errorMessageWrapper.setMessage(messageSource.getMessage(BroadleafWebServicesException.QUERY_PARAMETER_NOT_PRESENT,
                new String[] {parameterType, parameterName}, BroadleafWebServicesException.QUERY_PARAMETER_NOT_PRESENT, locale));
        errorWrapper.getMessages().add(errorMessageWrapper);

        return errorWrapper;
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorWrapper handleException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        ErrorWrapper errorWrapper = (ErrorWrapper) context.getBean(ErrorWrapper.class.getName());
        Locale locale = null;
        WakaRequestContext requestContext = WakaRequestContext.getWakaRequestContext();
        if (requestContext != null) {
            locale = requestContext.getJavaLocale();
        }

        LOG.error("An error occured invoking a REST service", ex);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        errorWrapper.setHttpStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        response.setStatus(resolveResponseStatusCode(ex, errorWrapper));
        ErrorMessageWrapper errorMessageWrapper = (ErrorMessageWrapper) context.getBean(ErrorMessageWrapper.class.getName());
        errorMessageWrapper.setMessageKey(resolveClientMessageKey(BroadleafWebServicesException.UNKNOWN_ERROR));
        errorMessageWrapper.setMessage(messageSource.getMessage(BroadleafWebServicesException.UNKNOWN_ERROR, null,
                BroadleafWebServicesException.UNKNOWN_ERROR, locale));
        errorWrapper.getMessages().add(errorMessageWrapper);
        return errorWrapper;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * This key is the prefix that will be stripped off of all message keys that are returned to a client.
     * The default is "com.wakacommerce.core.web.api.BroadleafWebServicesException.". So, if a message key contained
     * in a BroadleafWebServicesException is com.wakacommerce.core.web.api.BroadleafWebServicesException.unknownError,
     * just "unknownError" will be returned to the client. This behavior can be changed by overriding the
     * <code>resolveClientMessageKey</code> method.
     * @param prefix
     */
    public void setMessageKeyPrefix(String prefix) {
        this.messageKeyPrefix = prefix;
    }

    /*
     * This allows you to return a different HTTP response code in the HTTP response than what is in the response wrapper.
     * For example, some clients may wish to always return a 200 (SUCCESS), even when there is an error.
     * The default behavior is to return the same status code associated with the error wrapper, or 500 if it is not known.
     */
    protected int resolveResponseStatusCode(Throwable t, ErrorWrapper error) {
        if (error.getHttpStatusCode() == null) {
            return 500;
        }
        return error.getHttpStatusCode();
    }

    protected String resolveClientMessageKey(String key) {
        if (messageKeyPrefix != null) {
            return StringUtils.remove(key, messageKeyPrefix);
        }
        return key;
    }
}
