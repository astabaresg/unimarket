package com.taheos.ejbs;


import javax.ejb.Remote;

import com.taheos.unimarket.entidades.Persona;


@Remote
public interface AdminEJBRemote {

	/**
	 * Permite iniciar sesion para acceder al programa
	 * @param email email de la persona que va a entrar
	 * @param clave clave de la persona que va a entrar
	 * @return la persona si existe registrada, null en caso contrario
	 */
	public Persona iniciarSesion(String email, String clave);

}
