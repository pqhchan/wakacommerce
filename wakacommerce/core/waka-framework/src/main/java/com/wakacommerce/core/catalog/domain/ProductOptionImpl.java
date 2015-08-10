
package com.wakacommerce.core.catalog.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.i18n.service.DynamicTranslationProvider;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.AdminPresentationCollection;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.RequiredOverride;
import com.wakacommerce.common.presentation.client.AddMethodType;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.core.catalog.service.type.ProductOptionType;
import com.wakacommerce.core.catalog.service.type.ProductOptionValidationStrategyType;
import com.wakacommerce.core.catalog.service.type.ProductOptionValidationType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_OPTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "ProductOptionImpl_baseProductOption", populateToOneFields=PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class ProductOptionImpl implements ProductOption, AdminMainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "ProductOptionId")
    @GenericGenerator(
        name="ProductOptionId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="ProductOptionImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.catalog.domain.ProductOptionImpl")
        }
    )
    @Column(name = "PRODUCT_OPTION_ID")
    protected Long id;
    
    @Column(name = "OPTION_TYPE")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_type", 
    		fieldType = SupportedFieldType.WAKA_ENUMERATION, 
    		wakaEnumeration = "com.wakacommerce.core.catalog.service.type.ProductOptionType")
    protected String type;
    
    @Column(name = "ATTRIBUTE_NAME")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_attributeName", 
    		helpText = "ProductOptionImpl_attributeName_help", 
    		requiredOverride = RequiredOverride.REQUIRED)
    protected String attributeName;
    
    @Column(name = "LABEL")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_label", 
    		helpText = "ProductOptionImpl_label_help", 
    		prominent = true)
    protected String label;

    @Column(name = "REQUIRED")
    @AdminPresentation(friendlyName = "ProductOptionImpl_required", prominent = true)
    protected Boolean required;

    @Column(name = "USE_IN_SKU_GENERATION")
    @AdminPresentation(friendlyName = "ProductOptionImpl_useInSkuGeneration")
    private Boolean useInSkuGeneration;

    @Column(name = "DISPLAY_ORDER")
    @AdminPresentation(friendlyName = "ProductOptionImpl_displayOrder")
    protected Integer displayOrder;

    @Column(name = "VALIDATION_STRATEGY_TYPE")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_productOptionValidationStrategyType", 
    		group = "ProductOptionImpl_grp_validation", 
    		fieldType = SupportedFieldType.WAKA_ENUMERATION, 
    		wakaEnumeration = "com.wakacommerce.core.catalog.service.type.ProductOptionValidationStrategyType")
    private String productOptionValidationStrategyType;

    @Column(name = "VALIDATION_TYPE")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_productOptionValidationType", 
    		group = "ProductOptionImpl_grp_validation", 
    		fieldType = SupportedFieldType.WAKA_ENUMERATION, 
    		wakaEnumeration = "com.wakacommerce.core.catalog.service.type.ProductOptionValidationType")
    private String productOptionValidationType;

    @Column(name = "VALIDATION_STRING")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_validationString",
    		group = "ProductOptionImpl_grp_validation")
    protected String validationString;

    @Column(name = "ERROR_CODE")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_errorCode", 
    		group = "ProductOptionImpl_grp_validation")
    protected String errorCode;

    @Column(name = "ERROR_MESSAGE")
    @AdminPresentation(
    		friendlyName = "ProductOptionImpl_errorMessage", 
    		group = "ProductOptionImpl_grp_validation")
    protected String errorMessage;

    @OneToMany(mappedBy = "productOption", targetEntity = ProductOptionValueImpl.class, cascade = {CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @OrderBy(value = "displayOrder")
    @AdminPresentationCollection(
    		addType = AddMethodType.PERSIST, 
    		friendlyName = "ProductOptionImpl_allowedValues")
    protected List<ProductOptionValue> allowedValues = new ArrayList<ProductOptionValue>();

    @OneToMany(targetEntity = ProductOptionXrefImpl.class, mappedBy = "productOption")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    protected List<ProductOptionXref> products = new ArrayList<ProductOptionXref>();
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ProductOptionType getType() {
        return ProductOptionType.getInstance(type);
    }

    @Override
    public void setType(ProductOptionType type) {
        this.type = type == null ? null : type.getType();
    }
    
    @Override
    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
    @Override
    public String getLabel() {
        return DynamicTranslationProvider.getValue(this, "label", label);
    }
    
    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

    @Override
    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public List<ProductOptionXref> getProductXrefs() {
        return products;
    }

    @Override
    public void setProductXrefs(List<ProductOptionXref> xrefs) {
        this.products = xrefs;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> response = new ArrayList<Product>();
        for (ProductOptionXref xref : products) {
            response.add(xref.getProduct());
        }
        return Collections.unmodifiableList(response);
    }

    @Override
    public void setProducts(List<Product> products){
        throw new UnsupportedOperationException("Use setProductOptionXrefs(..) instead");
    }

    @Override
    public List<ProductOptionValue> getAllowedValues() {
        return allowedValues;
    }

    @Override
    public void setAllowedValues(List<ProductOptionValue> allowedValues) {
        this.allowedValues = allowedValues;
    }

    @Override
    public Boolean getUseInSkuGeneration() {
        return (useInSkuGeneration == null) ? true : useInSkuGeneration;
    }

    @Override
    public void setUseInSkuGeneration(Boolean useInSkuGeneration) {
        this.useInSkuGeneration = useInSkuGeneration;
    }

    @Override
    public ProductOptionValidationStrategyType getProductOptionValidationStrategyType() {
        return ProductOptionValidationStrategyType.getInstance(productOptionValidationStrategyType);
    }

    @Override
    public void setProductOptionValidationStrategyType(ProductOptionValidationStrategyType productOptionValidationStrategyType) {
        this.productOptionValidationStrategyType = productOptionValidationStrategyType == null ? null : productOptionValidationStrategyType.getType();
    }

    @Override
    public ProductOptionValidationType getProductOptionValidationType() {
        return ProductOptionValidationType.getInstance(productOptionValidationType);
    }

    @Override
    public void setProductOptionValidationType(ProductOptionValidationType productOptionValidationType) {
        this.productOptionValidationType = productOptionValidationType == null ? null : productOptionValidationType.getType();
    }

    @Override
    public String getValidationString() {
        return validationString;
    }

    @Override
    public void setValidationString(String validationString) {
        this.validationString = validationString;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return DynamicTranslationProvider.getValue(this, "errorMessage", errorMessage);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMainEntityName() {
        return getLabel();
    }

    @Override
    public <G extends ProductOption> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        ProductOption cloned = createResponse.getClone();
        cloned.setAttributeName(attributeName);
        cloned.setDisplayOrder(displayOrder);
        cloned.setErrorMessage(errorMessage);
        cloned.setErrorCode(errorCode);
        cloned.setLabel(label);
        cloned.setRequired(getRequired());
        cloned.setUseInSkuGeneration(getUseInSkuGeneration());
        cloned.setValidationString(validationString);
        cloned.setType(getType());
        cloned.setProductOptionValidationStrategyType(getProductOptionValidationStrategyType());
        cloned.setProductOptionValidationType(getProductOptionValidationType());
        for(ProductOptionValue entry : allowedValues){
            ProductOptionValue clonedEntry = entry.createOrRetrieveCopyInstance(context).getClone();
            cloned.getAllowedValues().add(clonedEntry);
        }
        for(ProductOptionXref entry : products){
            ProductOptionXref clonedEntry = entry.createOrRetrieveCopyInstance(context).getClone();
            cloned.getProductXrefs().add(clonedEntry);
        }

        return createResponse;
    }
}
