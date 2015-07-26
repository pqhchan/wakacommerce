
package com.wakacommerce.core.store.dao;

import com.wakacommerce.core.store.domain.ZipCode;

public interface ZipCodeDao {

    public ZipCode findZipCodeByZipCode(Integer zipCode);

    public ZipCode findBestZipCode(String pCity, String pCounty, String pState, Integer pZipCode, Long pZipGeo);

}
