
package com.wakacommerce.openadmin.server.service.export;

import javax.servlet.ServletOutputStream;

import com.wakacommerce.openadmin.dto.Property;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface AdminExporter {

    public String getName();

    public String getFriendlyName();

    public List<Property> getCriteriaFields();

    public String getType();

    public String getFileName();

    public void writeExport(ServletOutputStream out, Map<String, String> params) throws IOException;
    
}
