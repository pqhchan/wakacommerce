package com.wakacommerce.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wakacommerce.common.RequestDTOImpl;
import com.wakacommerce.common.exception.SiteNotFoundException;
import com.wakacommerce.common.web.exception.HaltFilterChainException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("blRequestFilter")
public class WakaRequestFilter extends OncePerRequestFilter {

    private final Log LOG = LogFactory.getLog(getClass());

    public static String REQUEST_DTO_PARAM_NAME = "blRequestDTO";

    public static final String ADMIN_USER_ID_PARAM_NAME = "blAdminUserId";

    private static final String BLC_ADMIN_GWT = "com.wakacommerce.admin";
    private static final String BLC_ADMIN_PREFIX = "blcadmin";
    private static final String BLC_ADMIN_SERVICE = ".service";

    private Set<String> ignoreSuffixes;

    @Resource(name = "blRequestProcessor")
    protected WakaRequestProcessor requestProcessor;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (!shouldProcessURL(request, request.getRequestURI())) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Process URL not processing URL " + request.getRequestURI());
            }
            filterChain.doFilter(request, response);
            return;
        }
        
        if (LOG.isTraceEnabled()) {
            String requestURIWithoutContext;

            if (request.getContextPath() != null) {
                requestURIWithoutContext = request.getRequestURI().substring(request.getContextPath().length());
            } else {
                requestURIWithoutContext = request.getRequestURI();
            }

            // Remove JSESSION-ID or other modifiers
            int pos = requestURIWithoutContext.indexOf(";");
            if (pos >= 0) {
                requestURIWithoutContext = requestURIWithoutContext.substring(0, pos);
            }

            LOG.trace("Process URL Filter Begin " + requestURIWithoutContext);
        }

        if (request.getAttribute(REQUEST_DTO_PARAM_NAME) == null) {
            request.setAttribute(REQUEST_DTO_PARAM_NAME, new RequestDTOImpl(request));
        }

        try {
            requestProcessor.process(new ServletWebRequest(request, response));
            filterChain.doFilter(request, response);
        } catch (HaltFilterChainException e) {
            return;
        } catch (SiteNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            requestProcessor.postProcess(new ServletWebRequest(request, response));
        }
    }

    protected boolean shouldProcessURL(HttpServletRequest request, String requestURI) {
        if (requestURI.contains(BLC_ADMIN_GWT) || requestURI.endsWith(BLC_ADMIN_SERVICE) || requestURI.contains(BLC_ADMIN_PREFIX)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("WakaRequestFilter ignoring admin request URI " + requestURI);
            }
            return false;
        } else {
            int pos = requestURI.lastIndexOf(".");
            if (pos > 0) {
//                String suffix = requestURI.substring(pos);
//                if (getIgnoreSuffixes().contains(suffix.toLowerCase())) {
//                    if (LOG.isTraceEnabled()) {
//                        LOG.trace("BroadleafProcessURLFilter ignoring request due to suffix " + requestURI);
//                    }
//                    return false;
//                }
            }
        }
        return true;
    }

    @SuppressWarnings("rawtypes")
	protected Set getIgnoreSuffixes() {
        if (ignoreSuffixes == null || ignoreSuffixes.isEmpty()) {
            String[] ignoreSuffixList = { ".aif", ".aiff", ".asf", ".avi", ".bin", ".bmp", ".css", ".doc", ".eps", ".gif", ".hqx", ".js", ".jpg", ".jpeg", ".mid", ".midi", ".mov", ".mp3", ".mpg", ".mpeg", ".p65", ".pdf", ".pic", ".pict", ".png", ".ppt", ".psd", ".qxd", ".ram", ".ra", ".rm", ".sea", ".sit", ".stk", ".swf", ".tif", ".tiff", ".txt", ".rtf", ".vob", ".wav", ".wmf", ".xls", ".zip" };
            ignoreSuffixes = new HashSet<String>(Arrays.asList(ignoreSuffixList));
        }
        return ignoreSuffixes;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
