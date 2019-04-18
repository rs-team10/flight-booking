package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> { 
	
	Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
    
    
}