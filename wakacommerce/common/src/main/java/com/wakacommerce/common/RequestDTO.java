package com.wakacommerce.common;

public interface RequestDTO {

    public String getRequestURI();

    public String getFullUrLWithQueryString();

    public Boolean isSecure();
    
}
