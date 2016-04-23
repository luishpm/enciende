package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACTIVIDAD_GRUPO database table.
 * 
 */
@Embeddable
public class ActividadGrupoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_ACTVIDAD")
	private Integer idActividad;

	@Column(name="ID_GRUPO")
	private Integer idGrupo;

	public ActividadGrupoPK() {
	}

	public Integer getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idActividad == null) ? 0 : idActividad.hashCode());
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		ActividadGrupoPK other = (ActividadGrupoPK) obj;
		if (idActividad == null) {
			if (other.idActividad != null)
				return false;
		} else if (!idActividad.equals(other.idActividad))
			return false;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}
}