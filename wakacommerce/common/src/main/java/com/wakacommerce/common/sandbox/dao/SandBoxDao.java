
package com.wakacommerce.common.sandbox.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.sandbox.domain.SandBoxType;

public interface SandBoxDao {

    public SandBox retrieve(Long id);
    
    public List<SandBox> retrieveAllSandBoxes();

    public List<SandBox> retrieveSandBoxesByType(SandBoxType sandboxType);

    public List<SandBox> retrieveSandBoxesForAuthor(Long authorId);

    public SandBox retrieveUserSandBoxForParent(Long authorId, Long parentSandBoxId);

    public SandBox retrieveSandBoxManagementById(Long sandBoxId);

    public SandBox retrieveNamedSandBox(SandBoxType sandboxType, String sandboxName);

    public Map<Long, String> retrieveAuthorNamesForSandBoxes(Set<Long> sandBoxIds);

    public SandBox persist(SandBox entity);

    public SandBox createSandBox(String sandBoxName, SandBoxType sandBoxType);

    public SandBox createUserSandBox(Long authorId, SandBox approvalSandBox);

    public SandBox createDefaultSandBox();

    @Deprecated
    List<SandBox> retrieveAllUserSandBoxes(Long authorId);

    SandBox merge(SandBox userSandBox);

    List<SandBox> retrieveChildSandBoxesByParentId(Long parentSandBoxId);
}
