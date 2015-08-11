package com.wakacommerce.core.pricing.service.tax.provider;

import org.apache.commons.lang.StringUtils;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupFee;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.TaxDetail;
import com.wakacommerce.core.order.domain.TaxType;
import com.wakacommerce.core.pricing.service.exception.TaxException;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.Country;
import com.wakacommerce.profile.core.domain.State;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class SimpleTaxProvider implements TaxProvider {

    protected Map<String, Double> itemPostalCodeTaxRateMap;
    protected Map<String, Double> itemCityTaxRateMap;
    protected Map<String, Double> itemStateTaxRateMap;
    protected Map<String, Double> itemCountryTaxRateMap;

    protected Map<String, Double> fulfillmentGroupPostalCodeTaxRateMap;
    protected Map<String, Double> fulfillmentGroupCityTaxRateMap;
    protected Map<String, Double> fulfillmentGroupStateTaxRateMap;
    protected Map<String, Double> fulfillmentGroupCountryTaxRateMap;

    protected Double defaultItemTaxRate;
    protected Double defaultFulfillmentGroupTaxRate;

    protected boolean taxFees;
    
    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfig;
    
    @Override
    public boolean canRespond(ModuleConfiguration config) {
        // this will only be executed with null module configurations
        return config == null;
    }

    @Override
    public Order calculateTaxForOrder(Order order, ModuleConfiguration config) throws TaxException {
        return order;
    }

    @Override
    public Order commitTaxForOrder(Order order, ModuleConfiguration config) throws TaxException {
        // intentionally left blank; no tax needs to be committed as this already has the tax details on the order
        return order;
    }

    @Override
    public void cancelTax(Order order, ModuleConfiguration config) throws TaxException {
        // intentionally left blank; tax never got committed so it never gets cancelled
    }

	public Double lookupPostalCodeRate(Map<String,Double> postalCodeTaxRateMap, String postalCode) {
        if (postalCodeTaxRateMap != null && postalCode != null) {
            return postalCodeTaxRateMap.get(postalCode);
        }
        return null;
    }

    public Double lookupCityRate(Map<String,Double> cityTaxRateMap, String city) {
        if (cityTaxRateMap != null && city != null) {
            city = city.toUpperCase();
            return cityTaxRateMap.get(city);
        }
        return null;
    }

    public Double lookupStateRate(Map<String,Double> stateTaxRateMap, State state) {
        if (stateTaxRateMap != null && state != null && state.getAbbreviation() != null) {
            String stateAbbr = state.getAbbreviation().toUpperCase();
            Double rate = stateTaxRateMap.get(stateAbbr);
            if (rate == null && state.getName() != null) {
                String stateName = state.getName().toUpperCase();
                return stateTaxRateMap.get(stateName);
            } else {
                return rate;
            }
        }
        return null;
    }

    public Double lookupStateRate(Map<String,Double> stateTaxRateMap, String stateProvinceRegion) {
        if (stateTaxRateMap != null && StringUtils.isNotBlank(stateProvinceRegion)) {
            return stateTaxRateMap.get(stateProvinceRegion);
        }
        return null;
    }

    public Double lookupCountryRate(Map<String,Double> countryTaxRateMap, Country country) {
        if (countryTaxRateMap != null && country != null && country.getAbbreviation() != null) {
            String cntryAbbr = country.getAbbreviation().toUpperCase();
            Double rate = countryTaxRateMap.get(cntryAbbr);
            if (rate == null && country.getName() != null) {
                String countryName = country.getName().toUpperCase();
                return countryTaxRateMap.get(countryName);
            } else {
                return rate;
            }
        }
        return null;
    }

    public Double lookupCountryRate(Map<String,Double> countryTaxRateMap, ISOCountry isoCountry) {
        if (countryTaxRateMap != null && isoCountry != null && isoCountry.getAlpha2() != null) {
            String cntryAbbr = isoCountry.getAlpha2().toUpperCase();
            Double rate = countryTaxRateMap.get(cntryAbbr);
            if (rate == null && isoCountry.getName() != null) {
                String countryName = isoCountry.getName().toUpperCase();
                return countryTaxRateMap.get(countryName);
            } else {
                return rate;
            }
        }
        return null;
    }

    protected boolean isItemTaxable(FulfillmentGroupItem item) {
        return item.getOrderItem().isTaxable();
    }

    protected boolean isFeeTaxable(FulfillmentGroupFee fee) {
        return fee.isTaxable();
    }

    public BigDecimal determineItemTaxRate(Address address) {
        if (address != null) {
            Double postalCodeRate = lookupPostalCodeRate(itemPostalCodeTaxRateMap, address.getPostalCode());
            if (postalCodeRate != null) {
                return BigDecimal.valueOf(postalCodeRate);
            }
            Double cityCodeRate = lookupCityRate(itemCityTaxRateMap, address.getCity());
            if (cityCodeRate != null) {
                return BigDecimal.valueOf(cityCodeRate);
            }

            Double stateCodeRate;
            if (StringUtils.isNotBlank(address.getStateProvinceRegion())) {
                stateCodeRate = lookupStateRate(itemStateTaxRateMap, address.getStateProvinceRegion());
            } else {
                stateCodeRate = lookupStateRate(itemStateTaxRateMap, address.getState());
            }

            if (stateCodeRate != null) {
                return BigDecimal.valueOf(stateCodeRate);
            }

            Double countryCodeRate;
            if (address.getIsoCountryAlpha2() != null) {
                countryCodeRate = lookupCountryRate(itemCountryTaxRateMap, address.getIsoCountryAlpha2());
            } else {
                countryCodeRate = lookupCountryRate(itemCountryTaxRateMap, address.getCountry());
            }

            if (countryCodeRate != null) {
                return BigDecimal.valueOf(countryCodeRate);
            }
        }

        if (defaultItemTaxRate != null) {
            return BigDecimal.valueOf(defaultItemTaxRate);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal determineTaxRateForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        boolean isTaxable = true;

        if (fulfillmentGroup.isShippingPriceTaxable() != null) {
            isTaxable = fulfillmentGroup.isShippingPriceTaxable();
        }

        if (isTaxable) {
            Address address = fulfillmentGroup.getAddress();
            if (address != null) {
                Double postalCodeRate = lookupPostalCodeRate(fulfillmentGroupPostalCodeTaxRateMap, address.getPostalCode());
                if (postalCodeRate != null) {
                    return BigDecimal.valueOf(postalCodeRate);
                }
                Double cityCodeRate = lookupCityRate(fulfillmentGroupCityTaxRateMap, address.getCity());
                if (cityCodeRate != null) {
                    return BigDecimal.valueOf(cityCodeRate);
                }

                Double stateCodeRate;
                if (StringUtils.isNotBlank(address.getStateProvinceRegion())) {
                    stateCodeRate = lookupStateRate(fulfillmentGroupStateTaxRateMap, address.getStateProvinceRegion());
                } else {
                    stateCodeRate = lookupStateRate(fulfillmentGroupStateTaxRateMap, address.getState());
                }

                if (stateCodeRate != null) {
                    return BigDecimal.valueOf(stateCodeRate);
                }

                Double countryCodeRate;
                if (address.getIsoCountryAlpha2() != null) {
                    countryCodeRate = lookupCountryRate(fulfillmentGroupCountryTaxRateMap, address.getIsoCountryAlpha2());
                } else {
                    countryCodeRate = lookupCountryRate(fulfillmentGroupCountryTaxRateMap, address.getCountry());
                }

                if (countryCodeRate != null) {
                    return BigDecimal.valueOf(countryCodeRate);
                }
            }

            if (defaultFulfillmentGroupTaxRate != null) {
                return BigDecimal.valueOf(defaultFulfillmentGroupTaxRate);
            }
        }
        return BigDecimal.ZERO;
    }

    public Map<String, Double> getItemPostalCodeTaxRateMap() {
        return itemPostalCodeTaxRateMap;
    }

    public void setItemPostalCodeTaxRateMap(Map<String, Double> itemPostalCodeTaxRateMap) {
        this.itemPostalCodeTaxRateMap = itemPostalCodeTaxRateMap;
    }

    public Map<String, Double> getItemCityTaxRateMap() {
        return itemCityTaxRateMap;
    }

    public void setItemCityTaxRateMap(Map<String, Double> itemCityTaxRateMap) {
        this.itemCityTaxRateMap = itemCityTaxRateMap;
    }

    public Map<String, Double> getItemStateTaxRateMap() {
        return itemStateTaxRateMap;
    }

    public void setItemStateTaxRateMap(Map<String, Double> itemStateTaxRateMap) {
        this.itemStateTaxRateMap = itemStateTaxRateMap;
    }

    public Map<String, Double> getItemCountryTaxRateMap() {
        return itemCountryTaxRateMap;
    }

    public void setItemCountryTaxRateMap(Map<String, Double> itemCountryTaxRateMap) {
        this.itemCountryTaxRateMap = itemCountryTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupPostalCodeTaxRateMap() {
        return fulfillmentGroupPostalCodeTaxRateMap;
    }

    public void setFulfillmentGroupPostalCodeTaxRateMap(Map<String, Double> fulfillmentGroupPostalCodeTaxRateMap) {
        this.fulfillmentGroupPostalCodeTaxRateMap = fulfillmentGroupPostalCodeTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupCityTaxRateMap() {
        return fulfillmentGroupCityTaxRateMap;
    }

    public void setFulfillmentGroupCityTaxRateMap(Map<String, Double> fulfillmentGroupCityTaxRateMap) {
        this.fulfillmentGroupCityTaxRateMap = fulfillmentGroupCityTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupStateTaxRateMap() {
        return fulfillmentGroupStateTaxRateMap;
    }

    public void setFulfillmentGroupStateTaxRateMap(Map<String, Double> fulfillmentGroupStateTaxRateMap) {
        this.fulfillmentGroupStateTaxRateMap = fulfillmentGroupStateTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupCountryTaxRateMap() {
        return fulfillmentGroupCountryTaxRateMap;
    }

    public void setFulfillmentGroupCountryTaxRateMap(Map<String, Double> fulfillmentGroupCountryTaxRateMap) {
        this.fulfillmentGroupCountryTaxRateMap = fulfillmentGroupCountryTaxRateMap;
    }

    public Double getDefaultItemTaxRate() {
        return defaultItemTaxRate;
    }

    public void setDefaultItemTaxRate(Double defaultItemTaxRate) {
        this.defaultItemTaxRate = defaultItemTaxRate;
    }

    public Double getDefaultFulfillmentGroupTaxRate() {
        return defaultFulfillmentGroupTaxRate;
    }

    public void setDefaultFulfillmentGroupTaxRate(Double defaultFulfillmentGroupTaxRate) {
        this.defaultFulfillmentGroupTaxRate = defaultFulfillmentGroupTaxRate;
    }

}
