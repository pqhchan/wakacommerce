
package com.wakacommerce.openadmin.server.service;

import org.springframework.security.access.annotation.Secured;

import com.wakacommerce.openadmin.dto.AdminExporterDTO;

import java.util.List;

/**
 * 
 *Phillip Verheyden
 */
public interface AdminExporterService {

    @Secured("PERMISSION_OTHER_DEFAULT")
    public List<AdminExporterDTO> getExporters(String type);

}
