
package com.wakacommerce.common.web.expression;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wakacommerce.common.crossapp.service.CrossAppAuthService;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.common.web.BroadleafRequestContext;

import java.util.Date;


/**
 * Exposes the {@link BroadleafRequestContext} to the Thymeleaf expression context
 * 
 *Andre Azzolini (apazzolini)
 */
public class BRCVariableExpression implements BroadleafVariableExpression {
    
    @Autowired(required = false)
    @Qualifier("blCrossAppAuthService")
    protected CrossAppAuthService crossAppAuthService;

    @Override
    public String getName() {
        return "brc";
    }
    
    public SandBox getSandbox() {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            return brc.getSandBox();
        }
        return null;
    }

    public Site getSite() {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            return brc.getNonPersistentSite();
        }
        return null;
    }

    public Site getCurrentProfile() {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            return brc.getCurrentProfile();
        }
        return null;
    }

    public Catalog getCurrentCatalog() {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            return brc.getCurrentCatalog();
        }
        return null;
    }
    
    public Date getCurrentTime() {
        return SystemTime.asDate(true);
    }
    
    public Object get(String propertyName) {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            try {
                return PropertyUtils.getProperty(brc, propertyName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean isCsrMode() {
        return crossAppAuthService == null ? false : crossAppAuthService.hasCsrPermission();
    }
    
    public boolean isSandboxMode() {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        return (brc == null) ? false : (brc.getSandBox() != null);
    }
    
    public Object getAdditionalProperty(String propertyName) {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            return brc.getAdditionalProperties().get(propertyName);
        }
        return null;
    }

}
