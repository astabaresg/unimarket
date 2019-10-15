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
@NamedQueries({ @NamedQuery(name = Producto.LISTAR_TODOS, query = "select p from Producto p"),
	@NamedQuery(name = Producto.LISTAR_DISPONIBLES, query = "select p from Producto p where p.fecha_limite between :fecha_inicio and :fecha_fin"),
	@NamedQuery(name = Producto.LISTAR_DISPONIBLES_CATEGORIA, query = "select p from Producto p where p.categoria= :categoria"),
	@NamedQuery(name = Producto.BUSCAR_POR_ID, query = "select p from Producto p where p.id= :id")})
public class Producto implements Serializable {

	/**
	 * Referencia al query de listar todos los productos
	 */
	public static final String LISTAR_TODOS = "ListarTodosLosProductos";
	/**
	 * Referencia al query de listar los productos disponibles
	 */
	public static final String LISTAR_DISPONIBLES = "ListarTodosLosProductosDisponibles";
	/**
	 * Referencia al query de listar los productos disponibles por categoria
	 */
	public static final String LISTAR_DISPONIBLES_CATEGORIA = "ListarTodosLosProductosDisponiblesPorCategoria";
	/**
	 * Referencia al query de buscar un producto mediante su ID
	 */
	public static final String BUSCAR_POR_ID = "ListarProductoPorID";
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
	 * Muestra 
	 */
	@OneToMany(mappedBy = "producto")
	private List<DetalleCompra> detalleCompra;
	/**
	 * representa la imagen que se desea almacenar en la base de datos
	 */
	@Lob
	private byte[] imagen;

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
	

	
	/**
	 * Lista de favoritos donde se encuentra 
	 */
	@OneToOne(mappedBy = "producto")
	private Favorito favorito;
	
	/**
	 * Comentarios del producto
	 */
	@OneToMany(mappedBy = "producto")
	private List<Comentario> comentarios;
	
	@OneToMany(mappedBy = "producto")
	private List<Calificacion> calificaciones;
	/**
	 * Fecha limite del producto
	 */
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, updatable = true)
	private Date fecha_limite;
	/**
	 * cantidad que se tiene de el productom actual 
	 */
	private int cantidad;
	/**
	 * Calificacion del producto
	 */
	@Column(nullable = false, updatable = true, scale = 1)
	private double calificacion;
	
	/**
	 * Usuario que sube productos
	 */
	@ManyToOne
	private Usuario usuario;
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

	
	
	public Favorito getFavorito() {
		return favorito;
	}

	public void setFavorito(Favorito favorito) {
		this.favorito = favorito;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<DetalleCompra> getDetalleCompra() {
		return detalleCompra;
	}

	public void setDetalleCompra(List<DetalleCompra> detalleCompra) {
		this.detalleCompra = detalleCompra;
	}

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
