package com.pcn.playing_ground.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Set;


public class SignupRequest {
    @NotBlank(message = "First is required!")
    private String firstname;
    @NotBlank(message = "Last is required!")
    private String lastname;
    @NotBlank(message = "User Name is required!")
    private String username;
    @NotBlank(message = "Email is required!")
    private String email;
    @NotBlank(message = "Password is required!")
    private String password;
    @NotBlank(message = "Phone is required!")
    private String phone;
    private boolean is_active;
    private String update_by;
    private Date dattime;
    private Date created_at;
    private Set<String> role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
