package com.taheos.unimarket.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Calificacion
 *
 */
@Entity

public class Calificacion implements Serializable {

	
	private static final long serialVersionUID = 1L;


	/**
	 * Identificador unico de la compra
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id_calificacion;
	
	/**
	 * Usuario que hace una calificacion
	 */
	@ManyToOne
	private Usuario usuario;
	
	/**
	 * Producto puntuado
	 */
	@ManyToOne
	private Producto producto;
	
	/**
	 * Puntuacion del producto
	 */
	private double puntaje;
	public Calificacion() {
		super();
	}
	public Long getId_calificacion() {
		return id_calificacion;
	}
	public void setId_calificacion(Long id_calificacion) {
		this.id_calificacion = id_calificacion;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public double getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_calificacion == null) ? 0 : id_calificacion.hashCode());
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
		Calificacion other = (Calificacion) obj;
		if (id_calificacion == null) {
			if (other.id_calificacion != null)
				return false;
		} else if (!id_calificacion.equals(other.id_calificacion))
			return false;
		return true;
	}
   
}
