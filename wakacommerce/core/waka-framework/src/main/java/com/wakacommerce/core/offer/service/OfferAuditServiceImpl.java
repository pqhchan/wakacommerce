
package com.wakacommerce.core.offer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.util.TransactionUtils;
import com.wakacommerce.core.offer.dao.OfferAuditDao;
import com.wakacommerce.core.offer.domain.OfferAudit;

import javax.annotation.Resource;


/**
 *
 * @ hui
 */
@Service("blOfferAuditService")
public class OfferAuditServiceImpl implements OfferAuditService {
    
    @Resource(name = "blOfferAuditDao")
    protected OfferAuditDao offerAuditDao;
    
    @Override
    public OfferAudit readAuditById(Long offerAuditId) {
        return offerAuditDao.readAuditById(offerAuditId);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public OfferAudit save(OfferAudit offerAudit) {
        return offerAuditDao.save(offerAudit);
    }
    
    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void delete(OfferAudit offerAudit) {
        offerAuditDao.delete(offerAudit);
    }

    @Override
    public OfferAudit create() {
        return offerAuditDao.create();
    }
    
    @Override
    public Long countUsesByCustomer(Long customerId, Long offerId) {
        return offerAuditDao.countUsesByCustomer(customerId, offerId);
    }

    @Override
    public Long countOfferCodeUses(Long offerCodeId) {
        return offerAuditDao.countOfferCodeUses(offerCodeId);
    }


}
