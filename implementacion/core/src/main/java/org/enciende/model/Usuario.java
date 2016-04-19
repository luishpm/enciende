package org.enciende.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_USUARIO")
	private int idUsuario;

	private String email;

	private String genero;

	private String nombre;

	@Column(name="TALLA_PLAYERA")
	private String tallaPlayera;

	@Column(name="ID_FACEBOOK")
	private String idFacebook;

	//bi-directional many-to-one association to GrupoUsuario
	@OneToMany(mappedBy="usuario",fetch=FetchType.EAGER)
	private List<GrupoUsuario> grupoUsuarios;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTallaPlayera() {
		return this.tallaPlayera;
	}

	public void setTallaPlayera(String tallaPlayera) {
		this.tallaPlayera = tallaPlayera;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}

	public List<GrupoUsuario> getGrupoUsuarios() {
		return this.grupoUsuarios;
	}

	public void setGrupoUsuarios(List<GrupoUsuario> grupoUsuarios) {
		this.grupoUsuarios = grupoUsuarios;
	}

	public GrupoUsuario addGrupoUsuario(GrupoUsuario grupoUsuario) {
		getGrupoUsuarios().add(grupoUsuario);
		grupoUsuario.setUsuario(this);

		return grupoUsuario;
	}

	public GrupoUsuario removeGrupoUsuario(GrupoUsuario grupoUsuario) {
		getGrupoUsuarios().remove(grupoUsuario);
		grupoUsuario.setUsuario(null);

		return grupoUsuario;
	}

}