
package com.wakacommerce.common.web;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

/**
 * Placeholder component to support a custom TemplateResolver.
 * 
 * Utilized by the Broadleaf Commerce CustomTemplate extension to introduce themes at the DB level.
 *
 *bpolster
 */
public class NullBroadleafTemplateResolver implements ITemplateResolver {

    @Override
    public String getName() {
        return "NullBroadleafTemplateResolver";
    }

    @Override
    public Integer getOrder() {
        return 9999;
    }

    @Override
    public TemplateResolution resolveTemplate(TemplateProcessingParameters templateProcessingParameters) {
        return null;
    }

    @Override
    public void initialize() {

    }
}
