package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.i18n.domain.Translation;
import com.wakacommerce.openadmin.web.form.TranslationForm;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;

import java.util.List;

public interface TranslationFormBuilderService {

    public ListGrid buildListGrid(List<Translation> translations, boolean isRte);

    public EntityForm buildTranslationForm(TranslationForm formProperties);


}
