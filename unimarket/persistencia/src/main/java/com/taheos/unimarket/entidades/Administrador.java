package com.taheos.unimarket.entidades;

import com.taheos.unimarket.entidades.Persona;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@NamedQuery(name=Administrador.CONTAR_ADMIN , query="select count (admin) from Administrador admin ")
@Entity
public class Administrador extends Persona implements Serializable {

	
	private static final long serialVersionUID = 1L;
	public static final String CONTAR_ADMIN= "ContarAdministradores";
	public Administrador() {
		super();
	}
   
}
