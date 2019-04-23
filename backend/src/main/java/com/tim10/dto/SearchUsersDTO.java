package com.tim10.dto;

import java.util.List;

public class SearchUsersDTO {
	
	List<RegisteredUserSearchDTO> users;
	
	public SearchUsersDTO() {
	}
	
    public SearchUsersDTO(List<RegisteredUserSearchDTO> users) {
        this.users = users;
    }

    public List<RegisteredUserSearchDTO> getUsers() {
        return users;
    }

    public void setUsers(List<RegisteredUserSearchDTO> users) {
        this.users = users;
    }
}
