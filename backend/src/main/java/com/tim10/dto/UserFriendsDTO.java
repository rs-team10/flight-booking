package com.tim10.dto;

import com.tim10.domain.RequestStatus;

public interface UserFriendsDTO {
	String getFirstName();
	String getLastName();
	String getEmail();
	RequestStatus getStatus();
}
