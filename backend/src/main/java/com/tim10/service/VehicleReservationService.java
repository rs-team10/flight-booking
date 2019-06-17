package com.tim10.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.Location;
import com.tim10.domain.PriceList;
import com.tim10.domain.PriceListItem;
import com.tim10.domain.QuickVehicleReservation;
import com.tim10.domain.RentACar;
import com.tim10.domain.Vehicle;
import com.tim10.domain.VehicleReservation;
import com.tim10.dto.QuickResSendDTO;
import com.tim10.dto.QuickVehicleReservationDTO;
import com.tim10.dto.VehicleReservationDTO;
import com.tim10.dto.VehicleReservationPrewDTO;
import com.tim10.dto.VehicleReservationPrewQuickDTO;
import com.tim10.repository.QuickVehicleReservationRepository;
import com.tim10.repository.RentACarRepository;
import com.tim10.repository.VehicleRepository;
import com.tim10.repository.VehicleReservationRepository;


@Service
public class VehicleReservationService {
	
	public static int index = 0;
	/*
	@Autowired
	private BranchOfficeRepository branchOfficeRepository;
	*/
	@Autowired
	private VehicleReservationRepository vehicleReservationRepository;
	
	@Autowired
	private QuickVehicleReservationRepository qVehicleReservationRepository;
	
	
	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private RentACarRepository rentACarRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Transactional(readOnly = true, propagation=Propagation.REQUIRES_NEW)
	public VehicleReservationPrewDTO forPreview(Long vehicleId) throws  ResourceNotFoundException, ParseException{
		
		
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
	
	public VehicleReservationPrewQuickDTO getAllQuickResFromVehicle(Long vehicleId, String from, String to) throws ResourceNotFoundException, ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 
		Date fromD = format.parse(from);
		Date toD = format.parse(to);
		
		QuickVehicleReservation holder=null;
		
		
		Collection<QuickVehicleReservation> col =vehicleReservationRepository.getAllQuickResFromVehicle(vehicleId);
		try {
			holder = col.stream().filter(qr -> qr.filter(fromD, toD)).collect(Collectors.toList()).get(0);
		}catch (Exception e){
			throw new ResourceNotFoundException("There is no quick reservation for vehicle: "+vehicleId+" in chosen date!");
		}

		
		Vehicle vehicle = holder.getReservedVehicle();
		
		BranchOffice bo = vehicle.getBranchOffice();
		Location loc = bo.getLocation(); 
		RentACar rac = bo.getMainOffice();
		
		
		Long rentACarId= rac.getId();
		String rentACarName = rac.getName();
		Long branchOfficeId =  bo.getId();
		String country = loc.getCountry();
		String city = loc.getCity();
		Long vId = vehicleId;
		
		PriceList priceList = new PriceList();
		
		for(PriceListItem i : holder.getAdditionalServices()) {
			priceList.getPriceListItems().add(i);
		}
		Long reservationId = holder.getId();
	
		Date qFrom = holder.getDateFrom();
		Date qTo = holder.getDateTo();
		
		
		VehicleReservationPrewQuickDTO ret = new VehicleReservationPrewQuickDTO(rentACarId,  rentACarName, branchOfficeId, country, city, vId, priceList, qFrom, qTo, reservationId);
		
		return ret;
		
		
		
		
		
	}
	
	
	
	public Collection<QuickResSendDTO> getQuickReservations(Long rentACarId) throws ResourceNotFoundException{
		
		Optional<RentACar> rentACarMyb = rentACarRepository.findById(rentACarId);
		if(!rentACarMyb.isPresent())
			throw new ResourceNotFoundException("Rent a car service with id: "+rentACarId+" doesn't exist!");
		
		RentACar rentACar = rentACarMyb.get();
		
		
		HashSet<QuickResSendDTO> send = new HashSet<QuickResSendDTO>();
		
		for(QuickVehicleReservation q : rentACar.getQuickVehicleReservations()) {
			
			QuickResSendDTO holder = new QuickResSendDTO(q.getId(), q.getDateFrom(), q.getDateTo(), q.getTotalPrice(), q.getAdditionalServices(), q.getDiscount(), q.getReservedVehicle());
			send.add(holder);
		}
				 
		
		return send;
	}
	
	public void createQucikVehicleReservation(QuickVehicleReservationDTO vehicleResDTO) throws ResourceNotFoundException, IOException {
		
		
		
		Long id = vehicleResDTO.getVehicleId();
        Optional<Vehicle> mybVeh = vehicleRepository.findById(id);
        
        if(!mybVeh.isPresent())
        	throw new ResourceNotFoundException("Vehicle with id: "+id+" doesn't exist!");
        Vehicle vehicle = mybVeh.get();
		
		Date from = vehicleResDTO.getDateFrom();
		Date to = vehicleResDTO.getDateTo();
		
		if(vehicle.isReserved(from, to))
			throw new IOException("Vehicle with id: "+id+" is already taken!");
		
		
		QuickVehicleReservation vehicleRes = new QuickVehicleReservation();
		
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
		
		//vehicle = vehicleRepository.save(vehicle);
		//System.out.println("STA JE OVAJ BROJ OVDE REKAO -> " + vehicle.getReservations().size());
		RentACar rc = rentACarRepository.findById(vehicleResDTO.getRentACarId()).get();
		rc.getQuickVehicleReservations().add(vehicleRes);
		rentACarRepository.save(rc);
		
		//vehicleReservationRepository.save(vehicleRes);
		
		//Vehicle ajde = vehicleRepository.findById(vehicle.getId()).get();
		//System.out.println(ajde.getId());
		//7System.out.println("STA JE OVAJ BROJ OVDE REKAO -> " + ajde.getReservations().size());
		
		
	}
	
	
	//@Lock(LockModeType.PESSIMISTIC_READ)
	//
	@Transactional(propagation=Propagation.REQUIRES_NEW, isolation=Isolation.REPEATABLE_READ )
	public boolean saveVehicleReservation(Long mainReservationId, VehicleReservationDTO vehicleResDTO) throws ResourceNotFoundException, IOException, InterruptedException, Exception {
		
		System.out.println("--------------------");
		
		Long id = vehicleResDTO.getVehicleId();
		/*
		Query q = em.createQuery("from Vehicle v where v.id = :vehicleId");
		q.setParameter("vehicleId", id);
		q.setLockMode(LockModeType.PESSIMISTIC_READ);
		q.getResultList().get(0);
		Vehicle vehicle =(Vehicle) q.getResultList().get(0);
		
		Vrati dobro vozilo, kada dodje sledeci req komotno procita isto vozilo
		
		Vehicle vehicle = em.find(Vehicle.class, id);
		em.lock(vehicle, LockModeType.PESSIMISTIC_READ);
		
		
		Vehicle vehicle = em.find(Vehicle.class, id, LockModeType.PESSIMISTIC_READ);
		*/
		
		
		Vehicle vehicle = em.find(Vehicle.class, id);
        System.out.println(em.getLockMode(vehicle));
        				
        em.lock(vehicle, LockModeType.PESSIMISTIC_WRITE);//LockModeType.PESSIMISTIC_READ //ovo bi trebalo da baci exception 

	    System.out.println(vehicle.getId()+" "+vehicle.getManufacturer()+" "+em.getLockMode(vehicle));
	    
		
		
	    /*
        Optional<Vehicle> mybVeh = vehicleRepository.findById(id); 
        
        if(!mybVeh.isPresent())
        	throw new ResourceNotFoundException("Vehicle with id: "+id+" doesn't exist!");
        Vehicle vehicle = mybVeh.get();
        */
       
        
		Date from = vehicleResDTO.getDateFrom();
		Date to = vehicleResDTO.getDateTo();
		
		
		if(vehicle.isReserved(from, to))
			throw new IOException("Voziolo je zauzeto");

		
		
		VehicleReservation vehicleRes = new VehicleReservation();
		
		vehicleRes.setDateFrom(from);
		vehicleRes.setDateTo(to);
        vehicleRes.setReservedVehicle(vehicle);
        
        LocalDate cFrom = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate cTo = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int days = (int) ChronoUnit.DAYS.between(cFrom, cTo); 
        
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
		
		Thread.sleep(5000);
		vehicleReservationRepository.save(vehicleRes);
		


		return true;
		
	}
	
	
	
	
	
	public Collection<QuickVehicleReservationDTO> rentACarQuick(Long rentACarId) throws ResourceNotFoundException{
		 
		 
		 
		 Optional<RentACar> rc = rentACarRepository.findById(rentACarId);
		 
		 if(!rc.isPresent())
			 throw new ResourceNotFoundException("Rent a car with id: "+rentACarId+" doesn't exist!");
			 
		 RentACar rcT = rc.get();
		 
		 Set<QuickVehicleReservation> lit = rcT.getQuickVehicleReservations();
		 
		 HashSet<QuickVehicleReservationDTO> ret = new HashSet<QuickVehicleReservationDTO>();
		 
		 
		 
		 for(QuickVehicleReservation qr : lit) {
			 QuickVehicleReservationDTO qrdto = new QuickVehicleReservationDTO(qr.getId(), qr.getDateFrom(),qr.getDateTo(),qr.getAdditionalServices(), qr.getReservedVehicle().getId(),
					 qr.getReservedVehicle().getManufacturer(), qr.getReservedVehicle().getModel(), qr.getTotalPrice(), qr.getDiscount(), rentACarId);
			 ret.add(qrdto);
		 }
		 
		 
		 
		 return ret;
	 }
	 
	@Transactional(propagation=Propagation.REQUIRES_NEW, isolation=Isolation.REPEATABLE_READ )
	public boolean confirQuickVehicleRes(Long mainResId, Long vehicleResId) throws ResourceNotFoundException, ObjectOptimisticLockingFailureException, InterruptedException{
		
		
		
		QuickVehicleReservation quckRes;
		try {
			quckRes = qVehicleReservationRepository.findOneById(vehicleResId);
			Thread.sleep(5000);
		} catch (ObjectOptimisticLockingFailureException e) {
			throw new ObjectOptimisticLockingFailureException("Quick vehicle reservation has already been reserved", QuickVehicleReservation.class);
		}
		 
		return quckRes != null;
	}
	

}
