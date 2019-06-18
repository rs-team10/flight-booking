package com.tim10.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tim10.dto.VehicleSearchDTO;
//a kako tip vozila!?
@Entity
@Table(name="Vehicles")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="manufacturer")
	private String manufacturer;
	
	@Column(name="model")
	private String model;
	
	@Column(name="year")
	private Integer year;
	
	@Column(name="fuel")
	private String fuel;
	
	@Column(name="engine")
	private Double engine;
	
	@Column(name="transmission")
	private Boolean transmission;
	
	@Column(name="seatsCount")
	private Integer seatsCount;
	
	@Column(name="airCondition")
	private Boolean airCondition;
	
	@Column(name="dailyRentalPrice")
	private BigDecimal dailyRentalPrice;
	
	@Column(name="averageFeedback")
	private Double averageFeedback;
	
	@Column(name="image")
	private String image = "defaultCar.jpg";
	
	@JsonIgnore
	@OneToMany(mappedBy="reservedVehicle", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<VehicleReservation> reservations;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "branchOfficeId")//nullable false
	private BranchOffice branchOffice;

	public Vehicle() {
		super();
		this.reservations = new HashSet<VehicleReservation>();
	}

	public Long getId() {
		return id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public Integer getYear() {
		return year;
	}

	public String getFuel() {
		return fuel;
	}

	public Double getEngine() {
		return engine;
	}

	public Boolean getTransmission() {
		return transmission;
	}

	public Integer getSeatsCount() {
		return seatsCount;
	}

	public Boolean getAirCondition() {
		return airCondition;
	}

	public BigDecimal getDailyRentalPrice() {
		return dailyRentalPrice;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public String getImage() {
		return image;
	}

	public Set<VehicleReservation> getReservations() {
		return reservations;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public void setEngine(Double engine) {
		this.engine = engine;
	}

	public void setTransmission(Boolean transmission) {
		this.transmission = transmission;
	}

	public void setSeatsCount(Integer seatsCount) {
		this.seatsCount = seatsCount;
	}

	public void setAirCondition(Boolean airCondition) {
		this.airCondition = airCondition;
	}

	public void setDailyRentalPrice(BigDecimal dailyRentalPrice) {
		this.dailyRentalPrice = dailyRentalPrice;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setReservations(Set<VehicleReservation> reservations) {
		this.reservations = reservations;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}
	
	public boolean isReserved(Date from, Date to) {
		System.out.println(this.id + " -> " + this.reservations.size());
		for( VehicleReservation r: this.reservations) {
			Date rFrom = r.getDateFrom();
			Date rTo = r.getDateTo();
			
			boolean prov1 = (from.before(rFrom) && to.after(rFrom) ) || (from.before(rTo) && to.after(rTo)); //svi koji su delimicno rezervisani
			boolean prov2 = from.after(rFrom) && to.before(rTo); //ako je vozilo zauzeto u celom intervalu
			boolean prov3 = from.equals(rFrom) && to.equals(rTo);
			
			if( prov1 || prov2 ||  prov3) {
				return true;
			}
			
		}
		return false;
	}
	
	public boolean isQuickReserved(Date from, Date to) {
		System.out.println(this.id + " -> " + this.reservations.size());
		for( VehicleReservation r: this.reservations) {
			if(r instanceof QuickVehicleReservation) {
				Date rFrom = r.getDateFrom();
				Date rTo = r.getDateTo();
				
				boolean prov1 = (from.before(rFrom) && to.after(rFrom) ) || (from.before(rTo) && to.after(rTo)); //svi koji su delimicno rezervisani
				boolean prov2 = from.after(rFrom) && to.before(rTo); //ako je vozilo zauzeto u celom intervalu
				boolean prov3 = from.equals(rFrom) && to.equals(rTo);
				
				if( prov1 || prov2 ||  prov3) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	public boolean filter(VehicleSearchDTO params) {
		
		
		String manufacturer = params.getManufacturer();
		String model = params.getModel();
		Integer year0 = params.getYear()[0]; 
		Integer year1 = params.getYear()[1]; 
		String fuel = params.getFuel(); 
		Double engine0 = params.getEngine()[0]; 
		Double engine1 =  params.getEngine()[1]; 
		Boolean transmission = params.getTransmission(); 
		Integer seatsCount = params.getSeatsCount();
		Boolean airCondition = params.getAirCondition(); 
		BigDecimal dailyRentalPrice0 = params.getDailyRentalPrice()[0]; 
		BigDecimal dailyRentalPrice1 = params.getDailyRentalPrice()[1];
		Date dateFrom = params.getDateFrom(); 
		Date dateTo = params.getDateTo();
		

		
		if(!manufacturer.equals(""))
			if(!this.manufacturer.toUpperCase().equals(manufacturer.toUpperCase()))
				return false;
		
		if(!model.equals(""))
			if(!this.model.equals(model))
				return false;
		
		if(!fuel.equals(""))
			if(!this.fuel.toUpperCase().equals(fuel.toUpperCase()))
				return false;
		
		if(!(year0 <= this.year && this.year <= year1))
			return false;
		
		if(!(engine0 <= this.engine && this.engine <= engine1))
			return false;
		
		if(!(dailyRentalPrice0.compareTo(this.dailyRentalPrice) == -1 ||  dailyRentalPrice0.compareTo(this.dailyRentalPrice) == 0)
		&& (dailyRentalPrice1.compareTo(this.dailyRentalPrice) == 1 ||  dailyRentalPrice1.compareTo(this.dailyRentalPrice) == 0))
			return false;
		if(seatsCount !=null)
			if(seatsCount!=0 && seatsCount!=1) 
				if(!this.seatsCount.equals(seatsCount)) 
					return false;
				
		
		if(transmission!= null)
			if(!this.transmission.equals(transmission))
				return false;
		
		if(airCondition!= null)
			if(!this.airCondition.equals(airCondition))
				return false;
		
		
		if(this.isReserved(dateFrom, dateTo))
			return false;
		
		
		return true;
	}
	
public boolean filterQuick(VehicleSearchDTO params) {
		
		
		String manufacturer = params.getManufacturer();
		String model = params.getModel();
		Integer year0 = params.getYear()[0]; 
		Integer year1 = params.getYear()[1]; 
		String fuel = params.getFuel(); 
		Double engine0 = params.getEngine()[0]; 
		Double engine1 =  params.getEngine()[1]; 
		Boolean transmission = params.getTransmission(); 
		Integer seatsCount = params.getSeatsCount();
		Boolean airCondition = params.getAirCondition(); 
		BigDecimal dailyRentalPrice0 = params.getDailyRentalPrice()[0]; 
		BigDecimal dailyRentalPrice1 = params.getDailyRentalPrice()[1];
		Date dateFrom = params.getDateFrom(); 
		Date dateTo = params.getDateTo();
		

		
		if(!manufacturer.equals(""))
			if(!this.manufacturer.toUpperCase().equals(manufacturer.toUpperCase()))
				return false;
		
		if(!model.equals(""))
			if(!this.model.equals(model))
				return false;
		
		if(!fuel.equals(""))
			if(!this.fuel.toUpperCase().equals(fuel.toUpperCase()))
				return false;
		
		if(!(year0 <= this.year && this.year <= year1))
			return false;
		
		if(!(engine0 <= this.engine && this.engine <= engine1))
			return false;
		
		if(!(dailyRentalPrice0.compareTo(this.dailyRentalPrice) == -1 ||  dailyRentalPrice0.compareTo(this.dailyRentalPrice) == 0)
		&& (dailyRentalPrice1.compareTo(this.dailyRentalPrice) == 1 ||  dailyRentalPrice1.compareTo(this.dailyRentalPrice) == 0))
			return false;
		if(seatsCount !=null)
			if(seatsCount!=0 && seatsCount!=1) 
				if(!this.seatsCount.equals(seatsCount)) 
					return false;
				
		
		if(transmission!= null)
			if(!this.transmission.equals(transmission))
				return false;
		
		if(airCondition!= null)
			if(!this.airCondition.equals(airCondition))
				return false;
		
		
		if(!this.isQuickReserved(dateFrom, dateTo))
			return false;
		
		
		return true;
	}
	
	

}