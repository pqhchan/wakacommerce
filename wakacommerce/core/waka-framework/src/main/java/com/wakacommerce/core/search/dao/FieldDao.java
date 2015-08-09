
package com.wakacommerce.core.search.dao;

import java.util.List;

import com.wakacommerce.core.search.domain.Field;
import com.wakacommerce.core.search.domain.FieldEntity;

/**
 *
 * @ hui
 */
public interface FieldDao {

    public Field readFieldByAbbreviation(String abbreviation);

    public List<Field> readAllProductFields();

    public List<Field> readAllSkuFields();

    public List<Field> readFieldsByEntityType(FieldEntity entityType);

    public Field save(Field field);
}
