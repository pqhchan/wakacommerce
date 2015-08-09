
package com.wakacommerce.common.extensibility.jpa;

/**
 *
 * @ hui
 */
public interface SiteDiscriminator extends Discriminatable {

    public Long getSiteDiscriminator();

    public void setSiteDiscriminator(Long siteDiscriminator);
}
