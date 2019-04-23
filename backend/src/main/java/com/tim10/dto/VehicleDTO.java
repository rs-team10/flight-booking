package com.tim10.dto;

import java.math.BigDecimal;

public interface VehicleDTO {
	
	Long getId();
	String getManufacturer();
	String getModel();
	Integer getYear();
	String getFuel();
	Double getEngine();
	Boolean getTransmission();
	Integer getSeatsCount();
	Boolean getAirCondition();
	BigDecimal getDailyRentalPrice();
	Double getAverageFeedback();
}
