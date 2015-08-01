package com.wakacommerce.admin.web.controller.entity;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller("blAdminOrderController")
@RequestMapping("/" + AdminOrderController.SECTION_KEY)
public class AdminOrderController extends AdminBasicEntityController {
    
    protected static final String SECTION_KEY = "order";
    
    @Override
    protected String getSectionKey(Map<String, String> pathVars) {
        //allow external links to work for ToOne items
        if (super.getSectionKey(pathVars) != null) {
            return super.getSectionKey(pathVars);
        }
        return SECTION_KEY;
    }
    
    @Override
    protected String showViewUpdateCollection(HttpServletRequest request, Model model, Map<String, String> pathVars,
            String id, String collectionField, String collectionItemId, String modalHeaderType) throws ServiceException {
        String returnPath = super.showViewUpdateCollection(request, model, pathVars, id, collectionField, collectionItemId,
                modalHeaderType);
        
        if ("orderItems".equals(collectionField)) {
            EntityForm ef = (EntityForm) model.asMap().get("entityForm");

            ListGrid adjustmentsGrid = ef.findListGrid("orderItemAdjustments");
            if (adjustmentsGrid != null && CollectionUtils.isEmpty(adjustmentsGrid.getRecords())) {
                ef.removeListGrid("orderItemAdjustments");
            }

            ListGrid priceDetailsGrid = ef.findListGrid("orderItemPriceDetails");
            if (priceDetailsGrid != null && CollectionUtils.isEmpty(priceDetailsGrid.getRecords())) {
                ef.removeListGrid("orderItemPriceDetails");
            }
        }
        
        return returnPath;
    }

}
