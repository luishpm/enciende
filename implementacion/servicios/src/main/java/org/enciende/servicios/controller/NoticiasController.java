package org.enciende.servicios.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.enciende.business.NoticiaBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/noticias")
public class NoticiasController {
	@Autowired
	private NoticiaBusiness noticiaBusiness;
	
	@ResponseBody
	@RequestMapping(value = "/lista/{inicio}/{cantidad}", method = RequestMethod.GET)
	public Map<String,Object> getNoticias(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("inicio") int inicio, @PathVariable("inicio") int cantidad) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("noticias", noticiaBusiness.findAllNoticias());
		
		return respuesta;
		
	}
}
