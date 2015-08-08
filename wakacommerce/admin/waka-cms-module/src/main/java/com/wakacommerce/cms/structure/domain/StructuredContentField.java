package com.wakacommerce.cms.structure.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.openadmin.audit.AdminAuditImpl;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Holds the values for custom fields that are part of a <code>StructuredContent</code> item.
 * <br>
 * Each item maintains a list of its custom fields.    The fields associated with an item are
 * determined by the {@link com.wakacommerce.cms.field.domain.FieldDefinition}s  associated
 * with the {@link StructuredContentType}.
 *
 * @see StructuredContentType
 * @see com.wakacommerce.cms.field.domain.FieldDefinition
 *
 */
public interface StructuredContentField extends Serializable, Cloneable, MultiTenantCloneable<StructuredContentField> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    /**
     * Returns the fieldKey associated with this field.   The key used for a
     * <code>StructuredContentField</code> is determined by the associated
     * {@link com.wakacommerce.cms.field.domain.FieldDefinition} that was used by the
     * Content Management System to create this instance.
     *
     * As an example, a <code>StructuredContentType</code> might be configured to contain a
     * field definition with a key of "targetUrl".
     *
     * @return the key associated with this item
     * @see com.wakacommerce.cms.field.domain.FieldDefinition
     */
    @Nonnull
    public String getFieldKey();

    public void setFieldKey(@Nonnull String fieldKey);

    public void setValue(@Nonnull String value);

    @Nonnull
    public String getValue();

    @Nullable
    public AdminAuditImpl getAuditable();

    public void setAuditable(@Nullable AdminAuditImpl auditable);

    public StructuredContentField clone();

}
