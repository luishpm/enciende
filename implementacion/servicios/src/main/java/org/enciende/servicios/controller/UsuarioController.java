package org.enciende.servicios.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.enciende.business.UsuarioBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	@ResponseBody
	@RequestMapping(value = "/get/{token}", method = RequestMethod.GET)
	public Map<String,Object> getByToken(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("token") String token) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		Usuario usuario = usuarioBusiness.getUsuarioByTokenFacebook(token);
		
		if(usuario!=null){
			respuesta.put("success", true);
			respuesta.put("usuario", usuario);
		}else{
			respuesta.put("success", false);
		}
		
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/registrarFacebook/{tokenEvento}/{tokenFacebook}", method = RequestMethod.GET)
	public Map<String,Object> getByToken(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("tokenEvento") String tokenEvento,@PathVariable("tokenFacebook") String tokenFacebook) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		
		try{
			Usuario usuario = usuarioBusiness.registrarFacebook(tokenFacebook, tokenEvento);
			respuesta.put("success", true);
			respuesta.put("usuario", usuario);
		}catch(BusinessException e){
			respuesta.put("success", false);
			respuesta.put("errorMessage", e.getMessage());
			respuesta.put("errorCode", e.getStatus());
		}
		
		
		return respuesta;
	}
}
