
package com.wakacommerce.openadmin.server.service.artifact.image;

/**
 * A bean designed to hold general information about an image
 * 
 *jfischer
 *
 */
public class ImageMetadata {
    
    private int width;
    private int height;
    
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
}
