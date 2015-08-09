
package com.wakacommerce.common.i18n.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.i18n.dao.ISODao;
import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.common.util.TransactionUtils;

import java.util.List;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blISOService")
public class ISOServiceImpl implements ISOService {

    protected static final Log LOG = LogFactory.getLog(ISOServiceImpl.class);

    @Resource(name="blISODao")
    protected ISODao isoDao;

    @Override
    public List<ISOCountry> findISOCountries() {
        return isoDao.findISOCountries();
    }

    @Override
    public ISOCountry findISOCountryByAlpha2Code(String alpha2) {
        return isoDao.findISOCountryByAlpha2Code(alpha2);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public ISOCountry save(ISOCountry isoCountry) {
        return isoDao.save(isoCountry);
    }

}
