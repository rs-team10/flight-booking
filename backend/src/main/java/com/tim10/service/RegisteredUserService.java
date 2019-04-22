package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Friendship;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.RequestStatus;
import com.tim10.dto.RegisteredUserSearchDTO;
import com.tim10.dto.UserFriendsDTO;
import com.tim10.repository.FriendshipRepository;
import com.tim10.repository.RegisteredUserRepository;


@Service
public class RegisteredUserService {
	
	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	
    @Autowired
    private FriendshipRepository friendshipRepository;
    
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	public RegisteredUser firstSave(RegisteredUser registeredUser) {
		
		
		String password = passwordEncoder.encode(registeredUser.getPassword());
		registeredUser.setPassword(password);


		return registeredUserRepository.save(registeredUser);
	}
	
	public RegisteredUser save(RegisteredUser registeredUser) {
		


		return registeredUserRepository.save(registeredUser);
	}
	
	public RegisteredUser findOneByEmail(String email) {
		return registeredUserRepository.findOneByEmail(email);
	}
	
	public RegisteredUser findOne(Long id) {
		Optional<RegisteredUser> user = registeredUserRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}

	public RegisteredUser findVerificationCode (String findVerificationCode) throws ResourceNotFoundException {
		
		Optional<RegisteredUser> regUser = registeredUserRepository.findOneByVerificationCode(findVerificationCode);
		
		if(regUser.isPresent())
			return regUser.get();
		else
			throw new ResourceNotFoundException("Registered user with this verification code not found!"); 
	}

	
	
	// =====================================================================
	// FRIENDSHIPS
	// =====================================================================
	
	public List<RegisteredUserSearchDTO> findByParameter(String parameter, Long currentUserId) {
		return registeredUserRepository.findByParameter(parameter, currentUserId);
	}
	
	public List<UserFriendsDTO> getAllFriends(Long id) {
		return friendshipRepository.getAllFriends(id);
	}
	
	public List<UserFriendsDTO> getAllFriendshipRequests(Long id) {
		return friendshipRepository.getAllFriendshipRequests(id);
	}

	public boolean addFriend(RegisteredUser currentUser, RegisteredUser friend) {
		if(friendRequestSent(currentUser, friend))
			return false;
		Friendship friendship = new Friendship(RequestStatus.WAITING, currentUser, friend);
		friendshipRepository.save(friendship);
		
		return true;
	}
	
	private boolean friendRequestSent(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		return (friendship == null) ? false : true;
    }
	
	public boolean removeFriend(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		friendshipRepository.delete(friendship);
		
		return true;
	}

	public boolean acceptFriendRequest(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		if(friendRequestSent(currentUser, friend) && friendship.getStatus() != RequestStatus.WAITING)
			return false;
		friendship.setStatus(RequestStatus.ACCEPTED);
		friendshipRepository.save(friendship);
		
		return true;
	}
    
	public boolean declineFriendRequest(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		if(!friendRequestSent(currentUser, friend) || friendship.getStatus() != RequestStatus.WAITING)
			return false;
		friendshipRepository.deleteById(friendship.getId());
		
		return true;
	}
}
