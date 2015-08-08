package com.wakacommerce.openadmin.web.rulebuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.DataDTO;
import com.wakacommerce.openadmin.web.rulebuilder.dto.ExpressionDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DataDTODeserializer extends StdDeserializer<DataDTO> {

    public DataDTODeserializer() {
        super(DataDTO.class);
    }

    @Override
    public DataDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        Iterator<Map.Entry<String, JsonNode>> elementsIterator =
                root.fields();
        DataDTO dataDTO = new DataDTO();
        ExpressionDTO expressionDTO = new ExpressionDTO();
        boolean isExpression = false;
        while (elementsIterator.hasNext()) {
            Map.Entry<String, JsonNode> element=elementsIterator.next();
            String name = element.getKey();
            if ("name".equals(name)) {
                expressionDTO.setName(getNullAwareText(element.getValue()));
                isExpression = true;
            }
            if ("operator".equals(name)) {
                expressionDTO.setOperator(getNullAwareText(element.getValue()));
                isExpression = true;
            }
            if ("value".equals(name)) {
                expressionDTO.setValue(getNullAwareText(element.getValue()));
                isExpression = true;
            }
            if ("start".equals(name)) {
                expressionDTO.setStart(getNullAwareText(element.getValue()));
                isExpression = true;
            }
            if ("end".equals(name)) {
                expressionDTO.setEnd(getNullAwareText(element.getValue()));
                isExpression = true;
            }
            if ("id".equals(name)) {
                if (getNullAwareText(element.getValue()) == null) {
                    dataDTO.setId(null);
                } else {
                    dataDTO.setId(element.getValue().asLong());
                }
            }
            if ("quantity".equals(name)) {
                if (getNullAwareText(element.getValue()) == null) {
                    dataDTO.setQuantity(null);
                } else {
                    dataDTO.setQuantity(element.getValue().asInt());
                }
            }
            if ("groupOperator".equals(name)) {
                dataDTO.setGroupOperator(getNullAwareText(element.getValue()));
            }
            if ("groups".equals(name)){
                CollectionType dtoCollectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, DataDTO.class);
                dataDTO.setGroups((ArrayList<DataDTO>)mapper.readValue(element.getValue().traverse(jp.getCodec()), dtoCollectionType));
            }
        }

        if (isExpression) {
            return expressionDTO;
        } else {
            return dataDTO;
        }
    }
    
    /**
     * Handles the string "null" when using asText() in a JsonNode and returns the literal null instead
     */
    protected String getNullAwareText(JsonNode node) {
        return "null".equals(node.asText()) ? null : node.asText();
    }

}
