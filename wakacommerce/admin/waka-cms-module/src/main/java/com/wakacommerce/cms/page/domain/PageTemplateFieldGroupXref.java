  
package com.wakacommerce.cms.page.domain;

import com.wakacommerce.cms.field.domain.FieldGroup;
import com.wakacommerce.common.copy.MultiTenantCloneable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @ hui
 */
public interface PageTemplateFieldGroupXref extends Serializable, MultiTenantCloneable<PageTemplateFieldGroupXref> {

    public void setId(Long id);

    public Long getId();

    public void setPageTemplate(PageTemplate pageTemplate);

    public PageTemplate getPageTemplate();

    public void setFieldGroup(FieldGroup fieldGroup);

    public FieldGroup getFieldGroup();

    public void setGroupOrder(BigDecimal groupOrder);

    public BigDecimal getGroupOrder();

}
