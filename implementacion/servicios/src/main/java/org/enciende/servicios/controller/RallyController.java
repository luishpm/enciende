package org.enciende.servicios.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.enciende.business.RallyBusiness;
import org.enciende.exception.BusinessException;
import org.enciende.model.ActividadGrupo;
import org.enciende.model.Grupo;
import org.enciende.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "/rally")
public class RallyController {
	@Autowired
	private RallyBusiness rallyBusiness;
	
	@Autowired
    ServletContext context; 
	
	@ResponseBody
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public Map<String,Object> findAll(HttpServletResponse response, Model model, HttpServletRequest request) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("rallys", rallyBusiness.findAllRallys());
		
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/{rallyId}/getUltimaActividadByGrupo", method = RequestMethod.GET)
	public Map<String,Object> findAll(HttpServletResponse response, Model model, HttpServletRequest request,
			@PathVariable("rallyId") Integer rallyId) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("actividades", rallyBusiness.getUltimaActividadByGrupo(rallyId));
		
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
	@RequestMapping(value = "/{rallyId}/grupos/{grupoId}/integrantes/{token}", method = RequestMethod.GET)
	public Map<String,Object> findAllUsuariosByGrupoAndRallyId(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("rallyId") Integer rallyId, @PathVariable("grupoId") Integer grupoId, @PathVariable("token") String token) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		try {
			respuesta.put("grupos", rallyBusiness.findAllUsuariosByGrupoAndRallyId(rallyId, grupoId, token));
		} catch (BusinessException e) {
			respuesta.put("success", false);
			respuesta.put("errorMessage", e.getMessage());
			respuesta.put("errorCode", e.getStatus());
		}
		
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/grupo/{grupoId}", method = RequestMethod.GET)
	public Map<String,Object> getGrupo(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("grupoId") Integer grupoId) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("grupo", rallyBusiness.getGrupo(grupoId));
		
		return respuesta;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/{rallyId}/staff", method = RequestMethod.GET)
	public Map<String,Object> findAllStaffByRallyId(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("rallyId") Integer rallyId) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("grupos", rallyBusiness.findAllStaffByRallyId(rallyId));
		
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping(value = "/actividades/{grupoId}/", method = RequestMethod.GET)
	public Map<String,Object> findActividadesByIdGrupo(HttpServletResponse response, Model model, HttpServletRequest request, 
			@PathVariable("grupoId") Integer grupoId) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		respuesta.put("success", true);
		respuesta.put("actividades", rallyBusiness.findActividadesByIdGrupo(grupoId));
		
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
	
	@ResponseBody
	@RequestMapping(value = "/actividad/cambiar-estatus", method = RequestMethod.POST)
	public Map<String,Object> cambiarEstatus(HttpServletResponse response, Model model, HttpServletRequest request, 
			@RequestBody ActividadesEstatusForm form) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		try{
			respuesta.put("actividades", rallyBusiness.cambiarEstatus(form.getActividades(),form.getTokenStaff()));
			respuesta.put("success", true);
		}catch(BusinessException e){
			respuesta.put("success", false);
			respuesta.put("errorMessage", e.getMessage());
			respuesta.put("errorCode", e.getStatus());
			try{
				respuesta.put("actividades", rallyBusiness.findActividadesByIdGrupo(form.getActividades().get(0).getId().getIdGrupo()));
			}catch(Exception ex){
				e.printStackTrace();
			}
		}
		
		
		return respuesta;
	}
	
	@RequestMapping(value = "/subir-selfie", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> subirIdentificacion(MultipartHttpServletRequest request) {
		Map<String,Object> respuesta = new HashMap<String,Object>();
		String token = request.getParameter("token");
		String grupoId = request.getParameter("idGrupo");
		String actividadId = request.getParameter("idActividad");
		context.getRealPath("");
		
		if("ki$59%38IO#".equals(token)){
			MultipartFile mpf = null;
			Iterator<String> itr = request.getFileNames();
			
			while (itr.hasNext()) {
				mpf = request.getFile(itr.next());
				OutputStream output = null;
				
				try {
					String path = context.getRealPath("")+"/../imagenes";
					
					if(StringUtils.isNotBlank(System.getenv("OPENSHIFT_DATA_DIR"))){
						path = System.getenv("OPENSHIFT_DATA_DIR");
					}
					
					File file = new File(path+"/selfies/"+grupoId);
					file.mkdirs();
					output = new FileOutputStream(path+"/selfies/"+grupoId+"/"+actividadId+".jpg");
					IOUtils.copy(mpf.getInputStream(), output);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {mpf.getInputStream().close();} catch (IOException e) {e.printStackTrace();}
					try {output.close();} catch (IOException e) {e.printStackTrace();}
				}
			}
		}
		
		return respuesta;
	}
}

class ActividadesEstatusForm implements Serializable{
	private static final long serialVersionUID = -6948447700889178468L;
	
	private List<ActividadGrupo> actividades;
	private String tokenStaff;

	public List<ActividadGrupo> getActividades() {
		return actividades;
	}

	public void setActividades(List<ActividadGrupo> actividades) {
		this.actividades = actividades;
	}

	public String getTokenStaff() {
		return tokenStaff;
	}

	public void setTokenStaff(String tokenStaff) {
		this.tokenStaff = tokenStaff;
	}
}

