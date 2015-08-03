
package com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.impl;

import java.awt.*;
import java.util.StringTokenizer;

import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.ConversionException;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.Parameter;
import com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion.ParameterConverter;

public class RectangleParameterConverter implements ParameterConverter {

    public Parameter convert(String value, Double factor, boolean applyFactor) throws ConversionException {
        StringTokenizer tokens = new StringTokenizer(value, ",");
        Rectangle rect = new Rectangle();
        rect.x = (int) (applyFactor&&factor!=null?Integer.parseInt(tokens.nextToken())/factor:Integer.parseInt(tokens.nextToken()));
        rect.y = (int) (applyFactor&&factor!=null?Integer.parseInt(tokens.nextToken())/factor:Integer.parseInt(tokens.nextToken()));
        rect.width = (int) (applyFactor&&factor!=null?Integer.parseInt(tokens.nextToken())/factor:Integer.parseInt(tokens.nextToken()));
        rect.height = (int) (applyFactor&&factor!=null?Integer.parseInt(tokens.nextToken())/factor:Integer.parseInt(tokens.nextToken()));
        
        Parameter param = new Parameter();
        param.setParameterClass(Rectangle.class);
        param.setParameterInstance(rect);
        
        return param;
    }

}
