package org.enciende.business.impl;

import org.enciende.business.UsuarioBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.Usuario;
import org.enciende.persistence.repository.UsuarioRepository;
import org.enciende.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioBusinessImpl implements UsuarioBusiness{
	@Autowired
	FacebookService facebookService;
	
	@Autowired
	UsuarioRepository dao;
	
	@Transactional(readOnly=true)
	@Override
	public Usuario getUsuarioByTokenFacebook(String token) {
		String idFacebook = facebookService.getFacebookIdByToken(token);
		
		if(idFacebook!=null){
			return dao.getUsuarioByIdFacebook(idFacebook);
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public Usuario registrarFacebook(String tokenFacebook, String tokenEvento) {
		String idFacebook = facebookService.getFacebookIdByToken(tokenFacebook);
		
		if(idFacebook!=null){
			Usuario usuario = dao.getUsuarioByTokenEvento(tokenEvento);
			if(usuario!=null){
				usuario.setIdFacebook(idFacebook);
				return usuario;
			}else{
				throw new BusinessException("TokenEvento no válido","200");
			}
		}else{
			throw new BusinessException("TokenFacebook no válido","100");
		}
	}

}
