
package com.wakacommerce.core.pricing.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.config.service.ModuleConfigurationService;
import com.wakacommerce.common.config.service.type.ModuleConfigurationType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.TaxException;
import com.wakacommerce.core.pricing.service.tax.provider.TaxProvider;

import java.util.List;

import javax.annotation.Resource;

@Service("blTaxService")
public class TaxServiceImpl implements TaxService {

    protected boolean mustCalculate = false;

    @Resource(name = "blTaxProviders")
    protected List<TaxProvider> providers;

    @Resource(name = "blModuleConfigurationService")
    protected ModuleConfigurationService moduleConfigService;

    @Override
    public Order calculateTaxForOrder(Order order) throws TaxException {

        List<ModuleConfiguration> configurations =
                moduleConfigService.findActiveConfigurationsByType(ModuleConfigurationType.TAX_CALCULATION);

        //Try to find a default configuration
        ModuleConfiguration config = null;
        if (configurations != null) {
            for (ModuleConfiguration configuration : configurations) {
                if (configuration.getIsDefault()) {
                    config = configuration;
                    break;
                }
            }

            if (config == null && CollectionUtils.isNotEmpty(configurations)) {
                //if there wasn't a default one, use the first active one...
                config = configurations.get(0);
            }
        }

        if (CollectionUtils.isNotEmpty(providers)) {
            for (TaxProvider provider : providers) {
                if (provider.canRespond(config)) {
                    return provider.calculateTaxForOrder(order, config);
                }
            }
        }
        
        // haven't returned anything, nothing must have run
        if (!mustCalculate) {
            return order;
        }
        throw new TaxException("No eligible tax providers were configured.");
    }

    @Override
    public Order commitTaxForOrder(Order order) throws TaxException {

        List<ModuleConfiguration> configurations =
                moduleConfigService.findActiveConfigurationsByType(ModuleConfigurationType.TAX_CALCULATION);

        if (configurations != null && !configurations.isEmpty()) {

            //Try to find a default configuration
            ModuleConfiguration config = null;
            for (ModuleConfiguration configuration : configurations) {
                if (configuration.getIsDefault()) {
                    config = configuration;
                    break;
                }
            }

            if (config == null) {
                //if there wasn't a default one, use the first active one...
                config = configurations.get(0);
            }

            if (providers != null && !providers.isEmpty()) {
                for (TaxProvider provider : providers) {
                    if (provider.canRespond(config)) {
                        return provider.commitTaxForOrder(order, config);
                    }
                }
            }
        }
        if (!mustCalculate) {
            return order;
        }
        throw new TaxException("No eligible tax providers were configured.");
    }

    @Override
    public void cancelTax(Order order) throws TaxException {
        List<ModuleConfiguration> configurations =
                moduleConfigService.findActiveConfigurationsByType(ModuleConfigurationType.TAX_CALCULATION);

        if (configurations != null && !configurations.isEmpty()) {

            //Try to find a default configuration
            ModuleConfiguration config = null;
            for (ModuleConfiguration configuration : configurations) {
                if (configuration.getIsDefault()) {
                    config = configuration;
                    break;
                }
            }

            if (config == null) {
                //if there wasn't a default one, use the first active one...
                config = configurations.get(0);
            }

            if (providers != null && !providers.isEmpty()) {
                for (TaxProvider provider : providers) {
                    if (provider.canRespond(config)) {
                        provider.cancelTax(order, config);
                        return;
                    }
                }
            }
        }
        if (mustCalculate) {
            throw new TaxException("No eligible tax providers were configured.");
        }
    }

    public void setTaxProviders(List<TaxProvider> providers) {
        this.providers = providers;
    }

    public void setMustCalculate(boolean mustCalculate) {
        this.mustCalculate = mustCalculate;
    }
}
