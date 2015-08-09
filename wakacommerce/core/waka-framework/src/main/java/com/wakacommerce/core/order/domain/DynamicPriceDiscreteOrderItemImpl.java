
package com.wakacommerce.core.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.core.catalog.domain.Sku;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @ hui
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_DYN_DISCRETE_ORDER_ITEM")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
@AdminPresentationClass(friendlyName = "DynamicPriceDiscreteOrderItemImpl_dynamicPriceOrderItem")
public class DynamicPriceDiscreteOrderItemImpl extends DiscreteOrderItemImpl implements DynamicPriceDiscreteOrderItem {

    private static final long serialVersionUID = 1L;

    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
        this.name = sku.getName();
    }

    @Override
    public boolean updateSaleAndRetailPrices() {
        return false;
    }

}
