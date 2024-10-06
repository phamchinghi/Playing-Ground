package com.pcn.playing_ground.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    private String descriptions;
    private String update_by;
    private Timestamp dattime;
    private Timestamp created_at;
    
    enum Roles {
        USER, ADMIN
    }
    
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRolename() {
		return roleName;
	}
	public void setRolename(String rolename) {
		this.roleName = rolename;
	}
	public String getDescription() {
		return descriptions;
	}
	public void setDescription(String description) {
		this.descriptions = description;
	}
	public String getUpdateby() {
		return update_by;
	}
	public void setUpdateby(String updateby) {
		this.update_by = updateby;
	}
	public Timestamp getDatime() {
		return dattime;
	}
	public void setDatime(Timestamp datime) {
		this.dattime = datime;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
}
