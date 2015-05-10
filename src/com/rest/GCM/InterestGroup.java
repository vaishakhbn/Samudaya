package com.rest.GCM;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 */
@XmlRootElement
public class InterestGroup {
 

	
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private String communityId;
	private int interestId;
    private String userName;
         
}
