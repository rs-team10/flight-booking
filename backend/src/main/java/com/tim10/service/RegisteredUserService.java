package com.tim10.service;

import java.util.Collection;

import com.tim10.domain.RegisteredUser;

public interface RegisteredUserService {
	
	Collection<RegisteredUser> findAll();
	
	RegisteredUser create(RegisteredUser registeredUser) throws Exception;
	
	RegisteredUser findByUsername(String username);
	
	RegisteredUser findByEmail(String email);
	
	RegisteredUser findById(Long id);
	
	RegisteredUser update(RegisteredUser registeredUser) throws Exception;
}
