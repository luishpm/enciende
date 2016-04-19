package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the GALERIA database table.
 * 
 */
@Entity
@NamedQuery(name="Galeria.findAll", query="SELECT g FROM Galeria g")
public class Galeria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_GALERIA")
	private int idGaleria;

	private String descripciion;

	private String nombre;

	//bi-directional many-to-one association to GaleriaFoto
	@OneToMany(mappedBy="galeria")
	private List<GaleriaFoto> galeriaFotos;

	//bi-directional many-to-one association to Noticia
	@OneToMany(mappedBy="galeria")
	private List<Noticia> noticias;

	public Galeria() {
	}

	public int getIdGaleria() {
		return this.idGaleria;
	}

	public void setIdGaleria(int idGaleria) {
		this.idGaleria = idGaleria;
	}

	public String getDescripciion() {
		return this.descripciion;
	}

	public void setDescripciion(String descripciion) {
		this.descripciion = descripciion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<GaleriaFoto> getGaleriaFotos() {
		return this.galeriaFotos;
	}

	public void setGaleriaFotos(List<GaleriaFoto> galeriaFotos) {
		this.galeriaFotos = galeriaFotos;
	}

	public GaleriaFoto addGaleriaFoto(GaleriaFoto galeriaFoto) {
		getGaleriaFotos().add(galeriaFoto);
		galeriaFoto.setGaleria(this);

		return galeriaFoto;
	}

	public GaleriaFoto removeGaleriaFoto(GaleriaFoto galeriaFoto) {
		getGaleriaFotos().remove(galeriaFoto);
		galeriaFoto.setGaleria(null);

		return galeriaFoto;
	}

	public List<Noticia> getNoticias() {
		return this.noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Noticia addNoticia(Noticia noticia) {
		getNoticias().add(noticia);
		noticia.setGaleria(this);

		return noticia;
	}

	public Noticia removeNoticia(Noticia noticia) {
		getNoticias().remove(noticia);
		noticia.setGaleria(null);

		return noticia;
	}

}