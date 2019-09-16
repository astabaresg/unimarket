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
	
	/**
	 * Usuario que realiza la compra
	 */
	@ManyToOne
	private Usuario usuario;
	
	/**
	 * Valor total de la compra
	 */
	@Column(updatable = true)
	private double total_compra;
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
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public double getTotal_compra() {
		return total_compra;
	}
	public void setTotal_compra(double total_compra) {
		this.total_compra = total_compra;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_compra == null) ? 0 : id_compra.hashCode());
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
		Compra other = (Compra) obj;
		if (id_compra == null) {
			if (other.id_compra != null)
				return false;
		} else if (!id_compra.equals(other.id_compra))
			return false;
		return true;
	}
   
}
