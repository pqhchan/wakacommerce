
package com.wakacommerce.core.store.domain;

public interface ZipCode {

    public String getId();

    public void setId(String id);

    public Integer getZipcode();

    public void setZipcode(Integer zipcode);

    public String getZipState();

    public void setZipState(String zipState);

    public String getZipCity();

    public void setZipCity(String zipCity);

    public double getZipLongitude();

    public void setZipLongitude(double zipLongitude);

    public double getZipLatitude();

    public void setZipLatitude(double zipLatitude);

}
