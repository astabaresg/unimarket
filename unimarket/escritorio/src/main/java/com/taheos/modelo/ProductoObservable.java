package com.taheos.modelo;

import com.taheos.unimarket.entidades.Producto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author com.taheos
 *
 */
public class ProductoObservable {
	/**
	 * cedula observable de un producto
	 */
	private StringProperty id;
	/**
	 * nombre observable de una producto
	 */
	private StringProperty nombre;

	/**
	 * email observable de un producto
	 */
	private StringProperty precio;
	/**
	 * numero de telefono observable de un producto
	 */
	private StringProperty cantidad;
	/**
	 * Categoria observable de un producto
	 */
	private StringProperty categoria;
	/**
	 * usuario asociado
	 */
	private Producto producto;

	/**
	 * constructor que genera con producto observable con base a un producto
	 * 
	 * @param producto que se quiere volver observable
	 */
	public ProductoObservable(Producto producto) {

		this.producto = producto;
		this.id = new SimpleStringProperty(producto.getId() + "");
		this.nombre = new SimpleStringProperty(producto.getNombre());
		this.precio = new SimpleStringProperty(producto.getPrecio() + "");
		this.cantidad = new SimpleStringProperty(producto.getCantidad() + "");
		this.categoria = new SimpleStringProperty(producto.getCategoria().name());
	}

	/**
	 * constructor que genera un producto observable a partir de unos parametros
	 * @param id
	 * @param nombre
	 * @param precio
	 * @param cantidad
	 * @param categoria
	 */
	public ProductoObservable(String id, String nombre, String precio, String cantidad,
			String categoria) {
		this.id = new SimpleStringProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.precio = new SimpleStringProperty(precio);
		this.cantidad = new SimpleStringProperty(cantidad);
		this.categoria = new SimpleStringProperty(categoria);
	}

	public StringProperty getId() {
		return id;
	}

	public void setId(StringProperty id) {
		this.id = id;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	public StringProperty getPrecio() {
		return precio;
	}

	public void setPrecio(StringProperty precio) {
		this.precio = precio;
	}

	public StringProperty getCantidad() {
		return cantidad;
	}

	public void setCantidad(StringProperty cantidad) {
		this.cantidad = cantidad;
	}

	public StringProperty getCategoria() {
		return categoria;
	}

	public void setCategoria(StringProperty categoria) {
		this.categoria = categoria;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	
}
