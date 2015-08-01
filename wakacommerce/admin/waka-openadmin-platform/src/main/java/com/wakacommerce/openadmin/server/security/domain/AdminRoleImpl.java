package com.wakacommerce.openadmin.server.security.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.AdminPresentationCollection;
import com.wakacommerce.common.presentation.AdminPresentationOperationTypes;
import com.wakacommerce.common.presentation.client.AddMethodType;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(friendlyName = "管理员角色")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_ADMINROLE)
})
public class AdminRoleImpl implements AdminRole, AdminMainEntity {

    private static final Log LOG = LogFactory.getLog(AdminRoleImpl.class);
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "AdminRoleId")
    @GenericGenerator(
        name="AdminRoleId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="AdminRoleImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.openadmin.server.security.domain.AdminRoleImpl")
        }
    )
    @Column(name = "ADMIN_ROLE_ID")
    @AdminPresentation(friendlyName = "角色ID", group = "主键", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", nullable=false)
    @AdminPresentation(friendlyName = "角色代码", order = 1, group = "角色")
    protected String name;

    @Column(name = "DESCRIPTION", nullable=false)
    @AdminPresentation(friendlyName = "角色名称", order=2, group = "角色", prominent=true)
    protected String description;

    /** All users that have this role */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminUserImpl.class)
    @JoinTable(name = "BLC_ADMIN_USER_ROLE_XREF", joinColumns = @JoinColumn(name = "ADMIN_ROLE_ID", referencedColumnName = "ADMIN_ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "ADMIN_USER_ID", referencedColumnName = "ADMIN_USER_ID"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    protected Set<AdminUser> allUsers = new HashSet<AdminUser>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminPermissionImpl.class)
    @JoinTable(name = "BLC_ADMIN_ROLE_PERMISSION_XREF", joinColumns = @JoinColumn(name = "ADMIN_ROLE_ID", referencedColumnName = "ADMIN_ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "ADMIN_PERMISSION_ID", referencedColumnName = "ADMIN_PERMISSION_ID"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    @AdminPresentationCollection(addType = AddMethodType.LOOKUP,
            friendlyName = "permissionListTitle",
            manyToField = "allRoles",
            customCriteria = "includeFriendlyOnly",
            operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<AdminPermission> allPermissions= new HashSet<AdminPermission>();


    @Override
    public Set<AdminPermission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<AdminUser> getAllUsers() {
        return allUsers;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public void setAllPermissions(Set<AdminPermission> allPermissions) {
        this.allPermissions = allPermissions;
    }

    public void checkCloneable(AdminRole adminRole) throws CloneNotSupportedException, SecurityException, NoSuchMethodException {
        Method cloneMethod = adminRole.getClass().getMethod("clone", new Class[]{});
        if (cloneMethod.getDeclaringClass().getName().startsWith("com.wakacommerce") && !adminRole.getClass().getName().startsWith("com.wakacommerce")) {
            //subclass is not implementing the clone method
            throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
        }
    }

    @Override
    public AdminRole clone() {
        AdminRole clone;
        try {
            clone = (AdminRole) Class.forName(this.getClass().getName()).newInstance();
            try {
                checkCloneable(clone);
            } catch (CloneNotSupportedException e) {
                LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: " + clone.getClass().getName(), e);
            }
            clone.setId(id);
            clone.setName(name);
            clone.setDescription(description);

            //don't clone the allUsers collection, as it would cause a recursion

            if (allPermissions != null) {
                for (AdminPermission permission : allPermissions) {
                    AdminPermission permissionClone = permission.clone();
                    clone.getAllPermissions().add(permissionClone);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clone;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }

}
