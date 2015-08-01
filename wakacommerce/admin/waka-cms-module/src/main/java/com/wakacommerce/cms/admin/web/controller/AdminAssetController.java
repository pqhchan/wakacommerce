package com.wakacommerce.cms.admin.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wakacommerce.cms.admin.web.service.AssetFormBuilderService;
import com.wakacommerce.cms.file.StaticAssetMultiTenantExtensionManager;
import com.wakacommerce.cms.file.domain.StaticAssetImpl;
import com.wakacommerce.cms.file.service.StaticAssetService;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.EntityFormAction;

@Controller("blAdminAssetController")
@RequestMapping("/" + AdminAssetController.SECTION_KEY)
public class AdminAssetController extends AdminBasicEntityController {
    
    protected static final String SECTION_KEY = "assets";
    
    @Resource(name = "blAssetFormBuilderService")
    protected AssetFormBuilderService formService;
    
    @Resource(name = "blStaticAssetService")
    protected StaticAssetService staticAssetService;

    @Resource(name = "blStaticAssetMultiTenantExtensionManager")
    protected StaticAssetMultiTenantExtensionManager staticAssetExtensionManager;
    
    @Override
    protected String getSectionKey(Map<String, String> pathVars) {
        //allow external links to work for ToOne items
        if (super.getSectionKey(pathVars) != null) {
            return super.getSectionKey(pathVars);
        }
        return SECTION_KEY;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewEntityList(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable  Map<String, String> pathVars,
            @RequestParam  MultiValueMap<String, String> requestParams) throws Exception {
        String returnPath = super.viewEntityList(request, response, model, pathVars, requestParams);
        
        // Remove the default add button and replace it with an upload asset button
        List<EntityFormAction> mainActions = (List<EntityFormAction>) model.asMap().get("mainActions");
        Iterator<EntityFormAction> actions = mainActions.iterator();
        while (actions.hasNext()) {
            EntityFormAction action = actions.next();
            if (EntityFormAction.ADD.equals(action.getId())) {
                actions.remove();
                break;
            }
        }
        mainActions.add(0, new EntityFormAction("UPLOAD_ASSET")
                .withButtonClass("upload-asset")
                .withIconClass("icon-camera")
                .withDisplayText("上传资源"));

        // Change the listGrid view to one that has a hidden form for uploading the image.
        model.addAttribute("viewType", "entityListWithUploadForm");
        
        ListGrid listGrid = (ListGrid) model.asMap().get("listGrid");
        formService.addImageThumbnailField(listGrid, "fullUrl");

        return returnPath;
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewEntityForm(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable  Map<String, String> pathVars,
            @PathVariable(value="id") String id) throws Exception {
        Site currentSite = WakaRequestContext.getWakaRequestContext().getNonPersistentSite();

        model.addAttribute("cmsUrlPrefix", staticAssetService.getStaticAssetUrlPrefix());
        String returnPath = super.viewEntityForm(request, response, model, pathVars, id);

        staticAssetExtensionManager.getProxy().removeShareOptionsForMTStandardSite(model, currentSite);

        return returnPath;
    }
    
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String saveEntity(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable  Map<String, String> pathVars,
            @PathVariable(value="id") String id,
            @ModelAttribute(value="entityForm") EntityForm entityForm, BindingResult result,
            RedirectAttributes ra) throws Exception {
        String templatePath = super.saveEntity(request, response, model, pathVars, id, entityForm, result, ra);
        if (result.hasErrors()) {
            model.addAttribute("cmsUrlPrefix", staticAssetService.getStaticAssetUrlPrefix());
        }
        return templatePath;
    }

    @Override
    protected String getDefaultEntityType() {
        return StaticAssetImpl.class.getName();
    }

}
