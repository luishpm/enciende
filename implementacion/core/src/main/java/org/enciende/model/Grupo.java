package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the GRUPO database table.
 * 
 */
@Entity
@NamedQuery(name="Grupo.findAll", query="SELECT g FROM Grupo g")
@Table(name="GRUPO")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GRUPO")
	private Integer idGrupo;

	private String nombre;

	//bi-directional many-to-one association to Rally
	@ManyToOne(fetch=FetchType.EAGER)
	private Rally rally;

	//bi-directional many-to-one association to GrupoUsuario
	@JsonIgnore
	@OneToMany(mappedBy="grupo")
	private List<GrupoUsuario> grupoUsuarios;

	public Grupo() {
	}

	public Integer getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Rally getRally() {
		return this.rally;
	}

	public void setRally(Rally rally) {
		this.rally = rally;
	}

	public List<GrupoUsuario> getGrupoUsuarios() {
		return this.grupoUsuarios;
	}

	public void setGrupoUsuarios(List<GrupoUsuario> grupoUsuarios) {
		this.grupoUsuarios = grupoUsuarios;
	}

	public GrupoUsuario addGrupoUsuario(GrupoUsuario grupoUsuario) {
		getGrupoUsuarios().add(grupoUsuario);
		grupoUsuario.setGrupo(this);

		return grupoUsuario;
	}

	public GrupoUsuario removeGrupoUsuario(GrupoUsuario grupoUsuario) {
		getGrupoUsuarios().remove(grupoUsuario);
		grupoUsuario.setGrupo(null);

		return grupoUsuario;
	}

}