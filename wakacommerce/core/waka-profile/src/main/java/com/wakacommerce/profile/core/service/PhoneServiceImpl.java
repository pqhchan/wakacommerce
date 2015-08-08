package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.util.TransactionUtils;
import com.wakacommerce.profile.core.dao.PhoneDao;
import com.wakacommerce.profile.core.domain.Phone;

import javax.annotation.Resource;

@Service("blPhoneService")
public class PhoneServiceImpl implements PhoneService {

    @Resource(name="blPhoneDao")
    protected PhoneDao phoneDao;

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public Phone savePhone(Phone phone) {
        return phoneDao.save(phone);
    }

    @Override
    public Phone readPhoneById(Long phoneId) {
        return phoneDao.readPhoneById(phoneId);
    }

    @Override
    public Phone create() {
        return phoneDao.create();
    }
}
