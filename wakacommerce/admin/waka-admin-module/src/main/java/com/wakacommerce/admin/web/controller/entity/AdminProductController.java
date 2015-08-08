package com.wakacommerce.admin.web.controller.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wakacommerce.admin.server.service.handler.ProductCustomPersistenceHandler;
import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuImpl;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.openadmin.dto.BasicCollectionMetadata;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.ClassTree;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.dto.SectionCrumb;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.component.ListGridAction;
import com.wakacommerce.openadmin.web.form.entity.DefaultEntityFormActions;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.Field;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see {@link ProductCustomPersistenceHandler}
 */
@Controller("blAdminProductController")
@RequestMapping("/" + AdminProductController.SECTION_KEY)
public class AdminProductController extends AdminBasicEntityController {
    
    protected static final String SECTION_KEY = "product";
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    
    @Override
    protected String getSectionKey(Map<String, String> pathVars) {
        //allow external links to work for ToOne items
        if (super.getSectionKey(pathVars) != null) {
            return super.getSectionKey(pathVars);
        }
        return SECTION_KEY;
    }
    
    @Override
    public String[] getSectionCustomCriteria() {
        return new String[]{"productDirectEdit"};
    }

    @Override
    protected void modifyEntityForm(EntityForm ef, Map<String, String> pathVars) {
        Field overrideGeneratedUrl = ef.findField("overrideGeneratedUrl");
        overrideGeneratedUrl.setFieldType(SupportedFieldType.HIDDEN.toString().toLowerCase());
    }

    @Override
    protected void modifyAddEntityForm(EntityForm ef, Map<String, String> pathVars) {
        String defaultCategoryUrlPrefix = null;
        Field defaultCategory = ef.findField("defaultCategory");
        if (StringUtils.isNotBlank(defaultCategory.getValue())) {
            Category cat = catalogService.findCategoryById(Long.parseLong(defaultCategory.getValue()));
            defaultCategoryUrlPrefix = cat.getUrl();
        }
                
        Field overrideGeneratedUrl = ef.findField("overrideGeneratedUrl");
        overrideGeneratedUrl.setFieldType(SupportedFieldType.HIDDEN.toString().toLowerCase());
        boolean overriddenUrl = Boolean.parseBoolean(overrideGeneratedUrl.getValue());
        Field fullUrl = ef.findField("url");
        if (fullUrl != null) {
            fullUrl.withAttribute("overriddenUrl", overriddenUrl)
                .withAttribute("sourceField", "defaultSku--name")
                .withAttribute("toggleField", "overrideGeneratedUrl")
                .withAttribute("prefix-selector", "#field-defaultCategory")
                .withAttribute("prefix", defaultCategoryUrlPrefix)
                .withFieldType(SupportedFieldType.GENERATED_URL.toString().toLowerCase());
        }
    }
    
    protected String showAddAdditionalSku(HttpServletRequest request, HttpServletResponse response, Model model,
            String id, Map<String, String> pathVars) throws Exception {
        String collectionField = "additionalSkus";
        String mainClassName = getClassNameForSection(SECTION_KEY);
        List<SectionCrumb> sectionCrumbs = getSectionCrumbs(request, SECTION_KEY, id);
        ClassMetadata mainMetadata = service.getClassMetadata(getSectionPersistencePackageRequest(mainClassName, sectionCrumbs, pathVars)).getDynamicResultSet().getClassMetaData();
        Property collectionProperty = mainMetadata.getPMap().get(collectionField);
        FieldMetadata md = collectionProperty.getMetadata();
        
        PersistencePackageRequest ppr = PersistencePackageRequest.fromMetadata(md, sectionCrumbs)
                .withCustomCriteria(new String[] { id });
        BasicCollectionMetadata fmd = (BasicCollectionMetadata) md;
        ClassMetadata cmd = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        // If the entity type isn't specified, we need to determine if there are various polymorphic types
        // for this entity.
        String entityType = null;
        if (request.getParameter("entityType") != null) {
            entityType = request.getParameter("entityType");
        }
        if (StringUtils.isBlank(entityType)) {
            if (cmd.getPolymorphicEntities().getChildren().length == 0) {
                entityType = cmd.getPolymorphicEntities().getFullyQualifiedClassname();
            } else {
                entityType = getDefaultEntityType();
            }
        } else {
            entityType = URLDecoder.decode(entityType, "UTF-8");
        }

        if (StringUtils.isBlank(entityType)) {
            List<ClassTree> entityTypes = getAddEntityTypes(cmd.getPolymorphicEntities());
            model.addAttribute("entityTypes", entityTypes);
            model.addAttribute("viewType", "modal/entityTypeSelection");
            model.addAttribute("entityFriendlyName", cmd.getPolymorphicEntities().getFriendlyName());
            String requestUri = request.getRequestURI();
            if (!request.getContextPath().equals("/") && requestUri.startsWith(request.getContextPath())) {
                requestUri = requestUri.substring(request.getContextPath().length() + 1, requestUri.length());
            }
            model.addAttribute("currentUri", requestUri);
            model.addAttribute("modalHeaderType", "addEntity");
            setModelAttributes(model, SECTION_KEY);
            return "modules/modalContainer";
        } else {
            ppr = ppr.withCeilingEntityClassname(entityType);
        }

        ClassMetadata collectionMetadata = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        EntityForm entityForm = formService.createEntityForm(collectionMetadata, sectionCrumbs);
        entityForm.setCeilingEntityClassname(ppr.getCeilingEntityClassname());
        entityForm.setEntityType(ppr.getCeilingEntityClassname());
        formService.removeNonApplicableFields(collectionMetadata, entityForm, ppr.getCeilingEntityClassname());

        entityForm.removeAction(DefaultEntityFormActions.DELETE);
        
        model.addAttribute("entityForm", entityForm);
        model.addAttribute("viewType", "modal/simpleAddEntity");
                
        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("modalHeaderType", "addCollectionItem");
        model.addAttribute("collectionProperty", collectionProperty);
        setModelAttributes(model, SECTION_KEY);
        return "modules/modalContainer";
    }
    
    @Override
    protected String buildAddCollectionItemModel(HttpServletRequest request, HttpServletResponse response,
            Model model,
            String id,
            String collectionField,
            String sectionKey,
            Property collectionProperty,
            FieldMetadata md, PersistencePackageRequest ppr, EntityForm entityForm, Entity entity) throws ServiceException {
        if ("additionalSkus".equals(collectionField) && ppr.getCustomCriteria().length == 0) {
            ppr.withCustomCriteria(new String[] { id });
        }
        return super.buildAddCollectionItemModel(request, response, model, id, collectionField, sectionKey, collectionProperty, md, ppr, entityForm, entity);
    }
    
    protected String showUpdateAdditionalSku(HttpServletRequest request, Model model,
                                             String id, String collectionItemId, Map<String, String> pathVars, EntityForm entityForm) throws Exception {
        String collectionField = "additionalSkus";
        
        // Find out metadata for the additionalSkus property
        String mainClassName = getClassNameForSection(SECTION_KEY);
        List<SectionCrumb> sectionCrumbs = getSectionCrumbs(request, SECTION_KEY, id);
        ClassMetadata mainMetadata = service.getClassMetadata(getSectionPersistencePackageRequest(mainClassName, sectionCrumbs, pathVars)).getDynamicResultSet().getClassMetaData();
        Property collectionProperty = mainMetadata.getPMap().get(collectionField);
        FieldMetadata md = collectionProperty.getMetadata();

        // Find the metadata and the entity for the selected sku
        PersistencePackageRequest ppr = PersistencePackageRequest.fromMetadata(md, sectionCrumbs)
                .withCustomCriteria(new String[] { id });
        ClassMetadata collectionMetadata = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        if (collectionMetadata.getCeilingType().equals(SkuImpl.class.getName())) {
            collectionMetadata.setCeilingType(Sku.class.getName());
        }

        Entity entity = service.getRecord(ppr, collectionItemId, collectionMetadata, true).getDynamicResultSet().getRecords()[0];

        Map<String, DynamicResultSet> subRecordsMap = service.getRecordsForAllSubCollections(ppr, entity, sectionCrumbs);
        if (entityForm == null) {
            entityForm = formService.createEntityForm(collectionMetadata, entity, subRecordsMap, sectionCrumbs);
        } else {
            entityForm.clearFieldsMap();
            formService.populateEntityForm(collectionMetadata, entity, subRecordsMap, entityForm, sectionCrumbs);
            //remove all the actions since we're not trying to redisplay them on the form
            entityForm.removeAllActions();
        }
        
        entityForm.removeAction(DefaultEntityFormActions.DELETE);
        
        // Ensure that operations on the Sku subcollections go to the proper URL
        for (ListGrid lg : entityForm.getAllListGrids()) {
            lg.setSectionKey("com.wakacommerce.core.catalog.domain.Sku");
            lg.setSectionCrumbs(sectionCrumbs);
        }
        
        model.addAttribute("entityForm", entityForm);
        model.addAttribute("viewType", "modal/simpleEditEntity");

        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("modalHeaderType", "updateCollectionItem");
        model.addAttribute("collectionProperty", collectionProperty);
        setModelAttributes(model, SECTION_KEY);
        return "modules/modalContainer";
    }

    @Override
    @RequestMapping(value = "/{id}/{collectionField:.*}/{collectionItemId}", method = RequestMethod.GET)
    public String showUpdateCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable  Map<String, String> pathVars,
            @PathVariable(value="id") String id,
            @PathVariable(value="collectionField") String collectionField,
            @PathVariable(value="collectionItemId") String collectionItemId) throws Exception {
        if ("additionalSkus".equals(collectionField)) {
            return showUpdateAdditionalSku(request, model, id, collectionItemId, pathVars, null);
        }
        return super.showUpdateCollectionItem(request, response, model, pathVars, id, collectionField, collectionItemId);
    }

    @Override
    protected String showViewUpdateCollection(HttpServletRequest request, Model model, Map<String, String> pathVars,
                                              String id, String collectionField, String collectionItemId, String alternateId, String modalHeaderType, EntityForm entityForm, Entity entity) throws ServiceException {
        try {
            if ("additionalSkus".equals(collectionField)) {
                return showUpdateAdditionalSku(request, model, id, collectionItemId, pathVars, entityForm);
            } else {
                return super.showViewUpdateCollection(request, model, pathVars, id, collectionField, collectionItemId, alternateId,
                        "updateCollectionItem", entityForm, entity);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    
    @Override
    @RequestMapping(value = "/{id}/{collectionField}/add", method = RequestMethod.GET)
    public String showAddCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable  Map<String, String> pathVars,
            @PathVariable(value="id") String id,
            @PathVariable(value="collectionField") String collectionField,
            @RequestParam  MultiValueMap<String, String> requestParams) throws Exception {
        if ("additionalSkus".equals(collectionField)) {
            return showAddAdditionalSku(request, response, model, id, pathVars);
        } 
        return super.showAddCollectionItem(request, response, model, pathVars, id, collectionField, requestParams);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewEntityForm(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable Map<String, String> pathVars,
            @PathVariable(value = "id") String id) throws Exception {
        String view = super.viewEntityForm(request, response, model, pathVars, id);
        
        //Skus have a specific toolbar action to generate Skus based on permutations
        EntityForm form = (EntityForm) model.asMap().get("entityForm");
        ListGridAction generateSkusAction = new ListGridAction(ListGridAction.GEN_SKUS).withDisplayText("Generate_Skus")
                                                                .withIconClass("icon-fighter-jet")
                                                                .withButtonClass("generate-skus")
                                                                .withUrlPostfix("/generate-skus");
        
        ListGrid skusGrid = form.findListGrid("additionalSkus");
        if (skusGrid != null) {
            skusGrid.addToolbarAction(generateSkusAction);
            skusGrid.setCanFilterAndSort(false);
        }
        
        // When we're dealing with product bundles, we don't want to render the product options and additional skus
        // list grids. Remove them from the form.
        if (ProductBundle.class.isAssignableFrom(Class.forName(form.getEntityType()))) {
            form.removeListGrid("additionalSkus");
            form.removeListGrid("productOptions");
        }
        
        form.removeListGrid("defaultSku.skuAttributes");
        
        return view;
    }
    
}
