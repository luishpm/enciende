package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the RALLY database table.
 * 
 */
@Entity
@NamedQuery(name="Rally.findAll", query="SELECT r FROM Rally r")
@Table(name="RALLY")
public class Rally implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_RALLY")
	private Integer idRally;

	private String descripcion;

	private int estatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

	private String nombre;

	//bi-directional many-to-one association to Grupo
	@OneToMany(mappedBy="rally",fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Grupo> grupos;

	//bi-directional many-to-one association to Noticia
	@OneToMany(mappedBy="rally")
	@JsonIgnore
	private List<Noticia> noticias;
	
	//bi-directional many-to-one association to Contacto
	@OneToMany(mappedBy="rally",fetch=FetchType.EAGER)
	private List<Contacto> contactos;

	public Rally() {
	}

	public Integer getIdRally() {
		return this.idRally;
	}

	public void setIdRally(Integer idRally) {
		this.idRally = idRally;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstatus() {
		return this.estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Grupo> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Grupo addGrupo(Grupo grupo) {
		getGrupos().add(grupo);
		grupo.setRally(this);

		return grupo;
	}

	public Grupo removeGrupo(Grupo grupo) {
		getGrupos().remove(grupo);
		grupo.setRally(null);

		return grupo;
	}

	public List<Noticia> getNoticias() {
		return this.noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Noticia addNoticia(Noticia noticia) {
		getNoticias().add(noticia);
		noticia.setRally(this);

		return noticia;
	}

	public Noticia removeNoticia(Noticia noticia) {
		getNoticias().remove(noticia);
		noticia.setRally(null);

		return noticia;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	

}