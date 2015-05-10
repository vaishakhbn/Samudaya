package com.rest.GCM;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 */
@XmlRootElement
public class Interest {
 

	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
	public String getInterestName() {
		return interestName;
	}
	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}
	
	
	private int interestId;
    private String interestName;
         
}
