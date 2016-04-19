package org.enciende.business.impl;

import java.util.List;

import org.enciende.business.NoticiaBusiness;
import org.enciende.model.Noticia;
import org.enciende.persistence.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticiaBusinessImpl implements NoticiaBusiness {
	@Autowired
	private NoticiaRepository dao;
	
	@Override
	public List<Noticia> findAllNoticias() {
		return dao.findAllNoticias();
	}

}
