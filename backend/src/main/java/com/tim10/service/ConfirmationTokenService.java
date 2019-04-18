package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.ConfirmationToken;
import com.tim10.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	
	
	public List<ConfirmationToken> findAll(){
		return confirmationTokenRepository.findAll();
	}
	
	public Optional<ConfirmationToken> findById(Long id) {
		return confirmationTokenRepository.findById(id);
	}
	
	public ConfirmationToken save(ConfirmationToken confirmationToken) {
		return confirmationTokenRepository.save(confirmationToken);
	}


	public Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken){
		return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
	}
	
	

}
