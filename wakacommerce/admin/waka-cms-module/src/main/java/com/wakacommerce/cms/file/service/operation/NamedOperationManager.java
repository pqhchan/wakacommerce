package com.wakacommerce.cms.file.service.operation;

import java.util.List;
import java.util.Map;

public interface NamedOperationManager {

    Map<String, String> manageNamedParameters(Map<String, String> parameterMap);

    List<NamedOperationComponent> getNamedOperationComponents();
}
