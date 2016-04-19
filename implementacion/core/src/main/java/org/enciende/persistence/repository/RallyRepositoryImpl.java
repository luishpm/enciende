package org.enciende.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
