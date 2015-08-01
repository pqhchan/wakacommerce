
package com.wakacommerce.common.sandbox.domain;

import java.io.Serializable;

/**
 * 
 */
public interface SandBoxManagement extends Serializable {

    SandBox getSandBox();

    void setSandBox(SandBox sandBox);
}
