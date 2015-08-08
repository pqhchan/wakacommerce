package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.profile.core.dao.RoleDao;
import com.wakacommerce.profile.core.domain.CustomerRole;

import java.util.List;

import javax.annotation.Resource;

@Service("blRoleService")
public class RoleServiceImpl implements RoleService {

    @Resource(name="blRoleDao")
    protected RoleDao roleDao;

    @Override
    @Transactional("blTransactionManager")
    public List<CustomerRole> findCustomerRolesByCustomerId(Long customerId) {
        return roleDao.readCustomerRolesByCustomerId(customerId);
    }
}
