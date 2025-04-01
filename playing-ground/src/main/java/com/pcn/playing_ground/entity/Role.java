package com.pcn.playing_ground.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.pcn.playing_ground.seeder.ERoleConverter;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ROLES")
public class Role extends BaseEntity{
	@Convert(converter = ERoleConverter.class)
	@Column(name = "ROLE_NAME")
	private ERole roleName;
	@Column(name = "DESCRIPTIONS")
	private String descriptions;

}

