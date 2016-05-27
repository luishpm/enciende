package org.enciende.persistence.repository;

import java.util.List;

import org.enciende.model.Noticia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface NoticiaRepository extends PagingAndSortingRepository<Noticia, Integer> {

	@Query("from Noticia ")
	public List<Noticia> findAllNoticias();
	
	@Query("from Noticia n where n.idNoticia = ?1 order by n.fechaNoticia desc")
	public Noticia findNoticia(Long noticiaId);
	
	
	
}
