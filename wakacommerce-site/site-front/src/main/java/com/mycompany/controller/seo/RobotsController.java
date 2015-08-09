package com.mycompany.controller.seo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wakacommerce.cms.web.controller.WakaRobotsController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Controller
public class RobotsController extends WakaRobotsController {

    @RequestMapping(value = { "/robots.txt" })
    @ResponseBody
    public String getRobotsFile(HttpServletRequest request, HttpServletResponse response) {
        return super.getRobotsFile(request, response);
    }
}
