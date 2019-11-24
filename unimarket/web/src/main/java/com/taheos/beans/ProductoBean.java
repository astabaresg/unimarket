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
import javax.validation.constraints.NotNull;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import com.taheos.ejbs.UsuarioEJB;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;
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
		
	@Inject @ManagedProperty(value = "#{seguridadBean.usuario}")
	private Persona usuario;
		
	private Producto producto;

	private UploadedFile imagen;
	
	//Datos filtro
	private boolean busqueda1,busqueda2,busqueda3;
	@NotNull(message = "Debe seleccionar un tipo de producto")
	private String dato1;
    
	@NotNull(message = "Debe tener un precio inicial")
	private String dato2;
	
	@NotNull(message = "Debe tener un precio final")
	private String dato3;
	
	private Usuario usuarioIni;
	
	@NotNull
	private Producto productoSelecionado;
	
	
	/**
	 * verificamos cual es el filtro que queremos
	 */
	private String seleccionFiltro;
	public String registrar() {

		try {
			
			String img = "";
			ArrayList<String> imgs = new ArrayList<String>();
			imgs.add(img);
			Producto p = new Producto();
			p.setUsuario((Usuario)usuario);
			p.setPrecio(precio);
			p.setNombre(nombre);
			p.setFecha_limite(fecha_limite);
			p.setDisponibilidad(disponibilidad);
			p.setDescripcion(descripcion);
			p.setCategoria(usuarioEJB.devolverCategoria(categoria));
			p.setCantidad(cantidad);
			p.setImagen(imgs);
			p.setCalificacion(5);
			usuarioEJB.registrarProducto(p);
			Util.mostrarMensaje("Exito", "Registro Existoso");
			productos = usuarioEJB.listarProductos();
			return "/producto/productos";
		} catch (Exception e) {
			Util.mostrarMensaje(e.getMessage(), e.getMessage());
			e.printStackTrace();
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
	/**
	 * mostramos los cambios efectuados
	 */
	public void cambiarVista() {
		if(busqueda1) {
			 productos=usuarioEJB.listarProductos();
			}else if(busqueda2) {
			 productos=usuarioEJB.ListarProductosPorCategoria(devolverCategoria(dato1));
			}else if(busqueda3) {
			 productos=usuarioEJB.ListarProductosPorPrecio(Double.parseDouble(dato2), Double.parseDouble(dato3), Disponibilidad.DISPONIBLE);
			}
	}
	/**
	 * cambiamos que lista se podra ver
	 * 
	 */
	public void cambiarLista() {
		if(seleccionFiltro.equals("1")) {
		 busqueda1=true;
		 busqueda2=false;
		 busqueda3=false;
		}else if(seleccionFiltro.equals("2")) {
		 busqueda1=false;
		 busqueda2=true;
		 busqueda3=false;
		}else if(seleccionFiltro.equals("3")) {
		 busqueda1=false;
		 busqueda2=false;
	     busqueda3=true;
		}
	}
	
	public Categoria devolverCategoria(String valor) {
		if(valor.equals("TECNOLOGIA")) {
			return Categoria.TECNOLOGIA;
		}else if(valor.equals("DEPORTE")) {
			return Categoria.DEPORTE;
		}else if(valor.equals("MODA")) {
			return Categoria.MODA;
		}else if(valor.equals("LIBROS")) {
			return Categoria.LIBROS;
		}else if(valor.equals("JOYAS")) {
			return Categoria.JOYAS;
		}else return null;
	}

	@PostConstruct
	private void init() {
		productos = usuarioEJB.listarProductos();
		//usuarioIni=(Usuario)usuario;
		busqueda1=false;
		busqueda2=false;
		busqueda3=false;
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

	public Persona getUsuario() {
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

	public boolean isBusqueda1() {
		return busqueda1;
	}

	public void setBusqueda1(boolean busqueda1) {
		this.busqueda1 = busqueda1;
	}

	public boolean isBusqueda2() {
		return busqueda2;
	}

	public void setBusqueda2(boolean busqueda2) {
		this.busqueda2 = busqueda2;
	}

	public boolean isBusqueda3() {
		return busqueda3;
	}

	public void setBusqueda3(boolean busqueda3) {
		this.busqueda3 = busqueda3;
	}

	public String getDato1() {
		return dato1;
	}

	public void setDato1(String dato1) {
		this.dato1 = dato1;
	}

	public String getDato2() {
		return dato2;
	}

	public void setDato2(String dato2) {
		this.dato2 = dato2;
	}

	public String getDato3() {
		return dato3;
	}

	public void setDato3(String dato3) {
		this.dato3 = dato3;
	}

	public String getSeleccionFiltro() {
		return seleccionFiltro;
	}

	public void setSeleccionFiltro(String seleccionFiltro) {
		this.seleccionFiltro = seleccionFiltro;
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
	
	
	
	
}
