package com.wakacommerce.cms.url.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.cms.url.type.URLRedirectType;
import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.web.Locatable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_URL_HANDLER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "URLHandlerImpl_friendlyName")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class URLHandlerImpl implements URLHandler, Locatable, AdminMainEntity, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "URLHandlerID")
    @GenericGenerator(
        name="URLHandlerID",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="URLHandlerImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.url.domain.URLHandlerImpl")
        }
    )
    @Column(name = "URL_HANDLER_ID")
    @AdminPresentation(
    		friendlyName = "URLHandlerImpl_ID", 
    		order = 1, 
    		group = "URLHandlerImpl_friendlyName", 
    		groupOrder = 1, 
    		visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @Column(name = "INCOMING_URL", nullable = false)
    @Index(name="INCOMING_URL_INDEX", columnNames={"INCOMING_URL"})
    @AdminPresentation(
    		friendlyName = "URLHandlerImpl_incomingURL", 
    		order = 1, 
    		group = "URLHandlerImpl_friendlyName", 
    		prominent = true, 
    		groupOrder = 1,
    		helpText = "URLHandlerImpl_incomingURL_help")
    protected String incomingURL;

    @Column(name = "NEW_URL", nullable = false)
    @AdminPresentation(
    		friendlyName = "URLHandlerImpl_newURL", 
    		order = 1, 
    		group = "URLHandlerImpl_friendlyName", 
    		prominent = true, 
    		groupOrder = 1,
            helpText = "URLHandlerImpl_newURL_help")
    protected String newURL;

    @Column(name = "URL_REDIRECT_TYPE")
    @AdminPresentation(
    		friendlyName = "URLHandlerImpl_redirectType", 
    		order = 4, 
    		group = "URLHandlerImpl_friendlyName", 
    		fieldType = SupportedFieldType.WAKA_ENUMERATION, 
    		wakaEnumeration = "com.wakacommerce.cms.url.type.URLRedirectType", 
    		groupOrder = 2, 
    		prominent = true)
    protected String urlRedirectType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getIncomingURL() {
        return incomingURL;
    }

    @Override
    public void setIncomingURL(String incomingURL) {
        this.incomingURL = incomingURL;
    }

    @Override
    public String getNewURL() {
        return newURL;
    }

    @Override
    public void setNewURL(String newURL) {
        this.newURL = newURL;
    }

    @Override
    public URLRedirectType getUrlRedirectType() {
        return URLRedirectType.getInstance(urlRedirectType);
    }

    @Override
    public void setUrlRedirectType(URLRedirectType redirectType) {
        this.urlRedirectType = redirectType.getType();
    }

    @Override
    public String getMainEntityName() {
        return getIncomingURL();
    }

    @Override
    public String getLocation() {
        String location = getIncomingURL();
        if (location == null) {
            return null;
        } else if (hasRegExCharacters(location)) {
            return getNewURL();
        } else {
            return location;
        }
    }

    protected boolean hasRegExCharacters(String location) {
        return location.contains(".") ||
                location.contains("(") ||
                location.contains(")") ||
                location.contains("?") ||
                location.contains("*") ||
                location.contains("^") ||
                location.contains("$") ||
                location.contains("[") ||
                location.contains("{") ||
                location.contains("|") ||
                location.contains("+") ||
                location.contains("\\");
    }

    @Override
    public <G extends URLHandler> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        URLHandler cloned = createResponse.getClone();
        cloned.setIncomingURL(incomingURL);
        cloned.setNewURL(newURL);
        cloned.setUrlRedirectType( URLRedirectType.getInstance(urlRedirectType));

        return createResponse;
    }
}
