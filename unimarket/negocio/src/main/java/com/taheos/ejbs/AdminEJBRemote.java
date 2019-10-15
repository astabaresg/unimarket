package com.taheos.ejbs;


import javax.ejb.Remote;

import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Usuario;



/**
 * Interfaz que representa las transacciones que se pueden realizar desde la
 * capa de presentación
 * 
 * @author Alvaro Sebastian Tabares Gaviria
 * @version 1.0
 *
 */
@Remote
public interface AdminEJBRemote {

	String JNDI = "java:global/ear/negocio/AdminEJB!com.taheos.ejbs.AdminEJBRemote";
	
	
	Persona iniciarSesion(String email, String clave);
	Usuario registrarUsuario(Usuario usuario) throws ElementoRepetidoExcepcion;

}
