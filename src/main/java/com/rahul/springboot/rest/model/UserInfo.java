package com.rahul.springboot.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserInfo {

	@Id
	private String userId;
	private String emailId;
	private String department;
	
	public UserInfo() {
	}

	public UserInfo(String userId, String emailId, String department) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.department = department;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", emailId=" + emailId + ", department=" + department + "]";
	}

	
}
