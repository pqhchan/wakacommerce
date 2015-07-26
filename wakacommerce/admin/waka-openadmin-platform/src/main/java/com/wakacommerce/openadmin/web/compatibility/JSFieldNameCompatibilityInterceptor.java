
package com.wakacommerce.openadmin.web.compatibility;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.JSCompatibilityHelper;
import com.wakacommerce.openadmin.web.form.component.RuleBuilderField;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *Jeff Fischer
 */
public class JSFieldNameCompatibilityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
        if (modelAndView != null) {
            Entity entity = (Entity) modelAndView.getModelMap().get("entity");
            EntityForm entityForm = (EntityForm) modelAndView.getModelMap().get("entityForm");
    
            if (entity != null) {
                for (Property property : entity.getProperties()) {
                    if (property.getName().contains(".")) {
                        property.setName(JSCompatibilityHelper.encode(property.getName()));
                    }
                }
            }
    
            if (entityForm != null) {
                entityForm.clearFieldsMap();
                for (Map.Entry<String, Field> field : entityForm.getFields().entrySet()) {
                    if (field.getKey().contains(".")) {
                        field.getValue().setName(JSCompatibilityHelper.encode(field.getValue().getName()));
                        if (field.getValue() instanceof RuleBuilderField) {
                            ((RuleBuilderField) field.getValue()).setJsonFieldName(JSCompatibilityHelper.encode((
                                    (RuleBuilderField) field.getValue()).getJsonFieldName()));
                        }
                    }
                }
                entityForm.clearFieldsMap();
            }
        }
    }
}
