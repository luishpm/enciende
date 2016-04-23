package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;
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

	@Id
	@Column(name="ID_LOCATION_LOG")
	private int idLocationLog;

	@Temporal(TemporalType.TIMESTAMP)
	private Date hora;

	private String latitud;

	private String LOCATION_LOGcol;

	private String longitud;

	@Column(name="`PRECISION`")
	private String precision;

	//bi-directional many-to-one association to GrupoUsuario
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="ID_GRUPO", referencedColumnName="GRUPO_ID_GRUPO"),
		@JoinColumn(name="ID_USUARIO", referencedColumnName="USUARIO_ID_USUARIO")
		})
	private GrupoUsuario grupoUsuario;

	public LocationLog() {
	}

	public int getIdLocationLog() {
		return this.idLocationLog;
	}

	public void setIdLocationLog(int idLocationLog) {
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

	public String getPrecision() {
		return this.precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public GrupoUsuario getGrupoUsuario() {
		return this.grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

}