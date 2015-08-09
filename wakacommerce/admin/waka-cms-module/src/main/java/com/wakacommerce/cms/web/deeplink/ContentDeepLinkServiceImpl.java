package com.wakacommerce.cms.web.deeplink;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;
import com.wakacommerce.common.web.deeplink.DeepLink;
import com.wakacommerce.common.web.deeplink.DeepLinkService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ hui
 */
public class ContentDeepLinkServiceImpl extends DeepLinkService<StructuredContentDTO> {

    protected String structuredContentAdminPath;

    @Override
    protected List<DeepLink> getLinksInternal(StructuredContentDTO item) {
        List<DeepLink> links = new ArrayList<DeepLink>();

        links.add(new DeepLink()
            .withAdminBaseUrl(getAdminBaseUrl())
            .withUrlFragment(structuredContentAdminPath + item.getId())
            .withDisplayText("Edit")
            .withSourceObject(item));

        return links;
    }

    public String getStructuredContentAdminPath() {
        return structuredContentAdminPath;
    }

    public void setStructuredContentAdminPath(String structuredContentAdminPath) {
        this.structuredContentAdminPath = structuredContentAdminPath;
    }

}
