
package com.wakacommerce.core.social.domain;


public interface UserConnection {

    UserConnectionImpl.UserConnectionPK getUserConnectionPK();

    void setUserConnectionPK(UserConnectionImpl.UserConnectionPK userConnectionPK);

    Integer getRank();

    void setRank(Integer rank);

    String getDisplayName();

    void setDisplayName(String displayName);

    String getProfileUrl();

    void setProfileUrl(String profileUrl);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getSecret();

    void setSecret(String secret);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    Long getExpireTime();

    void setExpireTime(Long expireTime);

}
