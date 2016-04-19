package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the GRUPO_USUARIO database table.
 * 
 */
@Entity
@Table(name="GRUPO_USUARIO")
@NamedQuery(name="GrupoUsuario.findAll", query="SELECT g FROM GrupoUsuario g")
public class GrupoUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@JsonIgnore
	private GrupoUsuarioPK id;

	private String rol;

	private String token;

	//bi-directional many-to-one association to Grupo
	@JoinColumn(insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.EAGER)
	private Grupo grupo;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JsonIgnore
	@JoinColumn(insertable=false, updatable=false)
	private Usuario usuario;

	public GrupoUsuario() {
	}

	public GrupoUsuarioPK getId() {
		return this.id;
	}

	public void setId(GrupoUsuarioPK id) {
		this.id = id;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoUsuario other = (GrupoUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}