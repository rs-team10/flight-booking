package com.tim10.repository;

import java.util.Collection;

import com.tim10.domain.User;

public interface UserRepository {
	
	Collection<User> findAll();
	
	User create(User user);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	User update(User user);
}
