package com.tim10.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.tim10.domain.Airline;
import com.tim10.domain.Flight;

public class AirlineReportDTO {

	private Double averageFeedback;
	private Integer numberOfFeedbacks;
	private Set<FlightDTO> flights;
	private Map<Long, Integer> dailyReports = new TreeMap<Long, Integer>();
	private Map<Long, Integer> weeklyReports = new TreeMap<Long, Integer>();
	private Map<Long, Integer> yearlyReports = new TreeMap<Long, Integer>();
	
	public AirlineReportDTO() {
		super();
	}

	public AirlineReportDTO(Airline airline) {
		super();
		
		this.averageFeedback = airline.getAverageFeedback() != null ? airline.getAverageFeedback() : 0;
		this.numberOfFeedbacks = 0;
		
		this.flights = new HashSet<FlightDTO>();
		
		for (Flight f : airline.getFlights()) {
			this.flights.add(new FlightDTO(f));
		}
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public Integer getNumberOfFeedbacks() {
		return numberOfFeedbacks;
	}

	public Set<FlightDTO> getFlights() {
		return flights;
	}

	public Map<Long, Integer> getDailyReports() {
		return dailyReports;
	}

	public Map<Long, Integer> getWeeklyReports() {
		return weeklyReports;
	}

	public Map<Long, Integer> getYearlyReports() {
		return yearlyReports;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setNumberOfFeedbacks(Integer numberOfFeedbacks) {
		this.numberOfFeedbacks = numberOfFeedbacks;
	}

	public void setFlights(Set<FlightDTO> flights) {
		this.flights = flights;
	}

	public void setDailyReports(Map<Long, Integer> dailyReports) {
		this.dailyReports = dailyReports;
	}

	public void setWeeklyReports(Map<Long, Integer> weeklyReports) {
		this.weeklyReports = weeklyReports;
	}

	public void setYearlyReports(Map<Long, Integer> yearlyReports) {
		this.yearlyReports = yearlyReports;
	}
}
