package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Friendship;
import com.tim10.dto.UserFriendsDTO;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	
	
	@Query(value = "SELECT u.id AS id, u.first_name AS firstName, u.last_name AS lastName, u.email AS email, f.status AS status " +
				   "FROM Friendship f JOIN User u ON f.receiver_id = u.id " +
				   "WHERE f.sender_id = :id AND f.status != 'DENIED' " +
				   "UNION ALL " +
				   "SELECT u.id AS id, u.first_name AS firstName, u.last_name AS lastName, u.email AS email, f.status AS status " +
				   "FROM Friendship f JOIN User u ON f.sender_id = u.id " +
				   "WHERE f.receiver_id = :id AND f.status = 'ACCEPTED'", nativeQuery = true)
	List<UserFriendsDTO> getAllFriends(@Param("id") Long id);
	
	@Query(value = "SELECT u.id AS id, u.first_name AS firstName, u.last_name AS lastName, u.email AS email, f.status AS status " +
			   "FROM Friendship f JOIN User u ON f.receiver_id = u.id " +
			   "WHERE f.sender_id = :id AND f.status != 'DENIED' AND f.status != 'WAITING' " +
			   "UNION ALL " +
			   "SELECT u.id AS id, u.first_name AS firstName, u.last_name AS lastName, u.email AS email, f.status AS status " +
			   "FROM Friendship f JOIN User u ON f.sender_id = u.id " +
			   "WHERE f.receiver_id = :id AND f.status = 'ACCEPTED'", nativeQuery = true)
	List<UserFriendsDTO> getAllFriendsAccepted(@Param("id") Long id);
	
	@Query(value = "SELECT u.id as id, u.first_name AS firstName, u.last_name AS lastName, u.email AS email, f.status AS status " +
				   "FROM Friendship f JOIN User u ON f.sender_id = u.id " +
				   "WHERE f.receiver_id = :id AND f.status = 'WAITING'", nativeQuery = true)
	List<UserFriendsDTO> getAllFriendshipRequests(@Param("id") Long id);

	@Query(value = "SELECT * " + 
					"FROM Friendship f " +
					"WHERE (f.sender_id = :userId AND f.receiver_id = :friendId) " +
					"OR (f.sender_id = :friendId AND f.receiver_id = :userId)", nativeQuery = true)
	Friendship findOneByUserAndFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
