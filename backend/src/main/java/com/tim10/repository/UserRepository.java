package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findOneByUsername(String username);
	
	Optional<User> findOneByEmail(String email);
	
	
}
