package org.enciende.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.enciende.business.RallyBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.Actividad;
import org.enciende.model.Grupo;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.Rally;
import org.enciende.model.Usuario;
import org.enciende.persistence.repository.RallyRepository;
import org.enciende.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RallyBusinessImpl implements RallyBusiness {
	@Autowired
	private RallyRepository dao;
	
	@Autowired
	private UsuarioRepository daoUsuario;

	@Override
	public List<Grupo> findAllGruposByRallyId(Integer rallyId) {
		return dao.findAllGruposByRallyId(rallyId);
	}
	
	@Override
	public List<Rally> findAllRallys() {
		return dao.findAllRallys();
	}
	
	@Transactional
	@Override
	public Grupo saveGrupo(Grupo grupo){
		if(dao.findGruposByNombre(grupo.getNombre()).size()>0){
			throw new BusinessException("Ya existe un grupo con el nombre "+grupo.getNombre(),"R-G-101");
		}else{
			try{
				return dao.saveGrupo(grupo);
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessException("Error al grabar grupo","100",e);
			}
		}
		
	}

	@Transactional
	@Override
	public GrupoUsuario inscribir(Usuario usuario) {
		Usuario usuarioBD = daoUsuario.getUsuarioByIdEmail(usuario.getEmail());
		GrupoUsuario grupoUsuario = usuario.getGrupoUsuarios().get(0);
		usuario.setGrupoUsuarios(null);
		
		if(usuarioBD!=null){
			usuarioBD.setGenero(usuario.getGenero());
			usuarioBD.setNombre(usuario.getNombre());
			usuarioBD.setTallaPlayera(usuario.getTallaPlayera());
			if(usuarioBD.getGrupoUsuarios()==null){
				usuarioBD.setGrupoUsuarios(new ArrayList<GrupoUsuario>());
			}
			usuario = usuarioBD;
			
			grupoUsuario.getId().setUsuarioIdUsuario(usuario.getIdUsuario());
			
			
			int index = usuario.getGrupoUsuarios().indexOf(grupoUsuario);
			
			if(index>=0){
				GrupoUsuario grupoUsuarioBD = usuario.getGrupoUsuarios().get(index);
				grupoUsuarioBD.setRol(grupoUsuario.getRol());
				grupoUsuario = grupoUsuarioBD;
			}else{
				daoUsuario.saveGrupoUsuario(grupoUsuario);
			}
		}else{
			usuario = daoUsuario.save(usuario);
			usuario.setGrupoUsuarios(new ArrayList<GrupoUsuario>());
			
			grupoUsuario.setToken(usuario.getIdUsuario()+""+grupoUsuario.getId().getGrupoIdGrupo());
			grupoUsuario.getId().setUsuarioIdUsuario(usuario.getIdUsuario());
			daoUsuario.saveGrupoUsuario(grupoUsuario);
			grupoUsuario.setGrupo(dao.getGrupoById(grupoUsuario.getId().getGrupoIdGrupo()));
		}
		
		grupoUsuario.setUsuario(usuario);
		usuario.getGrupoUsuarios().add(grupoUsuario);
		return grupoUsuario;
	}
	
	@Override
	public List<Actividad> findActividadesByIdGrupo(Integer grupoId) {
		return dao.findActividadesByIdGrupo(grupoId);
	}

}
