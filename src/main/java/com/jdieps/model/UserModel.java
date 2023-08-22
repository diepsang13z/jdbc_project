package com.jdieps.model;

public class UserModel {

	private long id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String phoneNumber;
	private EStatus status;
	private long roleId;

	public UserModel(String username, String password, String fullname, String email, String phoneNumber,
			EStatus status, long roleId) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.roleId = roleId;
	}

	public UserModel(long id, String username, String password, String fullname, String email, String phoneNumber,
			EStatus status, long roleId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.roleId = roleId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRole(long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", username=" + username + ", password=" + password + ", fullname=" + fullname
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", status=" + status + ", role=" + roleId + "]";
	}

}
