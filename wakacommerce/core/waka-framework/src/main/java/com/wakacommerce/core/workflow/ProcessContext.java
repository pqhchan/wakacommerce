
package com.wakacommerce.core.workflow;

import java.io.Serializable;

public interface ProcessContext<T> extends Serializable {

    public boolean stopProcess();

    public boolean isStopped();

    public void setSeedData(T seedObject);

    public T getSeedData();

}
