
package com.wakacommerce.common.copy;

/**
 *
 * @ hui
 */
public class CreateResponse<G> {

    public CreateResponse(G clone, boolean alreadyPopulated) {
        this.clone = clone;
        this.alreadyPopulated = alreadyPopulated;
    }

    private G clone;
    private boolean alreadyPopulated = false;

    public G getClone() {
        return clone;
    }

    public boolean isAlreadyPopulated() {
        return alreadyPopulated;
    }
}
