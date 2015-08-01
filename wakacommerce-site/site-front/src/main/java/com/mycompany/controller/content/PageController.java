package com.mycompany.controller.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.wakacommerce.cms.web.controller.WakaPageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("blPageController")
public class PageController extends WakaPageController {
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.handleRequest(request, response);
    }

}
