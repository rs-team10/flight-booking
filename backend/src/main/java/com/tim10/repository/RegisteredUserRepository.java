package com.tim10.repository;

import java.util.Collection;

import com.tim10.domain.RegisteredUser;

public interface RegisteredUserRepository {

	Collection<RegisteredUser> findAll();

	RegisteredUser create(RegisteredUser user);

	RegisteredUser findByUsername(String username);

	RegisteredUser findByEmail(String email);

	RegisteredUser findById(Long id);

	RegisteredUser update(RegisteredUser user);
}
