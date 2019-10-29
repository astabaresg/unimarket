package com.taheos.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import com.taheos.ejbs.UsuarioEJB;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Rol;


/**
 * Permite manejar todas las operaciones empleados
 * 
 * @author com.taheos
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private List<Usuario> usuarios;

	/**
	 * cedula del usuario
	 */
	private String cedula;
	/**
	 * nombre del usuario
	 */
	private String nombre;
	/**
	 * clave del usuario
	 */
	private String clave;
	/**
	 * email del usuario
	 */
	private String email;

	/**
	 * Direccion del usuario
	 */
	private String direccion;
	/**
	 * rol del usuario
	 */
	private Rol rol;
	/**
	 * Numero de telefono del usuario
	 */
	private String numTelefono;
	/**
	 * conexion con la capa de negocio
	 */
	@EJB (beanName = "UsuarioEJB")
	private UsuarioEJB usuarioEJB;
	
	/**
	 * carga la lista de usuarios
	 */
	@PostConstruct
	private void init() {
	}

	public String agregarU() {
		
		Usuario u = new Usuario();
		u.setClave(clave);
		u.setCorreo(email);
		u.setDireccion(direccion);
		u.setNombre(nombre);
		u.setNum_telefono(numTelefono);
		u.setRol(Rol.COMPRADOR);
		u.setId(cedula);

		try {
			usuarioEJB.registrarUsuario(u);
			
			return "/index";
		} catch (Exception e) {
			return null;
		}

	}

//	/**
//	 * permite obtener el usuario que se desea eliminar
//	 */
//	public void eliminarEmpleado() {
//		try {
//			adminEJB.desactivarEmpleado(usuario.getCedula());
//			usuarios = adminEJB.listarEmpleados();
//			Util.mostrarMensaje("Eliminaci�n exitosa!!!", "Eliminaci�n exitosa!!!");
//		} catch (Exception e) {
//			Util.mostrarMensaje(e.getMessage(), e.getMessage());
//		}
//	}

	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	
	
}
