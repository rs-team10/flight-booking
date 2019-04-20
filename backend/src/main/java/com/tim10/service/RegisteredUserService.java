package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

	public boolean addFriend(RegisteredUser user, RegisteredUser friend) {
		if(friendRequestSent(user, friend))
			return false;
		Friendship userFriendship = new Friendship(RequestStatus.WAITING, user, friend);
		Friendship friendFriendship = new Friendship(RequestStatus.WAITING, friend, user);
		user.getFriendships().add(userFriendship);
		friend.getFriendships().add(friendFriendship);
		this.registeredUserRepository.save(user);
		this.registeredUserRepository.save(friend);
		
		return true;
	}
	
	private boolean friendRequestSent(RegisteredUser user, RegisteredUser friend) {
        for(Friendship f: user.getFriendships()) {
            if (f.getSender().getId().equals(user.getId()) && f.getReceiver().getId().equals(friend.getId()) ||
            	f.getSender().getId().equals(friend.getId()) && f.getReceiver().getId().equals(user.getId()) ) {
            	return true;
            }
        }
        return false;
    }
	
	public boolean removeFriend(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship userFriendship = getUserFriendship(currentUser, friend);
		Friendship friendFriendship = getUserFriendship(friend, currentUser);
		removeUserFriendship(currentUser, userFriendship.getId());
		removeUserFriendship(friend, friendFriendship.getId());
		friendshipRepository.delete(userFriendship);
		friendshipRepository.delete(friendFriendship);
		this.registeredUserRepository.save(currentUser);
		this.registeredUserRepository.save(friend);
		
		return true;
	}

	public boolean acceptFriendRequest(RegisteredUser currentUser, RegisteredUser friend) {
		if(friendRequestSent(currentUser, friend) && getUserFriendship(currentUser, friend).getStatus() != RequestStatus.WAITING)
			return false;
		getUserFriendship(currentUser, friend).setStatus(RequestStatus.ACCEPTED);
		getUserFriendship(friend, currentUser).setStatus(RequestStatus.ACCEPTED);
		this.registeredUserRepository.save(currentUser);
		this.registeredUserRepository.save(friend);
		
		return true;
	}
	
    private Friendship getUserFriendship(RegisteredUser user, RegisteredUser friend) {
        for(Friendship f : user.getFriendships())
            if (f.getSender().getId().equals(user.getId()) &&
            		f.getReceiver().getId().equals(friend.getId()))
                return f;
        return null;
    }
    
	public boolean declineFriendRequest(RegisteredUser currentUser, RegisteredUser friend) {
		if(!friendRequestSent(currentUser, friend) || getUserFriendship(currentUser, friend).getStatus() != RequestStatus.WAITING)
			return false;
		Friendship userFriendship = getUserFriendship(currentUser, friend);
		Friendship friendFriendship = getUserFriendship(friend, currentUser);
		removeUserFriendship(currentUser, userFriendship.getId());
		removeUserFriendship(friend, friendFriendship.getId());
		this.registeredUserRepository.save(currentUser);
		this.registeredUserRepository.save(friend);
		
		return true;
	}
	
    private boolean removeUserFriendship(RegisteredUser user, Long id){
        for(Friendship f: user.getFriendships()) {
            if (f.getId().equals(id)) {
                user.getFriendships().remove(f);
                return true;
            }
        }
        return false;
    }
}
