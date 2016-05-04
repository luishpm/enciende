package org.enciende.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ACTIVIDAD_GRUPO database table.
 * 
 */
@Entity
@Table(name="ACTIVIDAD_GRUPO")
@NamedQuery(name="ActividadGrupo.findAll", query="SELECT a FROM ActividadGrupo a")
public class ActividadGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ActividadGrupoPK id;

	private String calificacion;

	private Integer estatus;
	
	private Integer orden;

	@Column(name="HORA_DESBLOQUEADA")
	private Date horaDesbloqueada;

	@Column(name="HORA_TERMINADA")
	private Date horaTerminada;

	//bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name="ID_ACTVIDAD",insertable=false, updatable=false)
	private Actividad actividad;

	//bi-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name="ID_GRUPO",insertable=false, updatable=false)
	private Grupo grupo;

	public ActividadGrupo() {
	}

	public ActividadGrupoPK getId() {
		return this.id;
	}

	public void setId(ActividadGrupoPK id) {
		this.id = id;
	}

	public String getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public Integer getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getHoraDesbloqueada() {
		return this.horaDesbloqueada;
	}

	public void setHoraDesbloqueada(Date horaDesbloqueada) {
		this.horaDesbloqueada = horaDesbloqueada;
	}

	public Date getHoraTerminada() {
		return this.horaTerminada;
	}

	public void setHoraTerminada(Date horaTerminada) {
		this.horaTerminada = horaTerminada;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	
}