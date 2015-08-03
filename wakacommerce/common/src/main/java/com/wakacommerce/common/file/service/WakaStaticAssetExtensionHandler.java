package com.wakacommerce.common.file.service;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Site;

public interface WakaStaticAssetExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType removeShareOptionsForMTStandardSite(Model model, Site currentSite);

}
