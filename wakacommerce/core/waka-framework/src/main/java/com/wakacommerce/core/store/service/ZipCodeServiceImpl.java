
package com.wakacommerce.core.store.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.core.store.dao.ZipCodeDao;
import com.wakacommerce.core.store.domain.ZipCode;

import javax.annotation.Resource;

@Service("blZipCodeService")
public class ZipCodeServiceImpl implements ZipCodeService {

    @Resource(name="blZipCodeDao")
    private ZipCodeDao zipCodeDao;

    public ZipCode findZipCodeByZipCode(Integer zipCode) {
        return zipCodeDao.findZipCodeByZipCode(zipCode);
    }
}
