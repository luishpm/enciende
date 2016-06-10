package org.enciende.persistence.repository;

import java.util.List;

import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;
import org.enciende.model.Usuario;

public interface RallyRepositoryCustom {
	public Grupo saveGrupo(Grupo grupo);
	
	public List<ActividadGrupo> getUltimaActividadByGrupo(Integer idRally);
	
	public List<Usuario> findAllUsuariosByGrupoAndRallyId(Integer rallyId, Integer grupoId);
}
