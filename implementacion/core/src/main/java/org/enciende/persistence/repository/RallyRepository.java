package org.enciende.persistence.repository;

import java.util.List;

import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.GrupoUsuarioPK;
import org.enciende.model.Rally;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RallyRepository extends PagingAndSortingRepository<Rally, Integer>,RallyRepositoryCustom{

	@Query("from Grupo g where g.rally.idRally = ?1 ")
	public List<Grupo> findAllGruposByRallyId(Integer rallyId);
	
	@Query("from GrupoUsuario g where g.grupo.rally.idRally = ?1 and g.grupo.idGrupo = ?2")
	public List<GrupoUsuario> findAllUsuariosByGrupoAndRallyId(Integer rallyId, Integer grupoId);
	
	@Query("from GrupoUsuario g where g.grupo.rally.idRally = ?1 and g.rol != 'PARTICIPANTE'")
	public List<GrupoUsuario> findAllStaffByRallyId(Integer rallyId);
	
	@Query("from Rally")
	public List<Rally> findAllRallys();
	
	@Query("from Grupo g where g.nombre = ?1")
	public List<Grupo> findGruposByNombre(String grupo);

	@Query("from GrupoUsuario g where g.id.grupoIdGrupo = ?1 and g.id.usuarioIdUsuario = ?2")
	public GrupoUsuario getGrupoUsuario(Integer grupoId, Integer usuarioId);
	
	@Query("from Grupo g where g.idGrupo = ?1")
	public Grupo getGrupoById(Integer grupoId);
	
	@Modifying
	@Query("delete from GrupoUsuario g where g.id = ?1")
	public void deleteGrupoUsuario(GrupoUsuarioPK id);
	
	@Query("from ActividadGrupo ag where ag.id.idGrupo =  ?1 order by ag.orden")
	public List<ActividadGrupo> findActividadesByIdGrupo(Integer idGrupo);

	@Query("from GrupoUsuario gu where gu.token=?1")
	public GrupoUsuario findGrupoUsuarioByToken(String tokenStaff);
	
	@Query("from ActividadGrupo ag where ag.estatus != 100 and ag.grupo.id = ?1 ")
	public List<ActividadGrupo> findActividadesGrupoNoFinalizadas(Integer idGrupo);
	
	
}
