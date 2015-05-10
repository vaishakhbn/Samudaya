package com.rest.GCM;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 */
@XmlRootElement
public class Community {
 
    public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}


    public String getCommunityPassword() {
		return communityPassword;
	}
	public void setCommunityPassword(String communityPassword) {
		this.communityPassword = communityPassword;
	}


	private String communityId;
    private String communityPassword;
         
}
