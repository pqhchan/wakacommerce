
package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.profile.core.dao.CountryDao;
import com.wakacommerce.profile.core.domain.Country;

import javax.annotation.Resource;
import java.util.List;

@Service("blCountryService")
public class CountryServiceImpl implements CountryService {

    @Resource(name="blCountryDao")
    protected CountryDao countryDao;

    public List<Country> findCountries() {
        return countryDao.findCountries();
    }

    public Country findCountryByAbbreviation(String abbreviation) {
        return countryDao.findCountryByAbbreviation(abbreviation);
    }

    @Transactional("blTransactionManager")
    public Country save(Country country) {
        return countryDao.save(country);
    }
}
