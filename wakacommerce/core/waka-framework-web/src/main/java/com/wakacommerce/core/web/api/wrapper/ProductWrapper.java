
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.file.service.StaticAssetPathService;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.util.xml.ISO8601DateAdapter;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductAttribute;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.ProductOption;
import com.wakacommerce.core.catalog.domain.RelatedProduct;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "product")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductWrapper extends BaseWrapper implements APIWrapper<Product> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String name;

    @XmlElement
    protected String description;

    @XmlElement
    protected String longDescription;

    @XmlElement
    protected Money retailPrice;

    @XmlElement
    protected Money salePrice;

    @XmlElement
    protected MediaWrapper primaryMedia;

    @XmlElement
    protected Boolean active;

    @XmlElement(name = "productOption")
    @XmlElementWrapper(name = "productOptions")
    protected List<ProductOptionWrapper> productOptions;

    // For bundles
    @XmlElement
    protected Integer priority;

    @XmlElement
    protected Money bundleItemsRetailPrice;

    @XmlElement
    protected Money bundleItemsSalePrice;

    //End for bundles

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeStartDate;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeEndDate;

    @XmlElement
    protected String manufacturer;

    @XmlElement
    protected String model;

    @XmlElement
    protected String promoMessage;
    
    @XmlElement
    protected Long defaultCategoryId;

    @XmlElement(name = "upsaleProduct")
    @XmlElementWrapper(name = "upsaleProducts")
    protected List<RelatedProductWrapper> upsaleProducts;

    @XmlElement(name = "crossSaleProduct")
    @XmlElementWrapper(name = "crossSaleProducts")
    protected List<RelatedProductWrapper> crossSaleProducts;

    @XmlElement(name = "productAttribute")
    @XmlElementWrapper(name = "productAttributes")
    protected List<ProductAttributeWrapper> productAttributes;

    @XmlElement(name = "media")
    @XmlElementWrapper(name = "mediaItems")
    protected List<MediaWrapper> media;

    @XmlElement(name = "skuBundleItem")
    @XmlElementWrapper(name = "skuBundleItems")
    protected List<SkuBundleItemWrapper> skuBundleItems;

    @Override
    public void wrapDetails(Product model, HttpServletRequest request) {

        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.longDescription = model.getLongDescription();
        this.activeStartDate = model.getActiveStartDate();
        this.activeEndDate = model.getActiveEndDate();
        this.manufacturer = model.getManufacturer();
        this.model = model.getModel();
        this.promoMessage = model.getPromoMessage();
        this.active = model.isActive();

        if (model instanceof ProductBundle) {

            ProductBundle bundle = (ProductBundle) model;
            this.priority = bundle.getPriority();
            this.bundleItemsRetailPrice = bundle.getBundleItemsRetailPrice();
            this.bundleItemsSalePrice = bundle.getBundleItemsSalePrice();

            if (bundle.getSkuBundleItems() != null) {
                this.skuBundleItems = new ArrayList<SkuBundleItemWrapper>();
                List<SkuBundleItem> bundleItems = bundle.getSkuBundleItems();
                for (SkuBundleItem item : bundleItems) {
                    SkuBundleItemWrapper skuBundleItemsWrapper = (SkuBundleItemWrapper) context.getBean(SkuBundleItemWrapper.class.getName());
                    skuBundleItemsWrapper.wrapSummary(item, request);
                    this.skuBundleItems.add(skuBundleItemsWrapper);
                }
            }
        } else {
            this.retailPrice = model.getDefaultSku().getRetailPrice();
            this.salePrice = model.getDefaultSku().getSalePrice();
        }

        if (model.getProductOptions() != null && !model.getProductOptions().isEmpty()) {
            this.productOptions = new ArrayList<ProductOptionWrapper>();
            List<ProductOption> options = model.getProductOptions();
            for (ProductOption option : options) {
                ProductOptionWrapper optionWrapper = (ProductOptionWrapper) context.getBean(ProductOptionWrapper.class.getName());
                optionWrapper.wrapSummary(option, request);
                this.productOptions.add(optionWrapper);
            }
        }

        if (model.getMedia() != null && !model.getMedia().isEmpty()) {
            Media media = model.getMedia().get("primary");
            if (media != null) {
                StaticAssetPathService staticAssetPathService = (StaticAssetPathService) this.context.getBean("blStaticAssetPathService");
                primaryMedia = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
                primaryMedia.wrapDetails(media, request);
                if (primaryMedia.isAllowOverrideUrl()) {
                    primaryMedia.setUrl(staticAssetPathService.convertAssetPath(media.getUrl(), request.getContextPath(), request.isSecure()));
                }
            }
        }
        
        if (model.getDefaultCategory() != null) {
            this.defaultCategoryId = model.getDefaultCategory().getId();
        }

        if (model.getUpSaleProducts() != null && !model.getUpSaleProducts().isEmpty()) {
            upsaleProducts = new ArrayList<RelatedProductWrapper>();
            for (RelatedProduct p : model.getUpSaleProducts()) {
                RelatedProductWrapper upsaleProductWrapper =
                        (RelatedProductWrapper) context.getBean(RelatedProductWrapper.class.getName());
                upsaleProductWrapper.wrapSummary(p, request);
                upsaleProducts.add(upsaleProductWrapper);
            }
        }

        if (model.getCrossSaleProducts() != null && !model.getCrossSaleProducts().isEmpty()) {
            crossSaleProducts = new ArrayList<RelatedProductWrapper>();
            for (RelatedProduct p : model.getCrossSaleProducts()) {
                RelatedProductWrapper crossSaleProductWrapper =
                        (RelatedProductWrapper) context.getBean(RelatedProductWrapper.class.getName());
                crossSaleProductWrapper.wrapSummary(p, request);
                crossSaleProducts.add(crossSaleProductWrapper);
            }
        }

        if (model.getProductAttributes() != null && !model.getProductAttributes().isEmpty()) {
            productAttributes = new ArrayList<ProductAttributeWrapper>();
            if (model.getProductAttributes() != null) {
                for (Map.Entry<String, ProductAttribute> entry : model.getProductAttributes().entrySet()) {
                    ProductAttributeWrapper wrapper = (ProductAttributeWrapper) context.getBean(ProductAttributeWrapper.class.getName());
                    wrapper.wrapSummary(entry.getValue(), request);
                    productAttributes.add(wrapper);
                }
            }
        }

        if (model.getMedia() != null && !model.getMedia().isEmpty()) {
            Map<String, Media> mediaMap = model.getMedia();
            media = new ArrayList<MediaWrapper>();
            StaticAssetPathService staticAssetPathService = (StaticAssetPathService) this.context.getBean("blStaticAssetPathService");
            for (Media med : mediaMap.values()) {
                MediaWrapper wrapper = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
                wrapper.wrapSummary(med, request);
                if (wrapper.isAllowOverrideUrl()) {
                    wrapper.setUrl(staticAssetPathService.convertAssetPath(med.getUrl(), request.getContextPath(), request.isSecure()));
                }
                media.add(wrapper);
            }
        }
    }

    @Override
    public void wrapSummary(Product model, HttpServletRequest request) {
        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.longDescription = model.getLongDescription();
        this.active = model.isActive();

        if (model instanceof ProductBundle) {

            ProductBundle bundle = (ProductBundle) model;
            this.priority = bundle.getPriority();
            this.bundleItemsRetailPrice = bundle.getBundleItemsRetailPrice();
            this.bundleItemsSalePrice = bundle.getBundleItemsSalePrice();
        } else {
            this.retailPrice = model.getDefaultSku().getRetailPrice();
            this.salePrice = model.getDefaultSku().getSalePrice();
        }

        if (model.getProductOptions() != null && !model.getProductOptions().isEmpty()) {
            this.productOptions = new ArrayList<ProductOptionWrapper>();
            List<ProductOption> options = model.getProductOptions();
            for (ProductOption option : options) {
                ProductOptionWrapper optionWrapper = (ProductOptionWrapper) context.getBean(ProductOptionWrapper.class.getName());
                optionWrapper.wrapSummary(option, request);
                this.productOptions.add(optionWrapper);
            }
        }

        if (model.getMedia() != null && !model.getMedia().isEmpty()) {
            Media media = model.getMedia().get("primary");
            if (media != null) {
                StaticAssetPathService staticAssetPathService = (StaticAssetPathService) this.context.getBean("blStaticAssetPathService");
                primaryMedia = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
                primaryMedia.wrapDetails(media, request);
                if (primaryMedia.isAllowOverrideUrl()) {
                    primaryMedia.setUrl(staticAssetPathService.convertAssetPath(media.getUrl(), request.getContextPath(), request.isSecure()));
                }
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Money getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Money retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Money getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Money salePrice) {
        this.salePrice = salePrice;
    }

    public MediaWrapper getPrimaryMedia() {
        return primaryMedia;
    }

    public void setPrimaryMedia(MediaWrapper primaryMedia) {
        this.primaryMedia = primaryMedia;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ProductOptionWrapper> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptionWrapper> productOptions) {
        this.productOptions = productOptions;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Money getBundleItemsRetailPrice() {
        return bundleItemsRetailPrice;
    }

    public void setBundleItemsRetailPrice(Money bundleItemsRetailPrice) {
        this.bundleItemsRetailPrice = bundleItemsRetailPrice;
    }

    public Money getBundleItemsSalePrice() {
        return bundleItemsSalePrice;
    }

    public void setBundleItemsSalePrice(Money bundleItemsSalePrice) {
        this.bundleItemsSalePrice = bundleItemsSalePrice;
    }

    public Date getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(Date activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public Date getActiveEndDate() {
        return activeEndDate;
    }

    public void setActiveEndDate(Date activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPromoMessage() {
        return promoMessage;
    }

    public void setPromoMessage(String promoMessage) {
        this.promoMessage = promoMessage;
    }

    public Long getDefaultCategoryId() {
        return defaultCategoryId;
    }

    public void setDefaultCategoryId(Long defaultCategoryId) {
        this.defaultCategoryId = defaultCategoryId;
    }

    public List<RelatedProductWrapper> getUpsaleProducts() {
        return upsaleProducts;
    }

    public void setUpsaleProducts(List<RelatedProductWrapper> upsaleProducts) {
        this.upsaleProducts = upsaleProducts;
    }

    public List<RelatedProductWrapper> getCrossSaleProducts() {
        return crossSaleProducts;
    }

    public void setCrossSaleProducts(List<RelatedProductWrapper> crossSaleProducts) {
        this.crossSaleProducts = crossSaleProducts;
    }

    public List<ProductAttributeWrapper> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttributeWrapper> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public List<MediaWrapper> getMedia() {
        return media;
    }

    public void setMedia(List<MediaWrapper> media) {
        this.media = media;
    }

    public List<SkuBundleItemWrapper> getSkuBundleItems() {
        return skuBundleItems;
    }

    public void setSkuBundleItems(List<SkuBundleItemWrapper> skuBundleItems) {
        this.skuBundleItems = skuBundleItems;
    }

}
