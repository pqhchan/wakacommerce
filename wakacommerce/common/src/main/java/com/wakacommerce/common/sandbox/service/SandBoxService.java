
package com.wakacommerce.common.sandbox.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.sandbox.domain.SandBoxType;

public interface SandBoxService {

    public SandBox retrieveSandBoxById(Long id);
    
    public List<SandBox> retrieveAllSandBoxes();

    /**
     * Returns the sandbox currently associated with the passed in userId.
     * If one is not associated, it uses (or creates) a default user sandbox with the
     * name:   user:username.
     *
     * @param adminUser
     * @return
     */
    public SandBox retrieveUserSandBox(Long authorId, Long overrideSandBoxId, String sandBoxName);
    
    public SandBox retrieveUserSandBoxForParent(Long authorId, Long parentSandBoxId);
    
    /**
     * Returns the SandBox by id but only if the SandBox is associated with the current site.
     * @param sandBoxId
     * @return
     */
    public SandBox retrieveSandBoxManagementById(Long sandBoxId);

    public List<SandBox> retrievePreviewSandBoxes(Long authorId);
    
    public List<SandBox> retrieveSandBoxesByType(SandBoxType type);
    
    public Map<Long, String> retrieveAuthorNamesForSandBoxes(Set<Long> sandBoxIds);

    public SandBox createSandBox(String sandBoxName, SandBoxType sandBoxType) throws Exception;

    public SandBox createUserSandBox(Long authorId, SandBox approvalSandBox);
    
    public SandBox retrieveSandBox(String sandBoxName, SandBoxType sandBoxType);

    public SandBox createDefaultSandBox();

    /**
     * Returns true if an existing sandboxName exists with the passed in name.  
     * @param sandboxName
     * @return
     */
    boolean checkForExistingApprovalSandboxWithName(String sandboxName);

    /**
     * @deprecated Not used in BLC.   May return incorrect results in MT installations.
     * Reads all SandBoxes that are of type {@link SandBoxType.USER} and belong to the given
     * user.
     * 
     * @param authorId
     * @return a list of SandBox belonging to the user
     */
    public List<SandBox> retrieveAllUserSandBoxes(Long authorId);

    public void archiveChildSandboxes(Long parentSandBoxId);

    public List<SandBox> retrieveChildSandBoxesByParentId(Long parentSandBoxId);
}
