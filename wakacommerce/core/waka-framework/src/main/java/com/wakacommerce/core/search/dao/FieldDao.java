
package com.wakacommerce.core.search.dao;

import java.util.List;

import com.wakacommerce.core.search.domain.Field;
import com.wakacommerce.core.search.domain.FieldEntity;

/**
 * DAO to facilitate interaction with Broadleaf fields.
 * 
 *Andre Azzolini (apazzolini)
 */
public interface FieldDao {

    /**
     * Given an abbreviation, returns the Field object that maps to this abbreviation.
     * Note that the default Broadleaf implementation of Field will enforce a uniqueness
     * constraint on the abbreviation field and this method will reliably return one field
     * 
     * @param abbreviation
     * @return the Field that has this abbreviation
     */
    public Field readFieldByAbbreviation(String abbreviation);

    /**
     * Reads all Field objects that are set to searchable. This is typically used to build an
     * index for searching. Note that the default Broadleaf implementation returns only fields that
     * have a FieldEntity equal to PRODUCT
     * 
     * @return the product Fields
     */
    public List<Field> readAllProductFields();

    /**
     * Reads all Field objects that are set to searchable. This is typically used to build an
     * index for searching. Note that the default Broadleaf implementation returns only fields that
     * have a FieldEntity equal to SKU
     * 
     * @return the product Fields
     */
    public List<Field> readAllSkuFields();

    /**
     * Finds all fields based on the entity type.
     * @param entityType
     * @return
     */
    public List<Field> readFieldsByEntityType(FieldEntity entityType);

    /**
     * Persist an instance to the data layer.
     *
     * @param field the instance to persist
     * @return the instance after it has been persisted
     */
    public Field save(Field field);
}
