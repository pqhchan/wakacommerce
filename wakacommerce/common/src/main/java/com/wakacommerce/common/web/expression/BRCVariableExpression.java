package com.wakacommerce.common.web.expression;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wakacommerce.common.crossapp.service.CrossAppAuthService;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.common.web.WakaRequestContext;

import java.util.Date;


/**
 *
 * @ hui
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
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            return brc.getSandBox();
        }
        return null;
    }

    public Site getSite() {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            return brc.getNonPersistentSite();
        }
        return null;
    }

    public Site getCurrentProfile() {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            return brc.getCurrentProfile();
        }
        return null;
    }

    public Catalog getCurrentCatalog() {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            return brc.getCurrentCatalog();
        }
        return null;
    }
    
    public Date getCurrentTime() {
        return SystemTime.asDate(true);
    }
    
    public Object get(String propertyName) {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
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
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        return (brc == null) ? false : (brc.getSandBox() != null);
    }
    
    public Object getAdditionalProperty(String propertyName) {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            return brc.getAdditionalProperties().get(propertyName);
        }
        return null;
    }

}
