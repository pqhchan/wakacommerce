package com.wakacommerce.wechat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WakaAsstConfig {
	
	public static final String TABLE_PREFIX = "WK_WC_";
	public static final int COL_LEN_URL  = 1024;
	public static final int COL_LEN_CONTENT  = 4000;
	public static final int COL_LEN_TITLE  = 200;
	public static final int COL_LEN_USER_NAME  = 100;
	public static final int COL_LEN_INDICATOR  = 64;
	
	public static class WeChatReqMsgType {
		public static final String TEXT = "text";
		public static final String IMAGE = "image";
		public static final String LOCATION = "location";
		public static final String LINK = "link";
		public static final String EVENT = "event";
		public static final String VIDEO = "video";
		public static final String VOICE = "voice";
	}
	
	public static class WeChatRespMsgType {
		public static final String TEXT = "text";
		public static final String IMAGE = "image";
		public static final String VOICE = "voice";
		public static final String VIDEO = "video";
		public static final String MUSIC = "music";
		public static final String NEWS = "news";
	}
	
	@Value("${site.baseurl}")
	private String sitefront;
	
	@Value("${wechat.token}")
	private String token;
	
	@Value("${wechat.appid}") 
	private String appid;
	
	@Value("${wechat.appsecret}")
	private String appsecret;
	
	@Value("${wechat.menuCreateUrl}")
	private String menuCreateUrl;
	
	@Value("${wechat.menuGetUrl}")
	private String menuGetUrl;
	
	@Value("${wechat.menuDelUrl}")
	private String menuDeleteUrl;
	
	@Value("${wechat.accessTokenCreateUrl}")
	private String accessTokenCreateUrl;
	
	@Value("${wechat.customSendUrl}")
	private String customSendUrl;

	@Value("${wechat.meidaUploadUrl}")
	private String mediaUploadUrl;
	
	@Value("${wechat.qrcodeCreateUrl}")
	private String qrcodeCreateUrl;
	
	@Value("${wechat.userGetUrl}")
	private String userInfoUrl;
	
	@Value("${wechat.userGetUrl}") 
	private String userGetUrl;
	
	@Value("${wechat.groupsCreateUrl}")
	private String groupsCreateUrl;
	
	@Value("${wechat.groupsGetUrl}")
	private String groupsGetUrl;
	
	@Value("${wechat.groupsGetidUrl}")
	private String groupsGetIdUrl;
	
	@Value("${wechat.groupsUpdateUrl}")
	private String groupsUpdateUrl;
	
	@Value("${wechat.groupsMembersUpdateUrl}")
	private String groupsMembersUpdateUrl;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getAppid() {
		return appid;
	}
	
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getAppsecret() {
		return appsecret;
	}
	
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
	public String getMenuCreateUrl() {
		return menuCreateUrl;
	}
	
	public void setMenuCreateUrl(String menuCreateUrl) {
		this.menuCreateUrl = menuCreateUrl;
	}
	
	public String getMenuGetUrl() {
		return menuGetUrl;
	}
	
	public void setMenuGetUrl(String menuGetUrl) {
		this.menuGetUrl = menuGetUrl;
	}
	
	public String getMenuDeleteUrl() {
		return menuDeleteUrl;
	}
	
	public void setMenuDeleteUrl(String menuDeleteUrl) {
		this.menuDeleteUrl = menuDeleteUrl;
	}
	
	public String getAccessTokenCreateUrl() {
		return accessTokenCreateUrl;
	}
	
	public void setAccessTokenCreateUrl(String accessTokenCreateUrl) {
		this.accessTokenCreateUrl = accessTokenCreateUrl;
	}
	
	public String getCustomSendUrl() {
		return customSendUrl;
	}
	
	public void setCustomSendUrl(String customSendUrl) {
		this.customSendUrl = customSendUrl;
	}
	
	public String getMediaUploadUrl() {
		return mediaUploadUrl;
	}
	
	public void setMediaUploadUrl(String mediaUploadUrl) {
		this.mediaUploadUrl = mediaUploadUrl;
	}
	
	public String getQrcodeCreateUrl() {
		return qrcodeCreateUrl;
	}
	
	public void setQrcodeCreateUrl(String qrcodeCreateUrl) {
		this.qrcodeCreateUrl = qrcodeCreateUrl;
	}
	
	public String getUserInfoUrl() {
		return userInfoUrl;
	}
	
	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}
	
	public String getUserGetUrl() {
		return userGetUrl;
	}
	
	public void setUserGetUrl(String userGetUrl) {
		this.userGetUrl = userGetUrl;
	}
	
	public String getGroupsCreateUrl() {
		return groupsCreateUrl;
	}
	
	public void setGroupsCreateUrl(String groupsCreateUrl) {
		this.groupsCreateUrl = groupsCreateUrl;
	}
	
	public String getGroupsGetUrl() {
		return groupsGetUrl;
	}
	
	public void setGroupsGetUrl(String groupsGetUrl) {
		this.groupsGetUrl = groupsGetUrl;
	}
	
	public String getGroupsGetIdUrl() {
		return groupsGetIdUrl;
	}
	
	public void setGroupsGetIdUrl(String groupsGetIdUrl) {
		this.groupsGetIdUrl = groupsGetIdUrl;
	}
	
	public String getGroupsUpdateUrl() {
		return groupsUpdateUrl;
	}
	
	public void setGroupsUpdateUrl(String groupsUpdateUrl) {
		this.groupsUpdateUrl = groupsUpdateUrl;
	}
	
	public String getGroupsMembersUpdateUrl() {
		return groupsMembersUpdateUrl;
	}
	
	public void setGroupsMembersUpdateUrl(String groupsMembersUpdateUrl) {
		this.groupsMembersUpdateUrl = groupsMembersUpdateUrl;
	}
	
	public String getSitefront() {
		return sitefront;
	}

	public void setSitefront(String sitefront) {
		this.sitefront = sitefront;
	}
	
}
