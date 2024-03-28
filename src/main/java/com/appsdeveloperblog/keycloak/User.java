package com.appsdeveloperblog.keycloak;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String userId;
//    private String roles;

	private String birthday;
	private String gender;
	private List<String> groups;
	private List<String> roles;
	private String cepTel;

	public String getCepTel() {
		return cepTel;
	}

	public void setCepTel(String cepTel) {
		this.cepTel = cepTel;
	}

	//	public String getRoles() {
//		return roles;
//	}
//	public void setRoles(String roles) {
//		this.roles = roles;
//	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    
}
