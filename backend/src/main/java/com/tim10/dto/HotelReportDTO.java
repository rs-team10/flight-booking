package com.tim10.dto;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.tim10.domain.Hotel;
import com.tim10.domain.RoomType;

public class HotelReportDTO {
	
	//Prosecna ocena hotela
	private Double averageFeedback;
	private Integer numberOfFeedbacks;
	
	//Prosecna ocena soba
	private  Set<RoomType> roomTypes;
	
	//daily report
	private Map<Long, Integer> dailyReports = new TreeMap<Long, Integer>();
	
	//weekly report
	private Map<Long, Integer> weeklyReports = new TreeMap<Long, Integer>();
	
	//yearly report
	private Map<Long, Integer> yearlyReports = new TreeMap<Long, Integer>();
	
	public HotelReportDTO() {
		super();
	}
	
	public HotelReportDTO(Hotel hotel) {
		this.averageFeedback = hotel.getAverageFeedback() != null ? hotel.getAverageFeedback() : 0;
		this.numberOfFeedbacks = 0;
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

	public Map<Long, Integer> getDailyReports() {
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

	public void setDailyReports(Map<Long, Integer> dailyReports) {
		this.dailyReports = dailyReports;
	}

	public Map<Long, Integer> getWeeklyReports() {
		return weeklyReports;
	}

	public void setWeeklyReports(Map<Long, Integer> weeklyReports) {
		this.weeklyReports = weeklyReports;
	}

	public Map<Long, Integer> getYearlyReport() {
		return yearlyReports;
	}

	public void setYearlyReport(Map<Long, Integer> yearlyReport) {
		this.yearlyReports = yearlyReport;
	}
	
	
}
