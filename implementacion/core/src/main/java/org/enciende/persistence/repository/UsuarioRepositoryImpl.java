package org.enciende.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.enciende.model.GrupoUsuario;

public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public void saveGrupoUsuario(GrupoUsuario grupoUsuario) {
		em.persist(grupoUsuario);
		
	}

}
