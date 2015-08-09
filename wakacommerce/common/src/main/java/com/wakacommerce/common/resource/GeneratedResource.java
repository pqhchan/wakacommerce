
package com.wakacommerce.common.resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.AbstractResource;
import org.springframework.security.util.InMemoryResource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @ hui
 */
public class GeneratedResource extends AbstractResource implements Serializable {
    
    private static final long serialVersionUID = 7044543270746433688L;

    protected long timeGenerated;
    protected String hashRepresentation;
    
    protected final byte[] source;
    protected final String description;

    public GeneratedResource()  {
        this(new byte[]{}, null);
    }

    public GeneratedResource(byte[] source, String description) {
        Assert.notNull(source);
        this.source = source;
        this.description = description;
        timeGenerated = System.currentTimeMillis();
    }
    
    @Override
    public String getFilename() {
        return getDescription();
    }
    
    @Override
	public long lastModified() throws IOException {
        return timeGenerated;
    }
    
    public String getHashRepresentation() {
        return StringUtils.isBlank(hashRepresentation) ? String.valueOf(timeGenerated) : hashRepresentation;
    }

    public void setHashRepresentation(String hashRepresentation) {
        this.hashRepresentation = hashRepresentation;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(source);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public byte[] getBytes() {
        return source;
    }

    @Override
    public boolean equals(Object res) {
        if (res == null) return false;
        if (!getClass().isAssignableFrom(res.getClass())) {
            return false;
        }

        return Arrays.equals(source, ((GeneratedResource)res).source);
    }

}
