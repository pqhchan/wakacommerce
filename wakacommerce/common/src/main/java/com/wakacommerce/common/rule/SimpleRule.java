package com.wakacommerce.common.rule;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * 用MVEL表达式来表示的规则
 */
public interface SimpleRule extends Serializable {

    @Nonnull
    public String getMatchRule();

    public void setMatchRule(@Nonnull String matchRule);

}
