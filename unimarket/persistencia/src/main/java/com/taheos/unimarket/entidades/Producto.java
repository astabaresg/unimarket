package com.taheos.unimarket.entidades;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Disponibilidad;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity

public class Producto implements Serializable {

	/**
	 * Id del producto, se genera automaticamente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id;
	/**
	 * Nombre del producto
	 */
	@Column(nullable = false, updatable = true)
	private String nombre;
	/**
	 * Descripcion del producto
	 */
	@Column(nullable = false, updatable = true)
	private String descripcion;
	/**
	 * Precio del producto
	 */
	@Column(updatable = true, nullable = false)
	private double precio;

	/**
	 * representa la imagen que se desea almacenar en la base de datos
	 */
	@Lob
	private byte[] imagen;

	/**
	 * Comentarios del productos
	 */
	@ElementCollection
	private List<String> comentarios;
	/**
	 * Disponible o no
	 */
	@Enumerated(EnumType.STRING)
	private Disponibilidad disponibilidad;
	/**
	 * Categoria del producto
	 */
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	@ManyToOne
	private Compra compra;
	/**
	 * Fecha limite del producto
	 */
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, updatable = true)
	private Date fecha_limite;

	/**
	 * Calificacion del producto
	 */
	@Column(nullable = false, updatable = true, scale = 1)
	private double calificacion;
	private static final long serialVersionUID = 1L;

	public Producto() {
		super();
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public List<String> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<String> comentarios) {
		this.comentarios = comentarios;
	}

	public void agregarComentario(String comentario) {
		this.comentarios.add(comentario);
	}

	public Disponibilidad getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Disponibilidad disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getFecha_limite() {
		return fecha_limite;
	}

	public void setFecha_limite(Date fecha_limite) {
		this.fecha_limite = fecha_limite;
	}

	public double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(double calificacion) {

		this.calificacion = calificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Producto other = (Producto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
