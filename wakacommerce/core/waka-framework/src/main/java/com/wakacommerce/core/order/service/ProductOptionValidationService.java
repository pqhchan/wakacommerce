

package com.wakacommerce.core.order.service;

import com.wakacommerce.core.catalog.domain.ProductOption;

public interface ProductOptionValidationService {

    public abstract Boolean validate(ProductOption productOption, String value);

}
