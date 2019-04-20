package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Friendship;
import com.tim10.dto.UserFriendsDTO;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	
	
	@Query(value = "SELECT u.first_name AS firstName, u.last_name AS lastName, u.email AS email, f.status AS status " +
				   "FROM Friendship f JOIN User u ON f.receiver_id = u.id " +
				   "WHERE f.sender_id = :id", nativeQuery = true)
	List<UserFriendsDTO> getAllFriends(@Param("id") Long id);

}
