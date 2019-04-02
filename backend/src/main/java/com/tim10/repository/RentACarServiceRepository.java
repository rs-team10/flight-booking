package com.tim10.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tim10.domain.Location;
import com.tim10.domain.RentACar;

@RepositoryRestResource
public class RentACarServiceRepository implements IRentACarServiceRepository {

	@SuppressWarnings("unused")
	private static AtomicLong counter = new AtomicLong();
	private final ConcurrentMap<Long, RentACar> rcss = new ConcurrentHashMap<Long, RentACar>();

	@Override
	public RentACar update(RentACar rentACarService) {

		Long id = rentACarService.getId();
		if (id != null)
			if (rcss.keySet().contains(id)) {
				RentACar rcs = this.rcss.get(id);
				String name = rentACarService.getName();
				String description = rentACarService.getDescription();
				Location location = rentACarService.getLocation();
				rcs.setName(name);
				rcs.setDescription(description);
				rcs.setLocation(location);

				return rcs;
			}

		return null;
	}

}
