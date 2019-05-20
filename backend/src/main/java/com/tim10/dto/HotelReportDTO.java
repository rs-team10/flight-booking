package com.tim10.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tim10.domain.Hotel;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.domain.RoomType;

public class HotelReportDTO {
	
	//Prosecna ocena hotela
	private Double averageFeedback;
	private Integer numberOfFeedbacks;
	
	//Prosecna ocena soba
	private  Set<RoomType> roomTypes;
	
	//daily report
	private Map<Date, Integer> dailyReports = new HashMap<Date, Integer>();
	
	public HotelReportDTO() {
		super();
	}
	
	public HotelReportDTO(Hotel hotel) {
		this.averageFeedback = hotel.getAverageFeedback() != null ? hotel.getAverageFeedback() : 0;
		this.numberOfFeedbacks = 0;
		for(Room room : hotel.getRooms()) {
			for(RoomReservation roomReservation : room.getRoomReservations()) {
				if(roomReservation.getReview() != null) {
					if(roomReservation.getReview().getCompanyFeedback() != null)
						numberOfFeedbacks += 1;
				}
			}
		} 
		this.roomTypes = hotel.getRoomTypes();
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public Integer getNumberOfFeedbacks() {
		return numberOfFeedbacks;
	}

	public Set<RoomType> getRoomTypes() {
		return roomTypes;
	}

	public Map<Date, Integer> getDailyReports() {
		return dailyReports;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setNumberOfFeedbacks(Integer numberOfFeedbacks) {
		this.numberOfFeedbacks = numberOfFeedbacks;
	}

	public void setRoomTypes(Set<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}

	public void setDailyReports(Map<Date, Integer> dailyReports) {
		this.dailyReports = dailyReports;
	}
	
	
}
