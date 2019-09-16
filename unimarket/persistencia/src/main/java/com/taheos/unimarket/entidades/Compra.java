package com.taheos.unimarket.entidades;

import java.io.Serializable;
import java.lang.Long;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Compra
 *
 */
@Entity

public class Compra implements Serializable {

	/**
	 * Identificador unico de la compra
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id_compra;
	
	/**
	 * Relacion de muchos productos en una compra
	 */
	@OneToMany(mappedBy = "compra")
	private List<Producto> productos;
	
	@ManyToOne
	private Usuario usuario;
	private static final long serialVersionUID = 1L;

	public Compra() {
		super();
	}   
	public Long getId_compra() {
		return this.id_compra;
	}

	public void setId_compra(Long id_compra) {
		this.id_compra = id_compra;
	}
   
}
