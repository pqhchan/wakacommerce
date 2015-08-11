package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

public interface IdGeneration extends Serializable {

    public String getType();

    public void setType(String type);

    public Long getBegin();

    public void setBegin(Long begin);

    public Long getEnd();

    public void setEnd(Long end);

    public Long getBatchStart();

    public void setBatchStart(Long batchStart);

    public Long getBatchSize();

    public void setBatchSize(Long batchSize);

    public Integer getVersion();

}
