package org.enciende.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the CONTACTOS database table.
 * 
 */
@Entity
@Table(name="CONTACTOS")
@NamedQuery(name="Contacto.findAll", query="SELECT c FROM Contacto c")
public class Contacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CONTACTO")
	private int idContacto;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to Rally
	@ManyToOne
	@JoinColumn(name="ID_RALLY")
	@JsonIgnore
	private Rally rally;

	public Contacto() {
	}

	public int getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(int idContacto) {
		this.idContacto = idContacto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Rally getRally() {
		return this.rally;
	}

	public void setRally(Rally rally) {
		this.rally = rally;
	}

}