package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

public interface PromotableProduct extends Serializable {
	
    Product getRelatedProduct();
    
    String getPromotionMessage();
    
}
