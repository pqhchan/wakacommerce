/*
 * #%L
 * BroadleafCommerce CMS Module
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.cms.field.domain;

import java.io.Serializable;

import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValue;

/**
 * Created by jfischer
 * @deprecated use {@link DataDrivenEnumerationValue} instead
 */
@Deprecated
public interface FieldEnumerationItem extends Serializable {
    
    FieldEnumeration getFieldEnumeration();

    void setFieldEnumeration(FieldEnumeration fieldEnumeration);

    int getFieldOrder();

    void setFieldOrder(int fieldOrder);

    String getFriendlyName();

    void setFriendlyName(String friendlyName);

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);
}
