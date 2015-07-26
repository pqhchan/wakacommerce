
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.i18n.domain.Translation;
import com.wakacommerce.openadmin.web.form.TranslationForm;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;

import java.util.List;

public interface TranslationFormBuilderService {

    /**
     * Builds a ListGrid for the given list of translations
     * 
     * @param translations
     * @param isRte - whether or not the field that this translation is tied to is a rich text edit field
     * @return the list grid
     */
    public ListGrid buildListGrid(List<Translation> translations, boolean isRte);

    /**
     * Builds an EntityForm used to create or edit a translation value
     * 
     * @param formProperties
     * @return the entity form
     */
    public EntityForm buildTranslationForm(TranslationForm formProperties);


}
