package com.tim10.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.RegisteredUser;
import com.tim10.dto.RegisteredUserSearchDTO;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long>{

	RegisteredUser findOneByEmail(String email);
	Optional<RegisteredUser> findOneByVerificationCode (String findVerificationCode);
	
	@Query("SELECT u.firstName AS firstName, u.lastName AS lastName, u.email AS email " + 
			"FROM User u " + 
			"WHERE lower(concat(u.firstName, ' ', u.lastName)) LIKE concat('%', lower(:parameter), '%')")
	List<RegisteredUserSearchDTO> findByParameter(@Param("parameter") String parameter);

}
