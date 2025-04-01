package com.pcn.playing_ground.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pcn.playing_ground.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.is_active = :is_active")
	List<User> findAllByStatus(@Param("is_active") Boolean is_active);
}
