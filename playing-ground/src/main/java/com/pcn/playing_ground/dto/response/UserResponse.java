package com.pcn.playing_ground.dto.response;

import java.util.Date;
import java.util.List;

public class UserResponse {
	private Long userID;
	private String userName;
	private String fullname;
    private String phone;
    private boolean is_active;
    private String update_by;
    private Date dattime;
    private Date created_at;
    private List<String> roles;
    
    
    
	public UserResponse(Long userID, String userName, String fullname, String phone, boolean is_active,
			String update_by, Date dattime, Date created_at, List<String> roles) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.fullname = fullname;
		this.phone = phone;
		this.is_active = is_active;
		this.update_by = update_by;
		this.dattime = dattime;
		this.created_at = created_at;
		this.roles = roles;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Date getDattime() {
		return dattime;
	}
	public void setDattime(Date dattime) {
		this.dattime = dattime;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    
    
}
