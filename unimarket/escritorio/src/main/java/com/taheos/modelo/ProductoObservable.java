package com.taheos.modelo;

import com.taheos.unimarket.entidades.Producto;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	 * fecha limite observable de un producto
	 */
	private ObjectProperty<Date> fechaLimite;
	/**
	 * Categoria observable de un producto
	 */
	private StringProperty categoria;
	/**
	 * disponibilidad observable de un producto
	 */
	private StringProperty disponibilidad;
	/**
	 * producto asociado
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
		this.fechaLimite = new SimpleObjectProperty<>(producto.getFecha_limite());
		this.categoria = new SimpleStringProperty(producto.getCategoria().name());
		this.disponibilidad = new SimpleStringProperty(producto.getDisponibilidad().name());
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

	public ObjectProperty<Date> getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(ObjectProperty<Date> fechaLimite) {
		this.fechaLimite = fechaLimite;
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

	public StringProperty devolverFechaLimite() {
		return new SimpleStringProperty(fechaLimite.toString());
	}

	public StringProperty getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(StringProperty disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	
}
