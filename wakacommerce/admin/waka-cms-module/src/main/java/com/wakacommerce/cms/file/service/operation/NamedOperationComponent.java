package com.wakacommerce.cms.file.service.operation;

import java.util.List;
import java.util.Map;

public interface NamedOperationComponent {

    List<String> setOperationValues(Map<String, String> originalParameters, Map<String, String> derivedParameters);

}
