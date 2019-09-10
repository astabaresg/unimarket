package com.taheos.unimarket.entidades;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Persona
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Persona.LISTAR_TODOS, query = "select p from Persona p"),
		@NamedQuery(name = Persona.PERSONA_POR_CREDENCIALES, query = "select p from Persona p where p.correo =:correo and p.clave = :clave"),
		@NamedQuery(name = Persona.BUSCAR_PERSONA_POR_CORREO, query = "select p from Persona p where p.correo =:correo")})
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	
	/**
	 * constante que representa la consulta PersonaPorCredenciales
	 */
	public static final String PERSONA_POR_CREDENCIALES = "PersonaPorCredenciales";

	/**
	 * constante que representa la consulta listarLasPersonas
	 */
	public static final String LISTAR_TODOS = "listarLasPersonas";


	/**
	 * constante que representa la consulta PersonaPorCorreo
	 */
	public static final String BUSCAR_PERSONA_POR_CORREO = "PersonaPorCorreo";


	/**
	 * identificacion de la persona
	 */
	@Id
	@Column(name = "identificacion", length = 20)
	private String id;

	/**
	 * nombre de la persona
	 */
	@Column(length = 40, updatable = true)
	private String nombre;
	/**
	 * correo de la persona
	 */
	@Column(length = 40, updatable = true)
	private String correo;
	/**
	 * clave de ingreso de la persona
	 */
	@Column(length = 15, updatable = true, unique = false)
	private String clave;
	/**
	 * cargo de la persona
	 */
	@Column(length = 15, updatable = false)
	private String rol;

	public Persona() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
