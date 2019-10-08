package com.taheos.ejbs;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Usuario;


/**
 * Maneja todas las operaciones asociadas a los adminstradores
 * 
 * @author Alvaro Sebastian Tabares G
 * @version 1.0
 */
@Stateless
@LocalBean
public class AdminEJB implements AdminEJBRemote {

	/**
	 * permite hacer todas las transacciones de la base de datos
	 */
	@PersistenceContext
	private EntityManager entityManager;

	public AdminEJB() {
	}

	/**
	 * Permite iniciar sesion para acceder al programa
	 * @param email email de la persona que va a entrar
	 * @param clave clave de la persona que va a entrar
	 * @return la persona si existe registrada, null en caso contrario
	 */
	public Persona iniciarSesion(String email, String clave) {

		try {

			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.INICIAR_SESION, Persona.class);
			query.setParameter("email", email);
			query.setParameter("clave", clave);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * Sirve para registrar un usuario
	 * @param usuario
	 * @return
	 * @throws ElementoRepetidoExcepcion
	 */
	public Usuario registrarUsuario(Usuario usuario) throws ElementoRepetidoExcepcion {
		if (buscarUsuarioPorEmail(usuario.getCorreo()) != null) {
			throw new ElementoRepetidoExcepcion("Ya existe una familia con ese nombre");
		} else {
			try {
				entityManager.persist(usuario);
				return buscarUsuarioPorEmail(usuario.getCorreo());
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * permite buscar una personas usando su email
	 * 
	 * @param email email de la presona
	 * @return persona con el email especificado
	 */
	private Usuario buscarUsuarioPorEmail(String email) {

		try {
			TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.BUSCAR_USUARIO_POR_CORREO, Usuario.class);
			query.setParameter("email", email);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

}
