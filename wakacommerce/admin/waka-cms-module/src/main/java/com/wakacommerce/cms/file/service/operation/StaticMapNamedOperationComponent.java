package com.wakacommerce.cms.file.service.operation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StaticMapNamedOperationComponent implements NamedOperationComponent {

    @Override
    public List<String> setOperationValues(Map<String, String> originalParameters, Map<String, String> derivedParameters) {
        List<String> utilizedNames = new ArrayList<String>();
        expandFulfilledMap(originalParameters, derivedParameters, utilizedNames);

        return utilizedNames;
    }

    protected void expandFulfilledMap(Map<String, String> originalParameters, Map<String, String> derivedParameters, List<String> utilizedNames) {
        for (Map.Entry<String, String> entry : originalParameters.entrySet()) {
            if (namedOperations.containsKey(entry.getKey())) {
                expandFulfilledMap(namedOperations.get(entry.getKey()), derivedParameters, utilizedNames);
                if (!utilizedNames.contains(entry.getKey())) {
                    utilizedNames.add(entry.getKey());
                }
            } else {
                derivedParameters.put(entry.getKey(), entry.getValue());
            }
        }
    }

    protected LinkedHashMap<String, LinkedHashMap<String, String>> namedOperations = new LinkedHashMap<String, LinkedHashMap<String, String>>();

    public LinkedHashMap<String, LinkedHashMap<String, String>> getNamedOperations() {
        return namedOperations;
    }

    public void setNamedOperations(LinkedHashMap<String, LinkedHashMap<String, String>> namedOperations) {
        this.namedOperations = namedOperations;
    }
}
