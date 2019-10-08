package com.taheos.ejbs;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.taheos.excepciones.ElementoNoEncontradoExcepcion;
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
	 * 
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
	 * 
	 * @param usuario
	 * @return
	 * @throws ElementoRepetidoExcepcion
	 */
	public Usuario registrarUsuario(Usuario usuario) throws ElementoRepetidoExcepcion {
		if (buscarUsuarioPorEmail(usuario.getCorreo()) != null) {
			throw new ElementoRepetidoExcepcion("Ya existe un usuario con ese nombre");
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
	 * Metodo para eliminar un usuario de la base de datos
	 * @param cedula
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public boolean eliminarUsuario(String cedula) throws ElementoNoEncontradoExcepcion {

		if (entityManager.find(Usuario.class, cedula) == null) {
			throw new ElementoNoEncontradoExcepcion(
					"El usuario con la cedula: " + cedula + "no se encuentra registrado");
		}
		
		try {
			Usuario aux = entityManager.find(Usuario.class, cedula);
			entityManager.remove(aux);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	/**
	 * Metodo que permite modificar un usuario
	 * @param usuario
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public Usuario modificarUsuario(Usuario usuario) throws ElementoNoEncontradoExcepcion {
		
		if (entityManager.find(Usuario.class, usuario.getId()) == null) {
			throw new ElementoNoEncontradoExcepcion("El usuario:" + usuario.getNombre() + "no se encuentra registrado");
		} else {

			try {

				Usuario aux = entityManager.find(Usuario.class, usuario.getId());
				
				aux.setCalificaciones(usuario.getCalificaciones());
				aux.setClave(usuario.getClave());
				aux.setComentarios(usuario.getComentarios());
				aux.setCompras(usuario.getCompras());
				aux.setCorreo(usuario.getCorreo());
				aux.setDireccion(usuario.getDireccion());
				aux.setFavoritos(usuario.getFavoritos());
				aux.setId(usuario.getId());
				aux.setNombre(usuario.getNombre());
				aux.setNum_telefono(usuario.getNum_telefono());
				aux.setProductos(usuario.getProductos());
				aux.setRol(usuario.getRol());
				
				entityManager.merge(aux);

				return aux;

			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
		}

	}
	/**
	 * Metodo que permite buscar un usuario
	 * @param cedula
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public Usuario buscarUsuario(String cedula) throws ElementoNoEncontradoExcepcion {
		if (entityManager.find(Persona.class, cedula) == null) {
			throw new ElementoNoEncontradoExcepcion("El usuario que está buscando no se encuentra registrado");
		}

		try {
			Usuario aux = entityManager.find(Usuario.class, cedula);
			return aux;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Metodo que sirve para listar todos los usuarios registrados
	 * @return
	 */
	public List<Usuario> listarUsuarios() {
		try {
			TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.LISTAR_TODOS_LOS_USUARIOS,
					Usuario.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
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
			TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.BUSCAR_USUARIO_POR_CORREO,
					Usuario.class);
			query.setParameter("email", email);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

}
