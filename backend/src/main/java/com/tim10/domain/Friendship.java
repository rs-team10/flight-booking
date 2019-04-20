package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Friendship", uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "receiver_id"}))
public class Friendship {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private RequestStatus status;

	@OneToOne
	private RegisteredUser sender;
	
	@OneToOne
	private RegisteredUser receiver;

	public Friendship() {
	}
	
	public Friendship(RequestStatus status, RegisteredUser sender, RegisteredUser receiver) {
		super();
		this.status = status;
		this.sender = sender;
		this.receiver = receiver;
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