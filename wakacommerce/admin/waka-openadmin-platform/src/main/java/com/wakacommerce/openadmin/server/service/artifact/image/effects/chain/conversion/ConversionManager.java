
package com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion;

import org.springframework.stereotype.Service;

import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.ConverterNotFoundException;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.impl.BooleanParameterConverter;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.impl.DoubleParameterConverter;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.impl.FloatParameterConverter;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.impl.IntParameterConverter;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.impl.RectangleParameterConverter;

import java.util.HashMap;
import java.util.Map;

@Service("blImageConversionManager")
public class ConversionManager {
    
    protected Map<String, ParameterConverter> converters = new HashMap<String, ParameterConverter>();

    public ConversionManager() {
        converters.put(ParameterTypeEnum.BOOLEAN.toString(), new BooleanParameterConverter());
        converters.put(ParameterTypeEnum.DOUBLE.toString(), new DoubleParameterConverter());
        converters.put(ParameterTypeEnum.FLOAT.toString(), new FloatParameterConverter());
        converters.put(ParameterTypeEnum.INT.toString(), new IntParameterConverter());
        converters.put(ParameterTypeEnum.RECTANGLE.toString(), new RectangleParameterConverter());
    }

    public Parameter convertParameter(String value, String type, Double factor, boolean applyFactor) throws ConverterNotFoundException, ConversionException {
        ParameterConverter converter = converters.get(type);
        if (converter == null) throw new ConverterNotFoundException("Could not find a parameter converter with the type name: " + type);
        return converter.convert(value, factor, applyFactor);
    }
    
    /**
     * @return the converters
     */
    public Map<String, ParameterConverter> getConverters() {
        return converters;
    }

    /**
     * @param converters the converters to set
     */
    public void setConverters(Map<String, ParameterConverter> converters) {
        this.converters = converters;
    }

}
