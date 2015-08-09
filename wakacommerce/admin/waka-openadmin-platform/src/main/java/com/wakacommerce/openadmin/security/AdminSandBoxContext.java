
package com.wakacommerce.openadmin.security;

import com.wakacommerce.common.web.SandBoxContext;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.service.SandBoxMode;

/**
 *
 * @ hui
 */
public class AdminSandBoxContext extends SandBoxContext {

    protected AdminUser adminUser;
    protected SandBoxMode sandBoxMode;
    protected String sandBoxName;
    protected boolean resetData = false;
    protected boolean isReplay = false;
    protected boolean rebuildSandBox = false;

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public SandBoxMode getSandBoxMode() {
        return sandBoxMode;
    }

    public void setSandBoxMode(SandBoxMode sandBoxMode) {
        this.sandBoxMode = sandBoxMode;
    }

    public String getSandBoxName() {
        return sandBoxName;
    }

    public void setSandBoxName(String sandBoxName) {
        this.sandBoxName = sandBoxName;
    }

    public boolean isReplay() {
        return isReplay;
    }

    public void setReplay(boolean replay) {
        isReplay = replay;
    }

    public boolean isRebuildSandBox() {
        return rebuildSandBox;
    }

    public void setRebuildSandBox(boolean rebuildSandBox) {
        this.rebuildSandBox = rebuildSandBox;
    }

    public boolean isResetData() {
        return resetData;
    }

    public void setResetData(boolean resetData) {
        this.resetData = resetData;
    }

    public SandBoxContext clone() {
        AdminSandBoxContext myContext = new AdminSandBoxContext();
        myContext.setResetData(isResetData());
        myContext.setAdminUser(getAdminUser());
        myContext.setSandBoxId(getSandBoxId());
        myContext.setPreviewMode(getPreviewMode());
        myContext.setSandBoxMode(getSandBoxMode());
        myContext.setSandBoxName(getSandBoxName());
        myContext.setReplay(isReplay());
        myContext.setRebuildSandBox(isRebuildSandBox());


        return myContext;
    }
}
