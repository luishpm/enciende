package org.enciende.business.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.enciende.business.LocationBusiness;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.LocationLog;
import org.enciende.persistence.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationBusinessImpl implements LocationBusiness {
	@Autowired
	private LocationRepository dao;
	
	@Override
	@Transactional
	public void guardarLocations(List<LocationLog> locations) {
		if(locations!=null && locations.size()>0){
			GrupoUsuario gu = locations.get(0).getGrupoUsuario();
			
			for(LocationLog location : locations){
				if(StringUtils.isNotBlank(location.getLatitud()) && StringUtils.isNotBlank(location.getLongitud()) 
						&& location.getPrecision()!=null){
					location.setGrupoUsuario(gu);
					dao.save(location);
				}
			}
		}
	}
	
	@Override
	public List<LocationLog> getLocationsByGrupo(Integer idGrupo, double precision) {
		return dao.findLocationsByGrupo(idGrupo);
	}
}