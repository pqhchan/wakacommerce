package com.mycompany.controller.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.wakacommerce.core.web.controller.catalog.BroadleafSkuController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Controller("blSkuController")
public class SkuController extends BroadleafSkuController {
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.handleRequest(request, response);
    }

}
