package com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.filter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.artifact.OperationBuilder;

public abstract class BaseFilter implements BufferedImageOp, OperationBuilder {
    
    protected RenderingHints hints;

    public BufferedImage createCompatibleDestImage(BufferedImage src,
            ColorModel destCM) {
        BufferedImage image;
        if (destCM == null) {
            destCM = src.getColorModel();
            // Not much support for ICM
            if (destCM instanceof IndexColorModel) {
                destCM = ColorModel.getRGBdefault();
            }
        }

        int w = src.getWidth();
        int h = src.getHeight();
        image = new BufferedImage (destCM,
                                   destCM.createCompatibleWritableRaster(w, h),
                                   destCM.isAlphaPremultiplied(), null);

        return image;
    }
    
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM, int width, int height) {
        BufferedImage image;
        if (destCM == null) {
            destCM = src.getColorModel();
            // Not much support for ICM
            if (destCM instanceof IndexColorModel) {
                destCM = ColorModel.getRGBdefault();
            }
        }

        image = new BufferedImage (destCM,
                                   destCM.createCompatibleWritableRaster(width, height),
                                   destCM.isAlphaPremultiplied(), null);

        return image;
    }

    public Rectangle2D getBounds2D(BufferedImage src) {
        return src.getRaster().getBounds();
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Float();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());

        return dstPt;
    }

    public RenderingHints getRenderingHints() {
        return hints;
    }

    protected boolean containsMyFilterParams(String key, Map<String, String> parameterMap) {
        for (String paramKey : parameterMap.keySet()) {
            if (paramKey.startsWith(key)) {
                return true;
            }
        }
        return false;
    }
}
