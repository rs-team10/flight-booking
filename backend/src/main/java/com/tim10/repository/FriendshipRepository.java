package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Friendship;
import com.tim10.dto.UserFriendsDTO;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	
	
	@Query(value = "SELECT u.* " +
				   "FROM Friendship f JOIN User u ON f.receiver = r.id " +
				   "WHERE f.sender = :id", nativeQuery = true)
	List<UserFriendsDTO> getAllFriends(@Param("id") Long id);

}
