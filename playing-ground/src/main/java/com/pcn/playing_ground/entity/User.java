package com.pcn.playing_ground.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "USERS")
public class User extends BaseEntity{

	@Column(name = "USERNAME", length = 50, unique = true, nullable = false)
	private String username;

	@Column(name = "PASSWRD", length = 255, nullable = false)
	private String passwrd; // hash password

	@Column(name = "EMAIL", length = 100, unique = true, nullable = false)
	private String email;

	@Column(name = "FIRSTNAME", length = 30, nullable = false)
	private String firstname;

	@Column(name = "LASTNAME", length = 50, nullable = false)
	private String lastname;

	@Column(name = "PHONE", length = 10)
	private String phone;

	@Column(name = "IS_ACTIVE", columnDefinition = "BIT default 1", nullable = false)
	private boolean is_active;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "USER_ROLES",
			joinColumns = @JoinColumn(name = "UserID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "RoleID", referencedColumnName = "ID")
	)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private List<PasswordReset> passwordResets;
	@OneToMany(mappedBy = "user")
	private List<UserLog> userLogs;

}

