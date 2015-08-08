package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.profile.core.dao.CountrySubdivisionDao;
import com.wakacommerce.profile.core.domain.CountrySubdivision;

import java.util.List;
import javax.annotation.Resource;

@Service("blCountrySubdivisionService")
public class CountrySubdivisionServiceImpl implements CountrySubdivisionService {

    @Resource(name="blCountrySubdivisionDao")
    protected CountrySubdivisionDao countrySubdivisionDao;

    @Override
    public List<CountrySubdivision> findSubdivisions() {
        return countrySubdivisionDao.findSubdivisions();
    }

    @Override
    public List<CountrySubdivision> findSubdivisions(String countryAbbreviation) {
        return countrySubdivisionDao.findSubdivisions(countryAbbreviation);
    }

    @Override
    public List<CountrySubdivision> findSubdivisionsByCountryAndCategory(String countryAbbreviation, String category) {
        return countrySubdivisionDao.findSubdivisionsByCountryAndCategory(countryAbbreviation, category);
    }

    @Override
    public CountrySubdivision findSubdivisionByAbbreviation(String abbreviation) {
        return countrySubdivisionDao.findSubdivisionByAbbreviation(abbreviation);
    }

    @Override
    @Transactional("blTransactionManager")
    public CountrySubdivision save(CountrySubdivision subdivision) {
        return countrySubdivisionDao.save(subdivision);
    }
}
