package com.wakacommerce.common.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.deeplink.DeepLink;
import com.wakacommerce.common.web.deeplink.DeepLinkService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统中控制器的最高抽象类，可以在这里放置一些公共方法
 * 
 * @see WakaControllerUtility
 */
public abstract class WakaAbstractController {
    
    /**
     * 判断指定的request是普通请求还是Ajax请求
     * 
     * @param request
     */
    protected boolean isAjaxRequest(HttpServletRequest request) {
        return WakaControllerUtility.isAjaxRequest(request);       
    }
    
    /**
     * 返回系统部署的上下文路径. 如果应用部署在根目录下则返回"/"，否则，返回一个
     * 前后两端都带"/"的斜线
     * 
     * @param request
     */
    protected String getContextPath(HttpServletRequest request) {
        String ctxPath = request.getContextPath();
        if (StringUtils.isBlank(ctxPath)) {
            return "/";
        } else {
            if (ctxPath.charAt(0) != '/') {
                ctxPath = '/' + ctxPath;
            }
            if (ctxPath.charAt(ctxPath.length() - 1) != '/') {
                ctxPath = ctxPath + '/';
            }
            
            return ctxPath;
        }
        
    }
    
    protected <T> void addDeepLink(ModelAndView model, DeepLinkService<T> service, T item) {
        if (service == null) {
            return;
        }

        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc.getSandBox() != null) {
            List<DeepLink> links = service.getLinks(item);
            if (links.size() == 1) {
                model.addObject("adminDeepLink", links.get(0));
            } else {
                model.addObject("adminDeepLink", links);
            }
        }
    }
    
    /**
     * 一般，控制器的方法声明都会返回一个字符串来指定逻辑视图名（模板路径）. 然而，
     * 在方法的执行过程中可能会出现异常或错误，这时候需要返回给客户端的是一个用json序列化了的执行状态，
     * 而不是逻辑视图名. 这个公共方法就是用来方便达成该目标的
     * 
     * @param response
     * @param responseMap
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    protected String jsonResponse(HttpServletResponse response, Map<?, ?> responseMap) 
            throws JsonGenerationException, JsonMappingException, IOException {
        response.setHeader("Content-Type", "application/json");
        new ObjectMapper().writeValue(response.getWriter(), responseMap);
        return null;
    }

}
