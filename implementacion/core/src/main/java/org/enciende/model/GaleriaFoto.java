package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the GALERIA_FOTOS database table.
 * 
 */
@Entity
@Table(name="GALERIA_FOTOS")
@NamedQuery(name="GaleriaFoto.findAll", query="SELECT g FROM GaleriaFoto g")
public class GaleriaFoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PHOTO")
	private int idPhoto;

	private String descripcion;

	private String url;

	//bi-directional many-to-one association to Galeria
	@ManyToOne
	@JoinColumn(name="ID_GALERIA")
	private Galeria galeria;

	public GaleriaFoto() {
	}

	public int getIdPhoto() {
		return this.idPhoto;
	}

	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Galeria getGaleria() {
		return this.galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}

}