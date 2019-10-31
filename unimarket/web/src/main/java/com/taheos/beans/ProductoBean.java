package com.taheos.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import com.taheos.ejbs.UsuarioEJB;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Disponibilidad;
import com.taheos.util.Util;


@FacesConfig(version = Version.JSF_2_3)
@Named("productoBean")
@ApplicationScoped
public class ProductoBean {

	/**
	 * Codigo del producto
	 */
	private String codigo;
	/**
	 * Nombre del producto
	 */
	private String nombre;

	/**
	 * Descripcion del producto
	 */
	private String descripcion;

	/**
	 * Precio del producto
	 */
	private double precio;

	/**
	 * Disponibilidad del producto
	 */
	private Disponibilidad disponibilidad;
	/**
	 * categoria del producto
	 */
	private String categoria;
	/**
	 * Fecha limite hasta donde se puede encontrar el producto 
	 */
	private Date fecha_limite;
	/**
	 * Numero de productos disponibles
	 */
	private int cantidad;
	@EJB 
	private UsuarioEJB usuarioEJB;
	
	private List<Usuario> usuarios;

	private List<Producto> productos;
		
	private Usuario usuario;
	
	private Producto producto;

	private UploadedFile imagen;
	
	public String registrar() {

		try {
			
			Producto p = new Producto();
			p.setUsuario(usuario);
			p.setPrecio(precio);
			p.setNombre(nombre);
			p.setFecha_limite(fecha_limite);
			p.setDisponibilidad(disponibilidad);
			p.setDescripcion(descripcion);
			p.setCategoria(usuarioEJB.devolverCategoria(categoria));
			p.setCantidad(cantidad);
			p.setImagen(new ArrayList<String>(imagen.getFileNames()));
			p.setCalificacion(5);
			usuarioEJB.registrarProducto(p);
			Util.mostrarMensaje("Exito", "Registro Existoso");
			
			return "/producto/productos";
		} catch (Exception e) {
			Util.mostrarMensaje("Error", "Error");
			return null;
		}

	}

	public String eliminar() {

		try {
			usuarioEJB.eliminarProducto(producto.getId() + "");

			Util.mostrarMensaje("Exito", "Eliminar Existoso");

			productos = usuarioEJB.listarProductos();
			return "/producto/productos";
		} catch (Exception e) {
			Util.mostrarMensaje(e.getMessage(), e.getMessage());
			return null;
		}

	}

	@PostConstruct
	private void init() {
		productos = usuarioEJB.listarProductos();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Disponibilidad getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Disponibilidad disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getFecha_limite() {
		return fecha_limite;
	}

	public void setFecha_limite(Date fecha_limite) {
		this.fecha_limite = fecha_limite;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public UploadedFile getImagen() {
		return imagen;
	}

	public void setImagen(UploadedFile imagen) {
		this.imagen = imagen;
	}
	
}
