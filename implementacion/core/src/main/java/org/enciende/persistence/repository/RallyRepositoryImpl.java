package org.enciende.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;

public class RallyRepositoryImpl implements RallyRepositoryCustom {
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Grupo saveGrupo(Grupo grupo) {
		if(grupo.getIdGrupo()==null){
			em.persist(grupo);
			return grupo;
		}else{
			return em.merge(grupo);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActividadGrupo> getUltimaActividadByGrupo(Integer idRally) {
		String queryStr = "select * from ACTIVIDAD_GRUPO where (id_grupo,orden) in "+
				"(	select  id_grupo,max(orden) from ACTIVIDAD_GRUPO where estatus != 0  and id_grupo in "+
				"(select ID_GRUPO from GRUPO where rally_id_rally= :idRally) "+
			"group by ID_GRUPO "+
		")";
		Query query = em.createNativeQuery(queryStr, ActividadGrupo.class);
		query.setParameter("idRally", idRally);
		return query.getResultList();
	}
	
	

}
