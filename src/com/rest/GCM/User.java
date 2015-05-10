package com.rest.GCM;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 */
@XmlRootElement
public class User {
 
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

    public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}


	private String userName;
    private String communityId;
    private String deviceId;
         
}
