package com.tim10.service;

import java.util.Collection;

import com.tim10.domain.User;
import com.tim10.repository.InMemoryUserRepository;

public class UserServiceImpl implements UserService {
	
	private InMemoryUserRepository userRepository;

	@Override
	public Collection<User> findAll() {
		Collection<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User create(User user) throws Exception {
		if(user.getId() != null)
			throw new Exception("Id has to be null when creating new entity");
		User savedUser = userRepository.create(user);
		return savedUser;
	}

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User findById(Long id) {
		User user = userRepository.findById(id);
		return user;
	}

	@Override
	public User update(User user) throws Exception {
		User userToUpdate = findById(user.getId());
		if(userToUpdate == null)
			throw new Exception("Entity not found");
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		// TODO: Dodati ostalo
		User updatedUser = userRepository.create(userToUpdate);
		return updatedUser;
		
	}
}
