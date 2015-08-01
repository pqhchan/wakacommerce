package com.wakacommerce.cms.file.service.operation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NamedOperationManagerImpl implements NamedOperationManager {

    protected List<NamedOperationComponent> namedOperationComponents = new ArrayList<NamedOperationComponent>();

    @Override
    public Map<String, String> manageNamedParameters(Map<String, String> parameterMap) {
        List<String> utilizedNames = new ArrayList<String>();
        Map<String, String> derivedMap = new LinkedHashMap<String, String>();
        for (NamedOperationComponent namedOperationComponent : namedOperationComponents) {
            utilizedNames.addAll(namedOperationComponent.setOperationValues(parameterMap, derivedMap));
        }
        for (String utilizedName : utilizedNames) {
            parameterMap.remove(utilizedName);
        }
        derivedMap.putAll(parameterMap);

        return derivedMap;
    }

    @Override
    public List<NamedOperationComponent> getNamedOperationComponents() {
        return namedOperationComponents;
    }

    public void setNamedOperationComponents(List<NamedOperationComponent> namedOperationComponents) {
        this.namedOperationComponents = namedOperationComponents;
    }
}
