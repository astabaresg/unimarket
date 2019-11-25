package com.taheos.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;


import com.taheos.ejbs.UsuarioEJB;
import com.taheos.unimarket.entidades.DetalleCompra;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.util.Util;

@FacesConfig(version = Version.JSF_2_3)
@Named("compraBean")
@ApplicationScoped
public class CompraBean {

	/**
	 * Numero de productos disponibles
	 */
	private int cantidad;
	@EJB
	private UsuarioEJB usuarioEJB;

	@Inject
	@ManagedProperty(value = "#{seguridadBean.usuario}")
	private Persona usuario;

	private Producto producto;
	
	private DetalleCompra detalleCompra;
	
	private List<DetalleCompra> detalles;



	/**
	 * Es el usuario que inicio sesion
	 */
	private Usuario usuarioIni;
	/**
	 * Producto que esta seleccionado en la pagina
	 */
	@NotNull
	private Producto productoSelecionado;

	public String registrar() {

		try {
			DetalleCompra d = new DetalleCompra();
			d.setCantidad(1);
			d.setProducto(productoSelecionado);
			d.setPrecioCompra(productoSelecionado.getPrecio());
			d.setPrecioCompra(d.totalDetalleCompra());
			usuarioEJB.registrarDetalleCompra(d);
			Util.mostrarMensaje("Exito", "Registro Existoso");
			detalles = usuarioEJB.listarDetalleCompra();
			return "Producto agregado al carrito";
		} catch (Exception e) {
			Util.mostrarMensaje(e.getMessage(), e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Eliminamos el producto de la tabla de productos
	 * 
	 * @return recargamos la pagina
	 */
	public String eliminar() {

		try {
			usuarioEJB.eliminarDetalleCompra(detalleCompra.getId_detalleCompra() + "");

			Util.mostrarMensaje("Exito", "Eliminar Existoso");

			detalles = usuarioEJB.listarDetalleCompra();
			return "/compras/detalles";
		} catch (Exception e) {
			Util.mostrarMensaje(e.getMessage(), e.getMessage());
			return null;
		}

	}

	/**
	 * Eliminamos el producto de la lista de el usuario
	 * 
	 * @return
	 */
	public String eliminarProductoUsuario() {

		try {
			usuarioEJB.eliminarProductoVenta(usuarioIni.getId(), productoSelecionado.getId());

			Util.mostrarMensaje("Exito", "Eliminar Existoso");

			detalles = usuarioEJB.listarDetalleCompra();
			return "/producto/productos";
		} catch (Exception e) {
			Util.mostrarMensaje(e.getMessage(), e.getMessage());
			return null;
		}

	}

	@PostConstruct
	private void init() {
		detalles = usuarioEJB.listarDetalleCompra();
		usuarioIni = (Usuario) usuario;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Persona getUsuario() {
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

	public Usuario getUsuarioIni() {
		return usuarioIni;
	}

	public void setUsuarioIni(Usuario usuarioIni) {
		this.usuarioIni = usuarioIni;
	}

	public Producto getProductoSelecionado() {
		return productoSelecionado;
	}

	public void setProductoSelecionado(Producto productoSelecionado) {
		this.productoSelecionado = productoSelecionado;
	}

	public DetalleCompra getDetalleCompra() {
		return detalleCompra;
	}

	public void setDetalleCompra(DetalleCompra detalleCompra) {
		this.detalleCompra = detalleCompra;
	}

	public List<DetalleCompra> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCompra> detalles) {
		this.detalles = detalles;
	}
	
	
	

}
