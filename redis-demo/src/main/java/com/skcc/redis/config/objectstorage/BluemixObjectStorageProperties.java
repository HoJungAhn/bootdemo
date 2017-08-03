package com.skcc.redis.config.objectstorage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.skcc.redis.config.AutoconfigureConst;

@ConfigurationProperties(prefix = AutoconfigureConst.PREFIX_BLUEMIX_OBJECTSTORAGE)
public class BluemixObjectStorageProperties {
	private String authUrl;
	private String domainName;
	private String project;
	private String userId;
	private String password;
	
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "BluemixObjectStorageProperties [auth_url=" + authUrl + ", domainName=" + domainName + ", project="
				+ project + ", userId=" + userId + ", password=" + password + "]";
	}
	
}
