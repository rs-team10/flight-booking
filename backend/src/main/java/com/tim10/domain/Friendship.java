package com.tim10.domain;

public class Friendship {
	
	private Long id;
	private RequestStatus status;

	private RegisteredUser sender;
	private RegisteredUser receiver;

	public Friendship() {
	}

	public Long getId() {
		return id;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public RegisteredUser getSender() {
		return sender;
	}

	public RegisteredUser getReceiver() {
		return receiver;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public void setSender(RegisteredUser sender) {
		this.sender = sender;
	}

	public void setReceiver(RegisteredUser receiver) {
		this.receiver = receiver;
	}

}