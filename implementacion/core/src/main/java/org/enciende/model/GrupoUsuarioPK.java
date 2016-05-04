package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GRUPO_USUARIO database table.
 * 
 */
@Embeddable
public class GrupoUsuarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="GRUPO_ID_GRUPO")
	private Integer grupoIdGrupo;

	@Column(name="USUARIO_ID_USUARIO")
	private Integer usuarioIdUsuario;

	public GrupoUsuarioPK() {
	}
	public Integer getGrupoIdGrupo() {
		return this.grupoIdGrupo;
	}
	public void setGrupoIdGrupo(Integer grupoIdGrupo) {
		this.grupoIdGrupo = grupoIdGrupo;
	}
	public Integer getUsuarioIdUsuario() {
		return this.usuarioIdUsuario;
	}
	public void setUsuarioIdUsuario(Integer usuarioIdUsuario) {
		this.usuarioIdUsuario = usuarioIdUsuario;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupoIdGrupo == null) ? 0 : grupoIdGrupo.hashCode());
		result = prime * result + ((usuarioIdUsuario == null) ? 0 : usuarioIdUsuario.hashCode());
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
		GrupoUsuarioPK other = (GrupoUsuarioPK) obj;
		if (grupoIdGrupo == null) {
			if (other.grupoIdGrupo != null)
				return false;
		} else if (!grupoIdGrupo.equals(other.grupoIdGrupo))
			return false;
		if (usuarioIdUsuario == null) {
			if (other.usuarioIdUsuario != null)
				return false;
		} else if (!usuarioIdUsuario.equals(other.usuarioIdUsuario))
			return false;
		return true;
	}
}