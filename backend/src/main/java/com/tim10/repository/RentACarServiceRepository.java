package com.tim10.repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tim10.domain.RentACarService;



@RepositoryRestResource
public class RentACarServiceRepository implements IRentACarServiceRepository {

	
	@SuppressWarnings("unused")
	private static AtomicLong counter = new AtomicLong();
	private final ConcurrentMap<Long, RentACarService> rcss = new  ConcurrentHashMap<Long, RentACarService>();
	
	
	
	@Override
	public RentACarService update(RentACarService rentACarService) {
		
		Long id = rentACarService.getId();
		if(id != null) 
			if(rcss.keySet().contains(id)) {
				RentACarService rcs = this.rcss.get(id);
				String name = rentACarService.getName();
				String address = rentACarService.getDescription();
				String description = rentACarService.getAddress();
				rcs.setName(name);
				rcs.setDescription(description);
				rcs.setAddress(address);
				
				return rcs;
			}

		return null;
	}
	
	
	
	
	
	
}
