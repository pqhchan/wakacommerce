package com.wakacommerce.cms.web;

import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.cms.page.service.PageService;
import com.wakacommerce.cms.web.controller.WakaPageController;
import com.wakacommerce.common.RequestDTO;
import com.wakacommerce.common.TimeDTO;
import com.wakacommerce.common.page.dto.NullPageDTO;
import com.wakacommerce.common.page.dto.PageDTO;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.common.web.WakaAbstractHandlerMapping;
import com.wakacommerce.common.web.WakaRequestContext;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @see com.wakacommerce.cms.page.domain.Page
 * @see WakaPageController
 */
public class PageHandlerMapping extends WakaAbstractHandlerMapping {
    
    private final String controllerName="blPageController";
    public static final String BLC_RULE_MAP_PARAM = "blRuleMap";

    public static final String REQUEST_DTO = "blRequestDTO";
    
    @Resource(name = "blPageService")
    private PageService pageService;
    
    public static final String PAGE_ATTRIBUTE_NAME = "BLC_PAGE";

    @Value("${request.uri.encoding}")
    public String charEncoding;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        if (context != null && context.getRequestURIWithoutContext() != null) {
            String requestUri = URLDecoder.decode(context.getRequestURIWithoutContext(), charEncoding);
            PageDTO page = pageService.findPageByURI(requestUri, buildMvelParameters(request), context.isSecure());

            if (page != null && ! (page instanceof NullPageDTO)) {
                context.getRequest().setAttribute(PAGE_ATTRIBUTE_NAME, page);
                return controllerName;
            }
        }
        return null;
    }
    
    private Map<String,Object> buildMvelParameters(HttpServletRequest request) {
        TimeDTO timeDto = new TimeDTO(SystemTime.asCalendar());
        RequestDTO requestDto = (RequestDTO) request.getAttribute(REQUEST_DTO);

        Map<String, Object> mvelParameters = new HashMap<String, Object>();
        mvelParameters.put("time", timeDto);
        mvelParameters.put("request", requestDto);

        @SuppressWarnings("unchecked")
		Map<String,Object> blcRuleMap = (Map<String,Object>) request.getAttribute(BLC_RULE_MAP_PARAM);
        if (blcRuleMap != null) {
            for (String mapKey : blcRuleMap.keySet()) {
                mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
            }
        }

        return mvelParameters;
    }
}
