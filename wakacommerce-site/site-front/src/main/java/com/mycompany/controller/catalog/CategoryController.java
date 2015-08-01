package com.mycompany.controller.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.wakacommerce.core.web.controller.catalog.WakaCategoryController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("blCategoryController")
public class CategoryController extends WakaCategoryController {
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.handleRequest(request, response);
    }

}
