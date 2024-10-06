package com.pcn.playing_ground.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pcn.playing_ground.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(String roleName);

//	@Query("SELECT R.ROLE_NAME FROM ROLES R INNER JOIN USER_ROLES UR ON R.ROLE_ID = UR.ROLE_ID WHERE UR.USER_ID = ?1")
//	List<String> findRolesByUserId(Integer userId);
	
}
