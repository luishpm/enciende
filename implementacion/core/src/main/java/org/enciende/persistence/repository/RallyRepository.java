package org.enciende.persistence.repository;

import java.util.List;

import org.enciende.model.Actividad;
import org.enciende.model.Grupo;
import org.enciende.model.GrupoUsuarioPK;
import org.enciende.model.Rally;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RallyRepository extends PagingAndSortingRepository<Rally, Integer>,RallyRepositoryCustom{

	@Query("from Grupo g where g.rally.idRally = ?1 ")
	public List<Grupo> findAllGruposByRallyId(Integer rallyId);
	
	@Query("from Rally")
	public List<Rally> findAllRallys();
	
	@Query("from Grupo g where g.nombre = ?1")
	public List<Grupo> findGruposByNombre(String grupo);

	@Query("from Grupo g where g.idGrupo = ?1")
	public Grupo getGrupoById(Integer grupoId);
	
	@Modifying
	@Query("delete from GrupoUsuario g where g.id = ?1")
	public void deleteGrupoUsuario(GrupoUsuarioPK id);
	
	@Query("select ag.actividad from ActividadGrupo ag where ag.id.idGrupo =  ?1 order by ag.orden")
	public List<Actividad> findActividadesByIdGrupo(Integer idGrupo);

}
