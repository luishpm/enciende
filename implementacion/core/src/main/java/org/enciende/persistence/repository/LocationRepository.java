package org.enciende.persistence.repository;

import org.enciende.model.LocationLog;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface LocationRepository extends PagingAndSortingRepository<LocationLog, Integer> {
	
}
