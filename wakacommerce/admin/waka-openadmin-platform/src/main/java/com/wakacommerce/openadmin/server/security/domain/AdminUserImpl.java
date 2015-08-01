package com.wakacommerce.openadmin.server.security.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.AdminPresentationCollection;
import com.wakacommerce.common.presentation.AdminPresentationOperationTypes;
import com.wakacommerce.common.presentation.ConfigurationItem;
import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.common.presentation.client.AddMethodType;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.sandbox.domain.SandBoxImpl;
import com.wakacommerce.openadmin.server.service.type.ContextType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(friendlyName = "管理员账户")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_ADMINUSER)
})
public class AdminUserImpl implements AdminUser, AdminMainEntity {

    private static final long serialVersionUID = 1L;
    protected static final String LAST_USED_SANDBOX = "LAST_USED_SANDBOX";

    @Id
    @GeneratedValue(generator = "AdminUserId")
    @GenericGenerator(
        name="AdminUserId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="AdminUserImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.openadmin.server.security.domain.AdminUserImpl")
        }
    )
    @Column(name = "ADMIN_USER_ID")
    @AdminPresentation(friendlyName = "账户ID", group = "主键", visibility = VisibilityEnum.HIDDEN_ALL)
    private Long id;

    @Column(name = "NAME", nullable=false)
    @Index(name="ADMINUSER_NAME_INDEX", columnNames={"NAME"})
    @AdminPresentation(friendlyName = "姓名", gridOrder = 1000, order = 1000,
            group = "一般信息", prominent = true)
    protected String name;

    @Column(name = "LOGIN", nullable=false)
    @AdminPresentation(friendlyName = "登录名", gridOrder = 2000, order = 2000,
            group = "一般信息", prominent = true)
    protected String login;

    @Column(name = "PASSWORD")
    @AdminPresentation(
            friendlyName = "密码", order = 6000,
            group = "一般信息",
            fieldType = SupportedFieldType.PASSWORD,
            validationConfigurations = { @ValidationConfiguration(
                    validationImplementation = "com.wakacommerce.openadmin.server.service.persistence.validation.MatchesFieldValidator",
                    configurationItems = {
                            @ConfigurationItem(itemName = ConfigurationItem.ERROR_MESSAGE, itemValue = "passwordNotMatchError"),
                            @ConfigurationItem(itemName = "otherField", itemValue = "passwordConfirm")
                    }
                    )
            })
    protected String password;

    @Column(name = "EMAIL", nullable=false)
    @Index(name="ADMINPERM_EMAIL_INDEX", columnNames={"EMAIL"})
    @AdminPresentation(friendlyName = "邮箱", order = 3000, group = "一般信息")
    protected String email;

    @Column(name = "PHONE_NUMBER")
    @AdminPresentation(friendlyName = "手机号", order = 5000, group = "一般信息")
    protected String phoneNumber;

    @Column(name = "ACTIVE_STATUS_FLAG")
    @AdminPresentation(friendlyName = "激活状态",
            order = 4000, group = "一般信息",
            defaultValue = "true")
    protected Boolean activeStatusFlag = Boolean.TRUE;

    /** All roles that this user has */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminRoleImpl.class)
    @JoinTable(name = "BLC_ADMIN_USER_ROLE_XREF", joinColumns = @JoinColumn(name = "ADMIN_USER_ID", referencedColumnName = "ADMIN_USER_ID"), inverseJoinColumns = @JoinColumn(name = "ADMIN_ROLE_ID", referencedColumnName = "ADMIN_ROLE_ID"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    @AdminPresentationCollection(addType = AddMethodType.LOOKUP, friendlyName = "关联角色", manyToField = "allUsers",
                operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<AdminRole> allRoles = new HashSet<AdminRole>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminPermissionImpl.class)
    @JoinTable(name = "BLC_ADMIN_USER_PERMISSION_XREF", joinColumns = @JoinColumn(name = "ADMIN_USER_ID", referencedColumnName = "ADMIN_USER_ID"), inverseJoinColumns = @JoinColumn(name = "ADMIN_PERMISSION_ID", referencedColumnName = "ADMIN_PERMISSION_ID"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    @AdminPresentationCollection(addType = AddMethodType.LOOKUP,
            friendlyName = "关联权限",
            customCriteria = "includeFriendlyOnly",
            manyToField = "allUsers",
            operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<AdminPermission> allPermissions = new HashSet<AdminPermission>();

    @Transient
    protected String unencodedPassword;
    
    @Override
    public String getUnencodedPassword() {
        return unencodedPassword;
    }

    @ManyToOne(targetEntity = SandBoxImpl.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "BLC_ADMIN_USER_SANDBOX", joinColumns = @JoinColumn(name = "ADMIN_USER_ID", referencedColumnName = "ADMIN_USER_ID"), inverseJoinColumns = @JoinColumn(name = "SANDBOX_ID", referencedColumnName = "SANDBOX_ID"))
    @AdminPresentation(excluded = true)
    protected SandBox overrideSandBox;

    @OneToMany(mappedBy = "adminUser", targetEntity = AdminUserAttributeImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @MapKey(name = "name")
    @BatchSize(size = 50)
    @AdminPresentation(excluded = true)
    protected Map<String, AdminUserAttribute> additionalFields = new HashMap<String, AdminUserAttribute>();

    @Override
    public void setUnencodedPassword(String unencodedPassword) {
        this.unencodedPassword = unencodedPassword;
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

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Boolean getActiveStatusFlag() {
        return activeStatusFlag;
    }

    @Override
    public void setActiveStatusFlag(Boolean activeStatusFlag) {
        this.activeStatusFlag = activeStatusFlag;
    }

    @Override
    public Set<AdminRole> getAllRoles() {
        return allRoles;
    }

    @Override
    public void setAllRoles(Set<AdminRole> allRoles) {
        this.allRoles = allRoles;
    }

    @Override
    public SandBox getOverrideSandBox() {
        return overrideSandBox;
    }

    @Override
    public void setOverrideSandBox(SandBox overrideSandBox) {
        this.overrideSandBox = overrideSandBox;
    }

    @Override
    public Set<AdminPermission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public void setAllPermissions(Set<AdminPermission> allPermissions) {
        this.allPermissions = allPermissions;
    }

    @Override
    public ContextType getContextType() {
        return ContextType.GLOBAL;
    }

    @Override
    public void setContextType(ContextType contextType) {
        //do nothing
    }

    @Override
    public String getContextKey() {
        return null;
    }

    @Override
    public void setContextKey(String contextKey) {
        //do nothing
    }
    
    @Override
    public Map<String, String> getFlatAdditionalFields() {
        Map<String, String> map = new HashMap<String, String>();
        for (Entry<String, AdminUserAttribute> entry : getAdditionalFields().entrySet()) {
            map.put(entry.getKey(), entry.getValue().getValue());
        }
        return map;
    }
    
    @Override
    public Map<String, AdminUserAttribute> getAdditionalFields() {
        return additionalFields;
    }

    @Override
    public void setAdditionalFields(Map<String, AdminUserAttribute> additionalFields) {
        this.additionalFields = additionalFields;
    }
    
    @Override
    public Long getLastUsedSandBoxId() {
        AdminUserAttribute attr = getAdditionalFields().get(LAST_USED_SANDBOX);
        if (attr != null && StringUtils.isNotBlank(attr.getValue())) {
            return Long.parseLong(attr.getValue());
        }
        return null;
    }
    
    @Override
    public void setLastUsedSandBoxId(Long sandBoxId) {
        AdminUserAttribute attr = getAdditionalFields().get(LAST_USED_SANDBOX);
        if (attr == null) {
            attr = new AdminUserAttributeImpl();
            attr.setName(LAST_USED_SANDBOX);
            attr.setAdminUser(this);
            getAdditionalFields().put(LAST_USED_SANDBOX, attr);
        }
        attr.setValue(String.valueOf(sandBoxId));
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }

}
