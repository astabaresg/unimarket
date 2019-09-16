package com.taheos.unimarket.entidades;

import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.enums.Rol;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity

public class Usuario extends Persona implements Serializable {

	/**
	 * Rol del usuario
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@OneToMany(mappedBy = "usuario")
	private List<Compra> compras;
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}   
	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
   
}
