package com.wakacommerce.common.web.dialect;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractModelVariableModifierProcessor extends AbstractElementProcessor {
    
    public AbstractModelVariableModifierProcessor(String elementName) {
        super(elementName);
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    protected ProcessorResult processElement(final Arguments arguments, final Element element) {
        modifyModelAttributes(arguments, element);
        
        // Remove the tag from the DOM
        final NestableNode parent = element.getParent();
        parent.removeChild(element);
        
        return ProcessorResult.OK;
    }
    
    @SuppressWarnings("unchecked")
    protected void addToModel(Arguments arguments, String key, Object value) {
        ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    protected <T> void addCollectionToExistingSet(Arguments arguments, String key, Collection<T> value) {
        Set<T> items = (Set<T>) ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).get(key);
        if (items == null) {
            items = new HashSet<T>();
            ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, items);
        }
        items.addAll(value);
    }

    @SuppressWarnings("unchecked")
    protected <T> void addItemToExistingSet(Arguments arguments, String key, Object value) {
        Set<T> items = (Set<T>) ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).get(key);
        if (items == null) {
            items = new HashSet<T>();                         
            ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, items);
        }
        items.add((T) value);
    }
    
    protected abstract void modifyModelAttributes(Arguments arguments, Element element);
    
}
