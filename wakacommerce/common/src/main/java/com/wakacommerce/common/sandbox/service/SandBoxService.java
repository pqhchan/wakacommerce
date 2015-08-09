
package com.wakacommerce.common.sandbox.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.sandbox.domain.SandBoxType;

public interface SandBoxService {

    public SandBox retrieveSandBoxById(Long id);
    
    public List<SandBox> retrieveAllSandBoxes();

    public SandBox retrieveUserSandBox(Long authorId, Long overrideSandBoxId, String sandBoxName);
    
    public SandBox retrieveUserSandBoxForParent(Long authorId, Long parentSandBoxId);

    public SandBox retrieveSandBoxManagementById(Long sandBoxId);

    public List<SandBox> retrievePreviewSandBoxes(Long authorId);
    
    public List<SandBox> retrieveSandBoxesByType(SandBoxType type);
    
    public Map<Long, String> retrieveAuthorNamesForSandBoxes(Set<Long> sandBoxIds);

    public SandBox createSandBox(String sandBoxName, SandBoxType sandBoxType) throws Exception;

    public SandBox createUserSandBox(Long authorId, SandBox approvalSandBox);
    
    public SandBox retrieveSandBox(String sandBoxName, SandBoxType sandBoxType);

    public SandBox createDefaultSandBox();

    boolean checkForExistingApprovalSandboxWithName(String sandboxName);

    public List<SandBox> retrieveAllUserSandBoxes(Long authorId);

    public void archiveChildSandboxes(Long parentSandBoxId);

    public List<SandBox> retrieveChildSandBoxesByParentId(Long parentSandBoxId);
}
