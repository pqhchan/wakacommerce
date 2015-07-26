
package com.wakacommerce.core.util.service;

import java.util.List;

import com.wakacommerce.core.util.domain.CodeType;

@Deprecated
public interface CodeTypeService {

    public List<CodeType> findAllCodeTypes();

    public CodeType save(CodeType codeType);

    public List<CodeType> lookupCodeTypeByKey(String key);

    public void deleteCodeType(CodeType codeTypeId);

    public CodeType lookupCodeTypeById(Long codeTypeId);
}
