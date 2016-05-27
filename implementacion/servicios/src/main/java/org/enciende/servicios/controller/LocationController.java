package org.enciende.servicios.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.enciende.business.LocationBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.GrupoUsuario;
import org.enciende.model.GrupoUsuarioPK;
import org.enciende.model.LocationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/location")
public class LocationController {
	@Autowired
	private LocationBusiness locationBusiness;
	
	@ResponseBody
	@RequestMapping(value = "/lista/{idGrupo}/{precision}", method = RequestMethod.GET)
	public Map<String,Object> getNoticias(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("idGrupo") Integer idGrupo,@PathVariable("precision") double precision) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("locations", locationBusiness.getLocationsByGrupo(idGrupo,precision));
		
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public Map<String,Object> guardarUbicacion(HttpServletResponse response, Model model, HttpServletRequest request, 
			@RequestBody LocationForm form) {
		
		if(form!=null&&form.getLocations()!=null&&form.getLocations().size()>0){
			form.getLocations().get(0).setGrupoUsuario(new GrupoUsuario());
			form.getLocations().get(0).getGrupoUsuario().setId(new GrupoUsuarioPK());
			form.getLocations().get(0).getGrupoUsuario().getId().setGrupoIdGrupo(form.getGrupoId());
			form.getLocations().get(0).getGrupoUsuario().getId().setUsuarioIdUsuario(form.getUsuarioId());
		}
		
		Map<String,Object> respuesta = new HashMap<String,Object>();
		
		try{
			locationBusiness.guardarLocations(form.getLocations());
			respuesta.put("success", true);
		}catch(BusinessException e){
			respuesta.put("success", false);
			respuesta.put("errorMessage", e.getMessage());
			respuesta.put("errorCode", e.getStatus());
		}
		
		return respuesta;
	}
}

class LocationForm{
	private List<LocationLog> locations;
	private Integer grupoId;
	private Integer usuarioId;
	
	public List<LocationLog> getLocations() {
		return locations;
	}
	public void setLocations(List<LocationLog> locations) {
		this.locations = locations;
	}
	public Integer getGrupoId() {
		return grupoId;
	}
	public void setGrupoId(Integer grupoId) {
		this.grupoId = grupoId;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
}
