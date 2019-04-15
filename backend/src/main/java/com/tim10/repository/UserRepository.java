package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	//iz JPA
	List<User> findAll();
	<S extends User> S save(S user);
	
	User findByEmail(String email);
	
	

	
}
