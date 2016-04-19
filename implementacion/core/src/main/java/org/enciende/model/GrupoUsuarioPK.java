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
	private int grupoIdGrupo;

	@Column(name="USUARIO_ID_USUARIO")
	private int usuarioIdUsuario;

	public GrupoUsuarioPK() {
	}
	public int getGrupoIdGrupo() {
		return this.grupoIdGrupo;
	}
	public void setGrupoIdGrupo(int grupoIdGrupo) {
		this.grupoIdGrupo = grupoIdGrupo;
	}
	public int getUsuarioIdUsuario() {
		return this.usuarioIdUsuario;
	}
	public void setUsuarioIdUsuario(int usuarioIdUsuario) {
		this.usuarioIdUsuario = usuarioIdUsuario;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GrupoUsuarioPK)) {
			return false;
		}
		GrupoUsuarioPK castOther = (GrupoUsuarioPK)other;
		return 
			(this.grupoIdGrupo == castOther.grupoIdGrupo)
			&& (this.usuarioIdUsuario == castOther.usuarioIdUsuario);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.grupoIdGrupo;
		hash = hash * prime + this.usuarioIdUsuario;
		
		return hash;
	}
}