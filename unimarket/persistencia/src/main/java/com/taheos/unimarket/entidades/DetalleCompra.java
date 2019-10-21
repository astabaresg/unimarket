 package com.taheos.unimarket.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DetalleCompra
 *
 */
@Entity

public class DetalleCompra implements Serializable {

  /**
   * Identificador de compra 
   */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id_detalleCompra;
	/**
	 * La compra asocioada a el detalleCompra
	 */
	@ManyToOne
	private Compra compra;
	/**
	 * Producto asociado a detalleCompra
	 */
	@ManyToOne
	private Producto producto;
	
	/**
	 * Precio en el que producto fue comprado
	 */
	private double precioCompra;
	/**
	 * Cantidad de el producto que se compro
	 */
	private int cantidad;
	
	private static final long serialVersionUID = 1L;

	/**
	 * hallamos el total de la compra realizada
	 * @return
	 */
	public double totalDetalleCompra() {
		return precioCompra*(double)cantidad;
	}
	public DetalleCompra() {
		super();
	}   
	
	public Long getId_detalleCompra() {
		return id_detalleCompra;
	}

	public void setId_detalleCompra(Long id_detalleCompra) {
		this.id_detalleCompra = id_detalleCompra;
	}

	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public double getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
