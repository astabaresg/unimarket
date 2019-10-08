package com.taheos.ejbs;

import javax.ejb.Remote;

import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Usuario;

@Remote
public interface AdminEJBRemote {

	Persona iniciarSesion(String email, String clave);
	Usuario registrarUsuario(Usuario usuario) throws ElementoRepetidoExcepcion;
}
