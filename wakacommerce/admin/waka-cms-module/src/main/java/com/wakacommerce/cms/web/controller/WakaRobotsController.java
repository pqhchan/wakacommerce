package com.wakacommerce.cms.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wakacommerce.cms.page.service.PageService;
import com.wakacommerce.common.RequestDTO;
import com.wakacommerce.common.TimeDTO;
import com.wakacommerce.common.file.service.WakaFileUtils;
import com.wakacommerce.common.page.dto.PageDTO;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.common.web.BaseUrlResolver;
import com.wakacommerce.common.web.WakaRequestContext;

/**
 * This class serves up the Robots.txt file. The default contents can be overridden by 
 * adding a Page named "/robots.txt" in the BLC admin or DB. 
 */
public class WakaRobotsController {

    public static final String BLC_RULE_MAP_PARAM = "blRuleMap";

    public static final String REQUEST_DTO = "blRequestDTO";

    @Resource(name = "blBaseUrlResolver")
    private BaseUrlResolver baseUrlResolver;

    @Resource(name = "blPageService")
    private PageService pageService;

    public String getRobotsFile(HttpServletRequest request, HttpServletResponse response) {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PageDTO page = pageService.findPageByURI("/robots.txt", buildMvelParameters(request), isSecure(request));

        if (page != null && page.getPageFields().containsKey("body")) {
            String body = (String) page.getPageFields().get("body");
            body = body.replace("${siteBaseUrl}", baseUrlResolver.getSiteBaseUrl());
            return body;
        } else {
            return getDefaultRobotsTxt();
        }
    }
    
    public boolean isSecure(HttpServletRequest request) {
        boolean secure = false;
        if (request != null) {
             secure = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
        }
        return secure;
    }

    /**
     * Used to produce a working but simple robots.txt.    Can be overridden in code or by defining a page
     * managed in the Broadleaf CMS named  "/robots.txt"
     * 
     * @return
     */
    protected String getDefaultRobotsTxt() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Using default Broadleaf Commerce robots.txt file").append("\n");
        sb.append("User-agent: *").append("\n");
        sb.append("Disallow:").append("\n");
        String fileLoc = WakaFileUtils.appendUnixPaths(baseUrlResolver.getSiteBaseUrl(), "/sitemap.xml.gz");

        sb.append("Sitemap:").append(fileLoc);
        return sb.toString();
    }

    /**
    *
    * @param request
    * @return
    */
    private Map<String, Object> buildMvelParameters(HttpServletRequest request) {
        TimeDTO timeDto = new TimeDTO(SystemTime.asCalendar());
        RequestDTO requestDto = (RequestDTO) request.getAttribute(REQUEST_DTO);

        Map<String, Object> mvelParameters = new HashMap<String, Object>();
        mvelParameters.put("time", timeDto);
        mvelParameters.put("request", requestDto);

        Map<String, Object> blcRuleMap = (Map<String, Object>) request.getAttribute(BLC_RULE_MAP_PARAM);
        if (blcRuleMap != null) {
            for (String mapKey : blcRuleMap.keySet()) {
                mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
            }
        }

        return mvelParameters;
    }
}
