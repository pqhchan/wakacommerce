
package com.wakacommerce.core.util.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.core.util.dao.CodeTypeDao;
import com.wakacommerce.core.util.domain.CodeType;

import java.util.List;

import javax.annotation.Resource;

@Service("blCodeTypeService")
@Deprecated
public class CodeTypeServiceImpl implements CodeTypeService {

    @Resource(name="blCodeTypeDao")
    protected CodeTypeDao codeTypeDao;

    @Override
    @Transactional("blTransactionManager")
    public void deleteCodeType(CodeType codeTypeId) {
        codeTypeDao.delete(codeTypeId);
    }

    @Override
    public List<CodeType> findAllCodeTypes() {
        return codeTypeDao.readAllCodeTypes();
    }

    @Override
    public CodeType lookupCodeTypeById(Long codeTypeId) {
        return codeTypeDao.readCodeTypeById(codeTypeId);
    }

    @Override
    public List<CodeType> lookupCodeTypeByKey(String key) {
        return codeTypeDao.readCodeTypeByKey(key);
    }

    @Override
    @Transactional("blTransactionManager")
    public CodeType save(CodeType codeType) {
        return codeTypeDao.save(codeType);
    }

}
