package com.taheos.modelo;

import com.taheos.unimarket.entidades.Usuario;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author com.taheos
 * @version 1.0
 */
public class UsuarioObservable {

	/**
	 * cedula observable de un usuario
	 */
	private StringProperty cedula;
	/**
	 * nombre observable de una usuario
	 */
	private StringProperty nombre;

	/**
	 * email observable de un usuario
	 */
	private StringProperty email;
	/**
	 * numero de telefono observable de un usuario
	 */
	private StringProperty num_telefono;
	/**
	 * Direccion observable de un usuario
	 */
	private StringProperty direccion;
	/**
	 * usuario asociado
	 */
	private Usuario usuario;

	/**
	 * 
	 * @param cedula
	 * @param nombre
	 */
	public UsuarioObservable(String cedula, String nombre) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);

		this.email = new SimpleStringProperty("algo@mail.com");
		this.num_telefono = new SimpleStringProperty("12312312");

	}

	/**
	 * constructor que genera con usuario observable con base a un usuario
	 * 
	 * @param usuario que se quiere volver observable
	 */
	public UsuarioObservable(Usuario usuario) {

		this.usuario = usuario;
		this.cedula = new SimpleStringProperty(usuario.getId());
		this.nombre = new SimpleStringProperty(usuario.getNombre());
		this.email = new SimpleStringProperty(usuario.getCorreo());
		this.num_telefono = new SimpleStringProperty(usuario.getNum_telefono());
		this.direccion = new SimpleStringProperty(usuario.getDireccion());
	}

	/**
	 * permite generar una instanci usando todos los usuarios
	 * 
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param email
	 * @param clave
	 * @param fecha
	 */
	public UsuarioObservable(String cedula, String nombre, String email, String clave, String num_telefono, String direccion) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.email = new SimpleStringProperty(email);
		this.num_telefono = new SimpleStringProperty(num_telefono);
		this.direccion = new SimpleStringProperty(direccion);
	}

	/**
	 * @return the cedula
	 */
	public StringProperty getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(StringProperty cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombre
	 */
	public StringProperty getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the email
	 */
	public StringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(StringProperty email) {
		this.email = email;
	}

	public StringProperty getNum_telefono() {
		return num_telefono;
	}

	public void setNum_telefono(StringProperty num_telefono) {
		this.num_telefono = num_telefono;
	}

	
	public StringProperty getDireccion() {
		return direccion;
	}

	public void setDireccion(StringProperty direccion) {
		this.direccion = direccion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
