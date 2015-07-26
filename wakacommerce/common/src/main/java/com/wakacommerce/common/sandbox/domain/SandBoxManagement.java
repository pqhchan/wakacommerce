
package com.wakacommerce.common.sandbox.domain;

import java.io.Serializable;

/**
 *Jeff Fischer
 */
public interface SandBoxManagement extends Serializable {

    SandBox getSandBox();

    void setSandBox(SandBox sandBox);
}
