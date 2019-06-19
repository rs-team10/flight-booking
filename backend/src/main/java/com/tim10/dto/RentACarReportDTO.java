package com.tim10.dto;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.RentACar;

public class RentACarReportDTO {
	
	
	//Prosecna ocena hotela
		private Double averageFeedback;
		private Integer numberOfFeedbacks;
		
		//Prosecna ocena branchOffica
		private  Set<BranchOffice> branchOffices;
		
		//daily report
		private Map<Long, Integer> dailyReports = new TreeMap<Long, Integer>();
		
		//weekly report
		private Map<Long, Integer> weeklyReports = new TreeMap<Long, Integer>();
		
		//yearly report
		private Map<Long, Integer> yearlyReports = new TreeMap<Long, Integer>();
		
		public RentACarReportDTO() {
			super();
		}
		
		public RentACarReportDTO(RentACar rentACar) {
			this.averageFeedback = rentACar.getAverageFeedback() != null ? rentACar.getAverageFeedback() : 0;
			this.numberOfFeedbacks = 0;
			this.branchOffices = rentACar.getBranchOffices();
		}

		public Double getAverageFeedback() {
			return averageFeedback;
		}

		public Integer getNumberOfFeedbacks() {
			return numberOfFeedbacks;
		}

		public Set<BranchOffice> getBranchOffices() {
			return branchOffices;
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

		public void setRoomTypes(Set<BranchOffice> branchOffices) {
			this.branchOffices = branchOffices;
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
