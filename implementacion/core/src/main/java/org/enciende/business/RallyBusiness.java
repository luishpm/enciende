package org.enciende.business;

import java.util.List;

import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.Rally;
import org.enciende.model.Usuario;

public interface RallyBusiness {
	public List<Grupo> findAllGruposByRallyId(Integer rallyId);
	
	public List<GrupoUsuario> findAllStaffByRallyId(Integer rallyId);

	public List<Rally> findAllRallys();

	public Grupo saveGrupo(Grupo grupo);

	public GrupoUsuario inscribir(Usuario usuario);

	public List<ActividadGrupo> findActividadesByIdGrupo(Integer grupoId);
	
	public List<ActividadGrupo> cambiarEstatus(List<ActividadGrupo> actividades, String tokenStaff);
}
