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
			
			emailService.sendEmail(usuario.getEmail(), getMessageToContact(token.toUpperCase()), "Bienvenido a rally enciende 2016");
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
	
	@Override
	public List<Map<String, String>> getTopicsByRally(Integer idRally) {
		String topicPrefix = "/topics/equipo_";
		
		List<Map<String, String>> topics = new ArrayList<>();
		List<Grupo> grupos = dao.findAllGruposByRallyId(idRally);
		
		if(grupos.size() > 0) {
			Map<String, String> topic = new HashMap<String, String>();
			topic.put("descripcion", "Rally " + idRally + ": " + grupos.get(0).getRally().getNombre());
			topic.put("nombre", "/topics/rally_" + idRally);
			topics.add(topic);
	
			for(Grupo grupo : grupos) {
				topic = new HashMap<String, String>();
				topic.put("descripcion", "Equipo " + grupo.getNombre());
				topic.put("nombre", topicPrefix + grupo.getIdGrupo());
				topics.add(topic);
			}
		}
		return topics;
	}
	
	private String getMessageToContact(String code){
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("    <head>");
		html.append("        <title>Enciende Mail</title>");
		html.append("        <meta charset=\"UTF-8\">");
		html.append("        <meta name=\"description\" content=\"\" />");
		html.append("        <meta name=\"keywords\" content=\"\" />");
		html.append("        <style>");
		html.append("            /*Fonts*/");
		html.append("            @import url(https://fonts.googleapis.com/css?family=Source+Sans+Pro);");
		html.append("        </style>");
		html.append("    </head>");
		html.append("    <body style=\"background-color: #FFF; font-family: 'Source Sans Pro', sans-serif; margin: 0;\">");
		html.append("        <style>");
		html.append("            /*Fonts*/");
		html.append("            @import url(https://fonts.googleapis.com/css?family=Source+Sans+Pro);");
		html.append("        </style>");
		html.append("        <div style=\"margin: 0 auto; background-color: #FFF;\">");
		html.append("            <table style=\"background: #9D0ED6; /* For browsers that do not support gradients */");
		html.append("                background: -webkit-linear-gradient(#9D0ED6, #8848FA); /* For Safari 5.1 to 6.0 */");
		html.append("                background: -o-linear-gradient(#9D0ED6, #8848FA); /* For Opera 11.1 to 12.0 */");
		html.append("                background: -moz-linear-gradient(#9D0ED6, #8848FA); /* For Firefox 3.6 to 15 */");
		html.append("                background: linear-gradient(#9D0ED6, #8848FA);\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		html.append("                <tbody>");
		html.append("                    <tr>");
		html.append("                        <td style=\"vertical-align: top; text-align:center; padding:22px 60px;\"><img src=\"http://i.imgur.com/46tmbti.png\" style=\"width: 200px; border:none; display:inline-block; height: auto; max-width: 100%;\" alt=\"Enciende\"></td>");
		html.append("                    </tr>");
		html.append("                    <tr>");
		html.append("                        <td style=\"vertical-align: top; padding:5px 20px 30px;\">");
		html.append("                            <p style=\"font-size: 25px; line-height: 19px; margin: 0 0 30px; color:#FFFFFF; text-align:center;\">¡Bienvenido al Rally Enciende 2016!</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">¡Gracias por ser parte del Rally Enciende 2016! Ya estamos a pocos días del evento y hay varias cosas que queremos contarte.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Por favor, lee con atención:</p>");
		html.append("                            <p style=\"font-size: 25px; line-height: 19px; margin: 30px 0 10px 30px; color:#FFFFFF; text-align:left;\">1. Indicaciones generales</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">El Rally comenzará a las 8:00am. ¡Llega temprano! Si llegas tarde no te enterarás de todas las instrucciones.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Viste con ropa cómoda, ya que viajarás en el metro por varios puntos de la ciudad. Puedes usar ropa deportiva o jeans, tenis y gorra. Te daremos una playera.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Te recomendamos llevar una mochila ligera, impermeable por si llueve, una botella de agua y alguna barrita energética o fruta. Tú decides.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">¡No te olvides de llegar desayunado! Correrás mucho.</p>");
		html.append("                            <p style=\"font-size: 25px; line-height: 19px; margin: 30px 0 10px 30px; color:#FFFFFF; text-align:left;\">2. App enciende</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Este año desarrollamos una aplicación móvil especial para el Rally. ¡Así las rutas serán más fáciles de seguir y las actividades más fáciles de compartir!</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">El día del rally procura ir con la batería del cel completamente llena. Si tienes una batería externa, llévala.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Procura que por lo menos alguien de tu equipo cuente con datos de Internet para compartir las fotos. No te preocupes, no se consumirá mucho.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">No es necesario que todo el equipo instale la app, pero pueden hacerlo. Con que una o dos personas la lleven activa TODO EL RECORRIDO será suficiente</p>");
		html.append("                            <p style=\"font-size: 25px; line-height: 19px; margin: 30px 0 10px 30px; color:#FFFFFF; text-align:left;\">3. Actividad especial</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Lleva dinero en efectivo o en tarjeta (como sea más cómodo), lo invertirás en una buena causa. Piensa en que la actividad podría requerir de 100 a 200 pesos aproximadamente por equipo.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">Más allá de correr, el Rally es para compartir a otros del amor de Dios y de las cosas que puede hacer en nuestras vidas. Tómate tu tiempo para cumplir adecuadamente las actividades.</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">¡Compartamos el amor de Dios con la mejor actitud!</p>");
		html.append("                            <p style=\"font-size: 15px; line-height: 17px; font-weight: 400; color: #FFFFFF; margin:0 0 10px;\">¡Nos vemos el sábado!  ¡Oremos juntos por el Rally!</p>");
		html.append("                        </td>");
		html.append("                    </tr>");
		html.append("                </tbody>");
		html.append("            </table>");
		html.append("            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		html.append("                <tbody>");
		html.append("                    <tr>");
		html.append("                        <td class=\"footer\" style=\"background-color:#242833; opacity: 0.9; text-align:center; padding:20px 30px;\">");
		html.append("                            <h5 style=\"font-size: 19px; font-weight: 400; margin: 10px 0 10px 0; color: #e6e6e6;\">Descarga la aplicación para tu celular e ingresa con el siguiente código de registro</h5>");
		html.append("                            <h4 style=\"font-size: 25px; font-weight: 700; line-height: 19px; margin: 0 0 20px; color: #FFFFFF;\">" + code + "</h4>");
		html.append("                            <div style=\"display:inline;\">");
		html.append("                                <a href=\"https://itunes.apple.com/us/app/enciende-app/id1113800928?mt=8\">");
		html.append("                                    <img style=\"height: 70px; border:none; display:inline-block; height: auto; max-width: 100px;\" src=\"http://i.imgur.com/sDIfmDp.png\" alt=\"App Store\">");
		html.append("                                </a>");
		html.append("                                <a href=\"https://play.google.com/store/apps/details?id=com.enciendeapp\">");
		html.append("                                    <img style=\"height: 70px; border:none; display:inline-block; height: auto; max-width: 100px; padding-left: 20px;\" src=\"http://i.imgur.com/c22eBtr.png\" alt=\"Google Play\">");
		html.append("                                </a>");
		html.append("                            </div>");
		html.append("                        </td>");
		html.append("                    </tr>");
		html.append("                </tbody>");
		html.append("            </table>");
		html.append("        </div>");
		html.append("    </body>");
		html.append("</html>");
		return html.toString();
	}
	
}
