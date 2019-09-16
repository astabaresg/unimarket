package com.taheos.unimarket.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Favoritos
 *
 */
@Entity

public class Favorito implements Serializable {

	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador unico de la compra
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id_favoritos;
	
	/**
	 * Usuario que tiene una lista de favoritos
	 */
	@ManyToOne
	private Usuario usuario;
	
	/**
	 * Lista de productos en favoritos
	 */
	@OneToMany(mappedBy = "favorito")
	private List<Producto> productos;
	public Favorito() {
		super();
	}
	public Long getId_favoritos() {
		return id_favoritos;
	}
	public void setId_favoritos(Long id_favoritos) {
		this.id_favoritos = id_favoritos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_favoritos == null) ? 0 : id_favoritos.hashCode());
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
		Favorito other = (Favorito) obj;
		if (id_favoritos == null) {
			if (other.id_favoritos != null)
				return false;
		} else if (!id_favoritos.equals(other.id_favoritos))
			return false;
		return true;
	}
   
	
}
