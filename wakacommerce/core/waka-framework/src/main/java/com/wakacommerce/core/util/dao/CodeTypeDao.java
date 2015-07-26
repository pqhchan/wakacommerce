
package com.wakacommerce.core.util.dao;

import java.util.List;

import com.wakacommerce.core.util.domain.CodeType;

public interface CodeTypeDao {

    public List<CodeType> readAllCodeTypes();

    public CodeType readCodeTypeById(Long codeTypeId);

    public List<CodeType> readCodeTypeByKey(String key);

    public CodeType save(CodeType codeType);

    public void delete(CodeType codeType);

    public CodeType create();
}
