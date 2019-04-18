package com.tim10.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.RegisteredUser;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long>{

	RegisteredUser findOneByEmail(String email);
	Optional<RegisteredUser> findOneByVerificationCode (String findVerificationCode);


}
