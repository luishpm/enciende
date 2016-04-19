package org.enciende.servicios.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.enciende.business.RallyBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.Grupo;
import org.enciende.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rally")
public class RallyController {
	@Autowired
	private RallyBusiness rallyBusiness;
	
	@ResponseBody
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public Map<String,Object> findAll(HttpServletResponse response, Model model, HttpServletRequest request) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("rallys", rallyBusiness.findAllRallys());
		
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/{rallyId}/grupos", method = RequestMethod.GET)
	public Map<String,Object> findAllGruposByRallyId(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("rallyId") Integer rallyId) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("grupos", rallyBusiness.findAllGruposByRallyId(rallyId));
		
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/grabarGrupos", method = RequestMethod.POST)
	public Map<String,Object> grabarGrupo(HttpServletResponse response, Model model, HttpServletRequest request, 
			@ModelAttribute("grupo") Grupo grupo) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		
		try{
			respuesta.put("success", true);
			respuesta.put("grupo", rallyBusiness.saveGrupo(grupo));
		}catch(BusinessException e){
			respuesta.put("success", false);
			respuesta.put("errorMessage", e.getMessage());
			respuesta.put("errorCode", e.getStatus());
		}
		
		
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/inscribir", method = RequestMethod.POST)
	public Map<String,Object> inscribir(HttpServletResponse response, Model model, HttpServletRequest request, 
			@ModelAttribute("usuario") Usuario usuario) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		
		try{
			respuesta.put("success", true);
			respuesta.put("grupo", rallyBusiness.inscribir(usuario));
		}catch(BusinessException e){
			respuesta.put("success", false);
			respuesta.put("errorMessage", e.getMessage());
			respuesta.put("errorCode", e.getStatus());
		}
		
		
		return respuesta;
	}
}
