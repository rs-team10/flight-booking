package com.tim10.service;

import java.util.Collection;

import com.tim10.domain.User;

public interface UserService {
	
	Collection<User> findAll();
	
	User create(User user) throws Exception;
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	User update(User user) throws Exception;
}
