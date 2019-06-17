package com.tim10.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.RegisteredUser;
import com.tim10.domain.Reservation;
import com.tim10.dto.RegisteredUserSearchDTO;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long>{

	RegisteredUser findOneByEmail(String email);
	Optional<RegisteredUser> findOneByVerificationCode (String findVerificationCode);
	
	@Query(value = "SELECT u.first_name AS firstName, u.last_name AS lastName, u.email AS email " + 
			"FROM User u " + 
			"WHERE u.id NOT IN " + 
				"(SELECT f.sender_id FROM Friendship f WHERE f.receiver_id = :currentUserId UNION ALL SELECT f.receiver_id FROM Friendship f WHERE f.sender_id = :currentUserId) " +
			"AND lower(concat(u.first_name, ' ', u.last_name)) LIKE concat('%', lower(:parameter), '%') " +
			"AND u.id != :currentUserId AND u.dType = 'RegisteredUser'", nativeQuery = true)
	List<RegisteredUserSearchDTO> findByParameter(@Param("parameter") String parameter, @Param("currentUserId") Long currentUserId);

	
	RegisteredUser findOneByUsername(String username);

}
