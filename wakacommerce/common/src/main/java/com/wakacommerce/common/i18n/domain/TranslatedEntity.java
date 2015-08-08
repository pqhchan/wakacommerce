package com.wakacommerce.common.i18n.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wakacommerce.common.WakaEnumType;

public class TranslatedEntity implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, TranslatedEntity> TYPES = new LinkedHashMap<String, TranslatedEntity>();

    public static final TranslatedEntity PRODUCT = new TranslatedEntity("com.wakacommerce.core.catalog.domain.Product", "Product");
    public static final TranslatedEntity SKU = new TranslatedEntity("com.wakacommerce.core.catalog.domain.Sku", "Sku");
    public static final TranslatedEntity CATEGORY = new TranslatedEntity("com.wakacommerce.core.catalog.domain.Category", "Category");
    public static final TranslatedEntity PRODUCT_OPTION = new TranslatedEntity("com.wakacommerce.core.catalog.domain.ProductOption", "ProdOption");
    public static final TranslatedEntity PRODUCT_OPTION_VALUE = new TranslatedEntity("com.wakacommerce.core.catalog.domain.ProductOptionValue", "ProdOptionVal");
    public static final TranslatedEntity STATIC_ASSET = new TranslatedEntity("com.wakacommerce.cms.file.domain.StaticAsset", "StaticAsset");
    public static final TranslatedEntity SEARCH_FACET = new TranslatedEntity("com.wakacommerce.core.search.domain.SearchFacet", "SearchFacet");
    public static final TranslatedEntity FULFILLMENT_OPTION = new TranslatedEntity("com.wakacommerce.core.order.domain.FulfillmentOption", "FulfillmentOption");
    public static final TranslatedEntity OFFER = new TranslatedEntity("com.wakacommerce.core.offer.domain.Offer", "Offer");
    public static final TranslatedEntity CHALLENGE_QUESTION = new TranslatedEntity("com.wakacommerce.profile.core.domain.ChallengeQuestion", "ChallengeQuestion");
    public static final TranslatedEntity SKU_ATTRIBUTE = new TranslatedEntity("com.wakacommerce.core.catalog.domain.SkuAttribute", "SkuAttribute");
    public static final TranslatedEntity PRODUCT_ATTRIBUTE = new TranslatedEntity("com.wakacommerce.core.catalog.domain.ProductAttribute", "ProductAttribute");
    public static final TranslatedEntity CATEGORY_ATTRIBUTE = new TranslatedEntity("com.wakacommerce.core.catalog.domain.CategoryAttribute", "CategoryAttribute");
    public static final TranslatedEntity CUSTOMER_ATTRIBUTE = new TranslatedEntity("com.wakacommerce.profile.core.domain.CustomerAttribute", "CustomerAttribute");
    public static final TranslatedEntity PAGE = new TranslatedEntity("com.wakacommerce.cms.page.domain.Page", "Page");
    public static final TranslatedEntity PAGE_TEMPLATE = new TranslatedEntity("com.wakacommerce.cms.page.domain.PageTemplate", "PageTemplate");
    public static final TranslatedEntity STRUCTURED_CONTENT_TYPE = new TranslatedEntity("com.wakacommerce.cms.structure.domain.StructuredContentType", "StructuredContentType");
    public static final TranslatedEntity COUNTRY = new TranslatedEntity("com.wakacommerce.profile.core.domain.Country", "Country");
    public static final TranslatedEntity COUNTRY_SUBDIVISION = new TranslatedEntity("com.wakacommerce.profile.core.domain.CountrySubdivision", "CountrySubdivision");
    public static final TranslatedEntity COUNTRY_SUBDIVISION_CATEGORY = new TranslatedEntity("com.wakacommerce.profile.core.domain.CountrySubdivisionCategory", "CountrySubdivisionCategory");

    public static TranslatedEntity getInstance(final String type) {
        return TYPES.get(type);
    }
    
    public static TranslatedEntity getInstanceFromFriendlyType(final String friendlyType) {
        for (Entry<String, TranslatedEntity> entry : TYPES.entrySet()) {
            if (entry.getValue().getFriendlyType().equals(friendlyType)) {
                return entry.getValue();
            }
        }
        
        return null;
    }

    private String type;
    private String friendlyType;

    public TranslatedEntity() {
        //do nothing
    }

    public TranslatedEntity(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }

    public static Map<String, TranslatedEntity> getTypes() {
        return TYPES;
    }
    
    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        TranslatedEntity other = (TranslatedEntity) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
