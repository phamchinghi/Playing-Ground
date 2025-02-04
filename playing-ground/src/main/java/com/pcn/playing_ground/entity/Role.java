package com.pcn.playing_ground.entity;

import java.time.LocalDate;

import com.pcn.playing_ground.seeder.ERoleConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
    private Long roleId;
	@Convert(converter = ERoleConverter.class)
	@Column(name = "ROLE_NAME")
    private ERole roleName;
	@Column(name = "DESCRIPTIONS")
    private String descriptions;
	@Column(name = "UPDATE_BY")
    private String update_by;
	@Column(name = "DATTIME")
    private LocalDate dattime;
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDate created_at = LocalDate.now();;

	public Role(ERole name) {
		this.roleName = name;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public ERole getRolename() {
		return roleName;
	}
	public void setRolename(ERole rolename) {
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
	public LocalDate getDatime() {
		return dattime;
	}
	public void setDatime(LocalDate datime) {
		this.dattime = datime;
	}
	public LocalDate getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}
}
