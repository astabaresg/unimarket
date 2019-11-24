package com.taheos.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.taheos.ejbs.UsuarioEJB;
import com.taheos.unimarket.entidades.Comentario;
import com.taheos.unimarket.entidades.Compra;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.util.Util;

@FacesConfig(version = Version.JSF_2_3)
@Named("usuarioProductoBean")
@ApplicationScoped
public class UsuarioProductoBean {

	@EJB 
	private UsuarioEJB usuarioEJB;
	
	//Datos lista Usuario
	private Usuario usuarioIni;
	@NotNull
	private Producto productoSelecionado;
	private String seleccion;
	@Inject @ManagedProperty(value = "#{seguridadBean.usuario}")
	private Persona usuario;
	
	private List<Producto> productos;
	private List<Compra> compras; 
	private boolean esProducto;
	private Compra compraSeleccionada;
	@PostConstruct
	private void init() {
		usuarioIni=(Usuario)usuario;
		productos = usuarioIni.getProductos();
		compras= usuarioEJB.ListarCompras(usuarioIni.getId());
		esProducto=true;
		
	}
	
	
	
	public String eliminarProductoUsuario() {

		try {
			usuarioEJB.eliminarProductoVenta(usuarioIni.getId(),productoSelecionado.getId());

			Util.mostrarMensaje("Exito", "Eliminar Existoso");

			productos = usuarioEJB.listarProductos();
			return "/producto/productos";
		} catch (Exception e) {
			Util.mostrarMensaje(e.getMessage(), e.getMessage());
			return null;
		}

	}
	
	public void listarProducos() {
		if(seleccion.equals("1")) {
			esProducto=true;
		}else if (seleccion.equals("2")) {
			esProducto=false;
		}
	}

	public UsuarioEJB getUsuarioEJB() {
		return usuarioEJB;
	}



	public void setUsuarioEJB(UsuarioEJB usuarioEJB) {
		this.usuarioEJB = usuarioEJB;
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



	public Persona getUsuario() {
		return usuario;
	}



	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}



	public List<Producto> getProductos() {
		return productos;
	}



	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}



	public String getSeleccion() {
		return seleccion;
	}



	public void setSeleccion(String seleccion) {
		this.seleccion = seleccion;
	}



	public boolean isEsProducto() {
		return esProducto;
	}



	public void setEsProducto(boolean esProducto) {
		this.esProducto = esProducto;
	}



	public List<Compra> getCompras() {
		return compras;
	}



	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}



	public Compra getCompraSeleccionada() {
		return compraSeleccionada;
	}



	public void setCompraSeleccionada(Compra compraSeleccionada) {
		this.compraSeleccionada = compraSeleccionada;
	}
	
	
	
}
