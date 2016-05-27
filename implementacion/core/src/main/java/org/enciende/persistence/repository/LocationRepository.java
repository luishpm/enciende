package org.enciende.persistence.repository;

import java.util.List;

import org.enciende.model.LocationLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface LocationRepository extends PagingAndSortingRepository<LocationLog, Integer> {

	@Query("from LocationLog l where l.grupoUsuario.id.grupoIdGrupo = ?1 ")
	public List<LocationLog> findLocationsByGrupo(Integer idGrupo, double precision);
	
}
