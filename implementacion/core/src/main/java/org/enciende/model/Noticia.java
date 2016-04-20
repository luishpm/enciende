package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the NOTICIA database table.
 * 
 */
@Entity
@NamedQuery(name="Noticia.findAll", query="SELECT n FROM Noticia n")
@Table(name="NOTICIA")
public class Noticia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_NOTICIA")
	private Integer idNoticia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_NOTICIA")
	private Date fechaNoticia;

	@Lob
	private String noticia;

	private String resumen;

	private String titulo;

	@Column(name="URL_IMAGEN")
	private String urlImagen;

	//bi-directional many-to-one association to Rally
	@ManyToOne
	@JoinColumn(name="ID_RALLY")
	private Rally rally;

	//bi-directional many-to-one association to Galeria
	@ManyToOne
	@JoinColumn(name="ID_GALERIA")
	private Galeria galeria;

	public Noticia() {
	}

	public int getIdNoticia() {
		return this.idNoticia;
	}

	public void setIdNoticia(int idNoticia) {
		this.idNoticia = idNoticia;
	}

	public Date getFechaNoticia() {
		return this.fechaNoticia;
	}

	public void setFechaNoticia(Date fechaNoticia) {
		this.fechaNoticia = fechaNoticia;
	}

	public String getNoticia() {
		return this.noticia;
	}

	public void setNoticia(String noticia) {
		this.noticia = noticia;
	}

	public String getResumen() {
		return this.resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrlImagen() {
		return this.urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public Rally getRally() {
		return this.rally;
	}

	public void setRally(Rally rally) {
		this.rally = rally;
	}

	public Galeria getGaleria() {
		return this.galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idNoticia;
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
		Noticia other = (Noticia) obj;
		if (idNoticia != other.idNoticia)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Noticia [idNoticia=" + idNoticia + ", fechaNoticia=" + fechaNoticia + ", titulo=" + titulo + "]";
	}
	
	

}