
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface OrderLock extends Serializable {

    public Long getOrderId();

    public void setOrderId(Long orderId);

    public Boolean getLocked();

    public void setLocked(Boolean locked);

    Long getLastUpdated();

    void setLastUpdated(Long lastUpdated);

    String getKey();

    void setKey(String nodeKey);
}