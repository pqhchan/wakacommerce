
package com.wakacommerce.common.util;

/**
 *
 * @ hui
 */
public class Tuple<A, B> {
    
    protected final A one;
    protected final B two;
    
    public Tuple(A one, B two) {
        this.one = one;
        this.two = two;
    }
    
    public A getFirst() {
        return one;
    }
    
    public B getSecond() {
        return two;
    }

}
