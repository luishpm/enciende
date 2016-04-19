package org.enciende.persistence.repository;

import org.enciende.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer>, UsuarioRepositoryCustom {

	/**
	 * Obtiene un usuario por token
	 * @param token El token
	 * @return Un {@link Usuario}
	 */
	@Query("from Usuario u where u.idFacebook = ?1 ")
	public Usuario getUsuarioByIdFacebook(String idFacebook);
	
	/**
	 * Obtiene un usuario por email
	 * @param token El email
	 * @return Un {@link Usuario}
	 */
	@Query("from Usuario u where u.email = ?1 ")
	public Usuario getUsuarioByIdEmail(String email);
	
	@Query("select gu.usuario from GrupoUsuario gu where gu.token = ?1")
	public Usuario getUsuarioByTokenEvento(String tokenEvento);
	
}
