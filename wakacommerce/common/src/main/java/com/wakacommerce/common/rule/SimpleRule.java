package com.wakacommerce.common.rule;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface SimpleRule extends Serializable {

    @Nonnull
    public String getMatchRule();

    public void setMatchRule(@Nonnull String matchRule);

}
