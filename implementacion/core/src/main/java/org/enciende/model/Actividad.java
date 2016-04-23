package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the ACTIVIDAD database table.
 * 
 */
@Entity
@Table(name="ACTIVIDAD")
@NamedQuery(name="Actividad.findAll", query="SELECT a FROM Actividad a")
public class Actividad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ACTVIDAD")
	private Integer idActividad;

	@Column(name="COMO_LLEGAR")
	private String comoLlegar;

	@Lob
	private String instrucciones;

	private String latitud;

	private String longitudad;

	private String nombre;

	@Column(name="PISTA_LUGAR")
	private String pistaLugar;

	//bi-directional many-to-one association to ActividadGrupo
	@OneToMany(mappedBy="actividad")
	@JsonIgnore
	private List<ActividadGrupo> actividadGrupos;

	//bi-directional many-to-one association to Rally
	@ManyToOne
	private Rally rally;

	public Actividad() {
	}

	public Integer getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public String getComoLlegar() {
		return this.comoLlegar;
	}

	public void setComoLlegar(String comoLlegar) {
		this.comoLlegar = comoLlegar;
	}

	public String getInstrucciones() {
		return this.instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getLatitud() {
		return this.latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitudad() {
		return this.longitudad;
	}

	public void setLongitudad(String longitudad) {
		this.longitudad = longitudad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPistaLugar() {
		return this.pistaLugar;
	}

	public void setPistaLugar(String pistaLugar) {
		this.pistaLugar = pistaLugar;
	}

	public List<ActividadGrupo> getActividadGrupos() {
		return this.actividadGrupos;
	}

	public void setActividadGrupos(List<ActividadGrupo> actividadGrupos) {
		this.actividadGrupos = actividadGrupos;
	}

	public ActividadGrupo addActividadGrupo(ActividadGrupo actividadGrupo) {
		getActividadGrupos().add(actividadGrupo);
		actividadGrupo.setActividad(this);

		return actividadGrupo;
	}

	public ActividadGrupo removeActividadGrupo(ActividadGrupo actividadGrupo) {
		getActividadGrupos().remove(actividadGrupo);
		actividadGrupo.setActividad(null);

		return actividadGrupo;
	}

	public Rally getRally() {
		return this.rally;
	}

	public void setRally(Rally rally) {
		this.rally = rally;
	}

}