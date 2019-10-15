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
	 * cedula observable de un empleado
	 */
	private StringProperty cedula;
	/**
	 * nombre observable de una persona
	 */
	private StringProperty nombre;

	/**
	 * email observable de un empleado
	 */
	private StringProperty email;
	/**
	 * clave observable de un empleado
	 */
	private StringProperty clave;

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
		this.clave = new SimpleStringProperty("12345");

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
		this.clave = new SimpleStringProperty(usuario.getClave());

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
	public UsuarioObservable(String cedula, String nombre, String email, String clave) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.email = new SimpleStringProperty(email);
		this.clave = new SimpleStringProperty(clave);

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

	/**
	 * @return the clave
	 */
	public StringProperty getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(StringProperty clave) {
		this.clave = clave;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
