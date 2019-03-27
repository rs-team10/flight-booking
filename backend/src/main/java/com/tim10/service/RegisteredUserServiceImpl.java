package com.tim10.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.RegisteredUser;
import com.tim10.repository.InMemoryRegisteredUserRepository;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {
	
	@Autowired
	private InMemoryRegisteredUserRepository registeredUserRepository;

	@Override
	public Collection<RegisteredUser> findAll() {
		Collection<RegisteredUser> registeredUsers = registeredUserRepository.findAll();
		return registeredUsers;
	}

	@Override
	public RegisteredUser create(RegisteredUser registeredUser) throws Exception {
		if(registeredUser.getId() != null)
			throw new Exception("Id has to be null when creating new entity");
		RegisteredUser savedRegisteredUser = registeredUserRepository.create(registeredUser);
		return savedRegisteredUser;
	}

	@Override
	public RegisteredUser findByUsername(String username) {
		RegisteredUser registeredUser = registeredUserRepository.findByUsername(username);
		return registeredUser;
	}

	@Override
	public RegisteredUser findByEmail(String email) {
		RegisteredUser registeredUser = registeredUserRepository.findByEmail(email);
		return registeredUser;
	}

	@Override
	public RegisteredUser findById(Long id) {
		RegisteredUser registeredUser = registeredUserRepository.findById(id);
		return registeredUser;
	}

	@Override
	public RegisteredUser update(RegisteredUser registeredUser) throws Exception {
		RegisteredUser registeredUserToUpdate = findById(registeredUser.getId());
		if(registeredUserToUpdate == null)
			throw new Exception("Entity not found");
		
		registeredUserToUpdate.setUsername(registeredUser.getUsername());
		registeredUserToUpdate.setPassword(registeredUser.getPassword());
		registeredUserToUpdate.setFirstName(registeredUser.getFirstName());
		registeredUserToUpdate.setLastName(registeredUser.getLastName());
		registeredUserToUpdate.setEmail(registeredUser.getEmail());
		registeredUserToUpdate.setPhone(registeredUser.getPhone());
		registeredUserToUpdate.setAddress(registeredUser.getAddress());

		RegisteredUser updatedRegisteredUser = registeredUserRepository.create(registeredUserToUpdate);
		return updatedRegisteredUser;
		
	}
}
