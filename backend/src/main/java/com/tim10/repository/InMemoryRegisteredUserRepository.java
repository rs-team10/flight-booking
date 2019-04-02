package com.tim10.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.tim10.domain.RegisteredUser;

@Repository
public class InMemoryRegisteredUserRepository implements RegisteredUserRepository {
	
	private static AtomicLong counter = new AtomicLong();
	
	private final ConcurrentMap<Long, RegisteredUser> registeredUsers = new ConcurrentHashMap<Long, RegisteredUser>();
	
	@Override
	public Collection<RegisteredUser> findAll() {
		return this.registeredUsers.values();
	}

	@Override
	public RegisteredUser create(RegisteredUser registeredUser) {
		Long id = registeredUser.getId();
		if(id == null) {
			id = counter.incrementAndGet();
			registeredUser.setId(id);
		}
		this.registeredUsers.put(id, registeredUser);
		return registeredUser;
	}

	@Override
	public RegisteredUser findByUsername(String username) {
		RegisteredUser searchedUser = null;
		for(RegisteredUser registeredUser : registeredUsers.values())
			if(registeredUser.getUsername().equals(username))
				searchedUser = registeredUser;
		return searchedUser;
	}

	@Override
	public RegisteredUser findByEmail(String email) {
		RegisteredUser searchedUser = null;
		for(RegisteredUser registeredUser : registeredUsers.values())
			if(registeredUser.getEmail().equals(email))
				searchedUser = registeredUser;
		return searchedUser;
	}

	@Override
	public RegisteredUser findById(Long id) {
		return this.registeredUsers.get(id);
	}

	@Override
	public RegisteredUser update(RegisteredUser registeredUser) {
		Long id = registeredUser.getId();
		if(id != null) {
			this.registeredUsers.put(id, registeredUser);
		}
		return registeredUser;
	}
}
