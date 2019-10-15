package com.taheos.modelo;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.taheos.ejbs.AdminEJBRemote;
import com.taheos.excepciones.ElementoNoEncontradoExcepcion;
import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.util.Utilidades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Delegado que permite conectar la capa de negocio con la de presentación
 * 
 * @author com.taheos
 * @version 1.0
 */
public class AdministradorDelegado {

	/**
	 * instancia que representa el ejb remoto de administrador
	 */
	private AdminEJBRemote adminEJB;
	/**
	 * permite manejar una unica instancia para le manejo de delegados
	 */
	public static AdministradorDelegado administradorDelegado = instancia();

	/**
	 * constructor para conectar con la capa de negocio
	 */
	private AdministradorDelegado() {
		try {
			adminEJB = (AdminEJBRemote) new InitialContext().lookup(AdminEJBRemote.JNDI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permite devolver una unica instancia de delegado
	 * 
	 * @return instancia unica del delegado
	 */
	private static AdministradorDelegado instancia() {

		if (administradorDelegado == null) {
			administradorDelegado = new AdministradorDelegado();
			return administradorDelegado;
		}
		return administradorDelegado;
	}

	/**
	 * pemite registar un nuevo usuario
	 * 
	 * @param usuario empleado a agregar
	 * @return devuelve true si el usuario fue registrado
	 */
	public boolean registrarUsuario(Usuario usuario) {
		try {
			return adminEJB.registrarUsuario(usuario) != null;
		} catch (ElementoRepetidoExcepcion e) {
			Utilidades.mostrarMensaje("Error al registrar un usuario", e.getMessage());
			return false;
		}
	}

	/**
	 * devuelve la lista de usuario que estan en la base de datos
	 * 
	 * @return todos los usuario
	 */
	public List<Usuario> listarUsuarios() {
		return adminEJB.listarUsuarios();
	}

	/**
	 * permite eliminar un usuario mediante su cedula
	 * 
	 * @return true si el usuario si fue eliminado
	 */
	public boolean eliminarUsuario(Usuario usuario) {
		try {
			return adminEJB.eliminarUsuario(usuario.getId());
		} catch (ElementoNoEncontradoExcepcion e) {
			Utilidades.mostrarMensaje("Error al eliminar un usuario", e.getMessage());
			return false;
		}
	}

	/**
	 * genera una lista de usuarios observables
	 * 
	 * @return todos los usuarios obsevables
	 */
	public ObservableList<UsuarioObservable> listarEmpleadosObservables() {
		List<Usuario> usuarios = listarUsuarios();
		ObservableList<UsuarioObservable> empleadosObservables = FXCollections.observableArrayList();
		for (Usuario usuario : usuarios) {
			empleadosObservables.add(new UsuarioObservable(usuario));
		}
		return empleadosObservables;
	}

}
