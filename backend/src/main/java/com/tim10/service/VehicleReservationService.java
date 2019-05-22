package com.tim10.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.Location;
import com.tim10.domain.PriceList;
import com.tim10.domain.PriceListItem;
import com.tim10.domain.RentACar;
import com.tim10.domain.Vehicle;
import com.tim10.domain.VehicleReservation;
import com.tim10.dto.VehicleReservationDTO;
import com.tim10.dto.VehicleReservationPrewDTO;
import com.tim10.repository.VehicleRepository;
import com.tim10.repository.VehicleReservationRepository;


@Service
public class VehicleReservationService {
	
	/*
	@Autowired
	private RentACarRepository rentACarRepository;
	
	
	@Autowired
	private BranchOfficeRepository branchOfficeRepository;
	*/
	@Autowired
	private VehicleReservationRepository vehicleReservationRepository;
	
	
	@Autowired
	private VehicleRepository vehicleRepository;

	
	
	
	public VehicleReservationPrewDTO forPreview(Long vehicleId) throws  ResourceNotFoundException{
		
		Optional<Vehicle> mybVehicle = vehicleRepository.findById(vehicleId);
		
		if(!mybVehicle.isPresent())
			throw new ResourceNotFoundException("Vehicle with id: "+vehicleId+" doesn't exist!");
		
		Vehicle vehicle = mybVehicle.get();
		
		BranchOffice bo = vehicle.getBranchOffice();
		Location loc = bo.getLocation(); 
		RentACar rac = bo.getMainOffice();
		
		
		Long rentACarId= rac.getId();
		String rentACarName = rac.getName();
		Long branchOfficeId =  bo.getId();
		String country = loc.getCountry();
		String city = loc.getCity();
		Long vId = vehicleId;
		PriceList priceList = rac.getAdditionalServicesPriceList();
		
		return new VehicleReservationPrewDTO(rentACarId,  rentACarName, branchOfficeId, country, city, vId, priceList);
		
	}
	
	
	/*
	public boolean saveVehicleReservation(Long mainReservationId, VehicleReservation vehicleRes) {
		
		Optional<Reservation> mybRes =  reservationRepository.findById(mainReservationId);
		
		if(!mybRes.isPresent())
			throw new ResourceNotFoundException("Reservation with id: "+mainReservationId+" doesn't exist!");
		
		Reservation res = mybRes.get();
		
		res.setVehicleReservation(vehicleRes);
		
		reservationRepository.save(res);
		
		vehicleReservationRepository.save(vehicleRes);
		
		return true;
		
	}
	*/
	
	public boolean saveVehicleReservation(Long mainReservationId, VehicleReservationDTO vehicleResDTO) throws ResourceNotFoundException {
		
		
		
		Long id = vehicleResDTO.getVehicleId();
        Optional<Vehicle> mybVeh = vehicleRepository.findById(id);
        
        if(!mybVeh.isPresent())
        	throw new ResourceNotFoundException("Vehicle with id: "+id+" doesn't exist!");
        Vehicle vehicle = mybVeh.get();
		
		Date from = vehicleResDTO.getDateFrom();
		Date to = vehicleResDTO.getDateTo();
		
		VehicleReservation vehicleRes = new VehicleReservation();
		
		vehicleRes.setDateFrom(from);
		vehicleRes.setDateTo(to);
		
		
		LocalDate cFrom = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate cTo = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int days = (int) ChronoUnit.DAYS.between(cFrom, cTo);
		
        
        
        vehicleRes.setReservedVehicle(vehicle);
        
        Set<PriceListItem> items = vehicleResDTO.getAdditionalServices();
        
        BigDecimal totalPrice = vehicle.getDailyRentalPrice().multiply(new BigDecimal(days));
        
        for(PriceListItem item : items) {
        	
        	BigDecimal percent = (new BigDecimal(1).subtract(item.getDiscount().divide(new BigDecimal(100))));
        	BigDecimal price = item.getPrice();
 
        	totalPrice = totalPrice.multiply(percent).add(price);
        	
        }
        
		
		vehicleRes.setTotalPrice(totalPrice);
		vehicleRes.setAdditionalServices(items);
		vehicle.getReservations().add(vehicleRes);
		
		vehicle = vehicleRepository.save(vehicle);
		System.out.println("STA JE OVAJ BROJ OVDE REKAO -> " + vehicle.getReservations().size());
		
		
		//vehicleReservationRepository.save(vehicleRes);
		
		//Vehicle ajde = vehicleRepository.findById(vehicle.getId()).get();
		//System.out.println(ajde.getId());
		//7System.out.println("STA JE OVAJ BROJ OVDE REKAO -> " + ajde.getReservations().size());
		
		return true;
		
	}
	

}
