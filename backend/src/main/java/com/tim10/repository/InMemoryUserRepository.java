package com.tim10.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.tim10.domain.User;

public class InMemoryUserRepository implements UserRepository {
	
	private static AtomicLong counter = new AtomicLong();
	
	private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<Long, User>();
	
	@Override
	public Collection<User> findAll() {
		return this.users.values();
	}

	@Override
	public User create(User user) {
		Long id = user.getId();
		if(id == null) {
			id = counter.incrementAndGet();
			user.setId(id);
		}
		this.users.put(id, user);
		return user;
	}

	@Override
	public User findByUsername(String username) {
		User searchedUser = null;
		for(User user : users.values())
			if(user.getUsername().equals(username))
				searchedUser = user;
		return searchedUser;
	}

	@Override
	public User findByEmail(String email) {
		User searchedUser = null;
		for(User user : users.values())
			if(user.getEmail().equals(email))
				searchedUser = user;
		return searchedUser;
	}

	@Override
	public User findById(Long id) {
		return this.users.get(id);
	}

	@Override
	public User update(User user) {
		Long id = user.getId();
		if(id != null) {
			this.users.put(id, user);
		}
		return user;
	}
}
