package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the LOCATION_LOG database table.
 * 
 */
@Entity
@Table(name="LOCATION_LOG")
@NamedQuery(name="LocationLog.findAll", query="SELECT l FROM LocationLog l")
public class LocationLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_LOCATION_LOG")
	private Integer idLocationLog;

	@Temporal(TemporalType.TIMESTAMP)
	private Date hora;

	private String latitud;

	private String LOCATION_LOGcol;

	private String longitud;

	@Column(name="`PRECISION`")
	private Double precision;

	//bi-directional many-to-one association to GrupoUsuario
	@ManyToOne
	@JsonIgnore
	@JoinColumns({
		@JoinColumn(name="ID_GRUPO", referencedColumnName="GRUPO_ID_GRUPO"),
		@JoinColumn(name="ID_USUARIO", referencedColumnName="USUARIO_ID_USUARIO")
		})
	private GrupoUsuario grupoUsuario;

	public LocationLog() {
	}

	public Integer getIdLocationLog() {
		return this.idLocationLog;
	}

	public void setIdLocationLog(Integer idLocationLog) {
		this.idLocationLog = idLocationLog;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getLatitud() {
		return this.latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLOCATION_LOGcol() {
		return this.LOCATION_LOGcol;
	}

	public void setLOCATION_LOGcol(String LOCATION_LOGcol) {
		this.LOCATION_LOGcol = LOCATION_LOGcol;
	}

	public String getLongitud() {
		return this.longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Double getPrecision() {
		return this.precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public GrupoUsuario getGrupoUsuario() {
		return this.grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLocationLog == null) ? 0 : idLocationLog.hashCode());
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
		LocationLog other = (LocationLog) obj;
		if (idLocationLog == null) {
			if (other.idLocationLog != null)
				return false;
		} else if (!idLocationLog.equals(other.idLocationLog))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocationLog [idLocationLog=" + idLocationLog + ", latitud=" + latitud + ", longitud=" + longitud
				+ ", precision=" + precision + ", grupoUsuario=" + grupoUsuario + "]";
	}
	

}