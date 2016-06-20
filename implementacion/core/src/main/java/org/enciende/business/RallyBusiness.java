package org.enciende.business;

import java.util.List;
import java.util.Map;

import org.enciende.exception.BusinessException;
import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.Rally;
import org.enciende.model.Usuario;

public interface RallyBusiness {
	public List<Grupo> findAllGruposByRallyId(Integer rallyId);
	
	public List<Usuario> findAllUsuariosByGrupoAndRallyId(Integer rallyId, Integer grupoId, String token) throws BusinessException;
	
	public List<GrupoUsuario> findAllStaffByRallyId(Integer rallyId);

	public List<Rally> findAllRallys();

	public Grupo saveGrupo(Grupo grupo);
	
	public Grupo getGrupo(Integer grupoId);

	public GrupoUsuario inscribir(Usuario usuario);

	public List<ActividadGrupo> findActividadesByIdGrupo(Integer grupoId);

	public List<ActividadGrupo> cambiarEstatus(List<ActividadGrupo> actividades, String tokenStaff);

	public List<ActividadGrupo> getUltimaActividadByGrupo(Integer idRally);
	
	public List<Map<String, String>> getTopicsByRally(Integer idRally);
}
