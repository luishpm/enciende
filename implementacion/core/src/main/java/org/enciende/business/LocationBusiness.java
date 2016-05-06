package org.enciende.business;

import java.util.List;

import org.enciende.model.LocationLog;

public interface LocationBusiness {
	public void guardarLocations(List<LocationLog> locations);

	public List<LocationLog> getLocationsByGrupo(Integer idGrupo);
}
