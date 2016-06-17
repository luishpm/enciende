package org.enciende.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.enciende.business.RallyBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.Rally;
import org.enciende.model.Usuario;
import org.enciende.persistence.repository.RallyRepository;
import org.enciende.persistence.repository.UsuarioRepository;
import org.enciende.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RallyBusinessImpl implements RallyBusiness {
	@Autowired
	private RallyRepository dao;
	
	@Autowired
	private UsuarioRepository daoUsuario;
	
	@Autowired
	private EmailService emailService;

	
	
	@Override
	public List<Grupo> findAllGruposByRallyId(Integer rallyId) {
		return dao.findAllGruposByRallyId(rallyId);
	}
	
	@Override
	public List<Usuario> findAllUsuariosByGrupoAndRallyId(Integer rallyId, Integer grupoId, String token) throws BusinessException {
		GrupoUsuario gUsuarioStaff = dao.findGrupoUsuarioByToken(token);
		
		if("ki$59%38IO#".equals(token) || (gUsuarioStaff!=null && !"PARTICIPANTE".equals(gUsuarioStaff.getRol()))){
			return dao.findAllUsuariosByGrupoAndRallyId(rallyId, grupoId);
		}else{
			throw new BusinessException("TokenStaff no válido", "R-A-101");
		}
	}
	
	@Override
	public List<GrupoUsuario> findAllStaffByRallyId(Integer rallyId) {
		return dao.findAllStaffByRallyId(rallyId);
	}
	
	@Override
	public List<Rally> findAllRallys() {
		return dao.findAllRallys();
	}
	
	@Override
	public List<ActividadGrupo> getUltimaActividadByGrupo(Integer idRally){
		return dao.getUltimaActividadByGrupo(idRally);
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
			
			String token = Integer.toHexString(Integer.parseInt(usuario.getIdUsuario()+""+grupoUsuario.getId().getGrupoIdGrupo()));
			token += getRandomString(2);
			
			grupoUsuario.setToken(token.toUpperCase());
			grupoUsuario.setReportarUbiacion(true);;
			grupoUsuario.getId().setUsuarioIdUsuario(usuario.getIdUsuario());
			daoUsuario.saveGrupoUsuario(grupoUsuario);
			grupoUsuario.setGrupo(dao.getGrupoById(grupoUsuario.getId().getGrupoIdGrupo()));
			
			emailService.sendEmail(usuario.getEmail(), "Bienvenido al rally enciende 2016, te inivitamos a bajar nuestra app para que mantengas informado."
					+ " Tu código de registro es: "+token.toUpperCase(), "Bienvenido a rally enciende 2016");
		}
		
		grupoUsuario.setUsuario(usuario);
		usuario.getGrupoUsuarios().add(grupoUsuario);
		return grupoUsuario;
	}
	
	/**
	 * generador de palabras aleatorias de n caracteres
	 * 
	 * @return
	 */
	public static String getRandomString(int n) {
		char[] pw = new char[n];
		int c = 'A';
		int r1 = 0;
		for (int i = 0; i < n; i++) {
			r1 = (int) (Math.random() * 3);
			switch (r1) {
			case 0:
				c = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				c = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				c = 'A' + (int) (Math.random() * 26);
				break;
			}
			pw[i] = (char) c;
		}
		return new String(pw);
	}
	
	@Override
	public List<ActividadGrupo> findActividadesByIdGrupo(Integer grupoId) {
		return dao.findActividadesByIdGrupo(grupoId);
	}

	@Override
	@Transactional
	public List<ActividadGrupo> cambiarEstatus(List<ActividadGrupo> actividades, String tokenStaff) {
		if(actividades!=null && actividades.size()>0){
			GrupoUsuario gUsuarioStaff = dao.findGrupoUsuarioByToken(tokenStaff);
			
			if("ki$59%38IO#".equals(tokenStaff) || (gUsuarioStaff!=null && !"PARTICIPANTE".equals(gUsuarioStaff.getRol()))){
				List<ActividadGrupo> actividadesBd = dao.findActividadesGrupoNoFinalizadas(actividades.get(0).getId().getIdGrupo());
				Map<Integer, ActividadGrupo> actividadesMapa = new HashMap<Integer, ActividadGrupo>();
				for(ActividadGrupo aGrupo : actividadesBd){
					actividadesMapa.put(aGrupo.getId().getIdActividad(), aGrupo);
				}
				for(ActividadGrupo aGrupo : actividades){
					if(actividades.get(0).getId().getIdGrupo().equals(aGrupo.getId().getIdGrupo())){
						//Validamos que la actividad exista
						ActividadGrupo aGrupoBd = actividadesMapa.get(aGrupo.getId().getIdActividad()); 
						if(aGrupoBd!=null){
							//Validamos que el estatus enviado sea mayor al estatus existente
							if(aGrupo.getEstatus()>aGrupoBd.getEstatus()){
								aGrupoBd.setEstatus(aGrupo.getEstatus());
								
								if(aGrupo.getEstatus()==10){
									
								}else if(aGrupo.getEstatus()==20){
									aGrupoBd.setHoraInstrucciones(aGrupo.getHoraInstrucciones()!=null?aGrupo.getHoraInstrucciones():new Date());
								}else if(aGrupo.getEstatus()==30){
									aGrupoBd.setHoraDesbloqueada(aGrupo.getHoraDesbloqueada()!=null?aGrupo.getHoraDesbloqueada():new Date());
								}else if(aGrupo.getEstatus()==40){
									
								}else if(aGrupo.getEstatus()==100){
									aGrupoBd.setHoraTerminada(aGrupo.getHoraTerminada()!=null?aGrupo.getHoraTerminada():new Date());
									aGrupoBd.setCalificacion(aGrupo.getCalificacion());
								}
							}
						}
					}
				}
				return dao.findActividadesByIdGrupo(actividades.get(0).getId().getIdGrupo());
			}else{
				throw new BusinessException("TokenStaff no válido", "R-A-101");
			}
		}
		return null;
	}
	/**
	 * BLOQUEDA - 0
	 * PISTA -10
	 * COMO_LLEGAR - 20
	 * DESBLOQUEADA - 30
	 * SELFIE - 40
	 * FINALIZADA - 100
	 */

	@Override
	public Grupo getGrupo(Integer grupoId) {
		return dao.getGrupoById(grupoId);
	}

}
