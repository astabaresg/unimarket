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
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;

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
	 * permite buscar una personas usando su email
	 * 
	 * @param email email de la presona
	 * @return persona con el email especificado
	 */
	private Persona buscarPersonaPorEmail(String email) {

		try {
			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.BUSCAR_PERSONA_POR_CORREO,
					Persona.class);
			query.setParameter("email", email);
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
		if (buscarPersonaPorEmail(usuario.getCorreo()) != null) {
			throw new ElementoRepetidoExcepcion("Ya existe un usuario con ese nombre");
		} else {
			try {
				entityManager.persist(usuario);
				return usuario;
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
	 * Metodo que permite obtener la clave de un usuario
	 * @param cedula
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public String obtenerClave(String email) throws ElementoNoEncontradoExcepcion {
		if (buscarPersonaPorEmail(email) == null) {
			throw new ElementoNoEncontradoExcepcion("El usuario que está buscando no se encuentra registrado");
		}

		try {
			Persona aux = buscarPersonaPorEmail(email);
			return aux.getClave();
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
	 * Sirve para registrar un producto
	 * 
	 * @param usuario
	 * @return
	 * @throws ElementoRepetidoExcepcion
	 * @throws ElementoNoEncontradoExcepcion 
	 */
	public Producto registrarProducto(Producto producto) throws ElementoRepetidoExcepcion, ElementoNoEncontradoExcepcion {
		if (buscarProducto(producto.getId() + "") != null) {
			throw new ElementoRepetidoExcepcion("Ya existe un producto con ese nombre");
		} else {
			try {
				entityManager.persist(producto);
				return producto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * Metodo para eliminar un producto de la base de datos
	 * @param codigo
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public boolean eliminarProducto(String codigo) throws ElementoNoEncontradoExcepcion {

		if (buscarProducto(codigo) == null) {
			throw new ElementoNoEncontradoExcepcion(
					"El producto con el codigo: " + codigo + "no se encuentra registrado");
		}
		
		try {
			TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.BUSCAR_POR_ID, Producto.class);
			query.setParameter("id", Long.parseLong(codigo));
			Producto aux = query.getSingleResult();
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
	public Producto modificarProducto(Producto producto) throws ElementoNoEncontradoExcepcion {
		
		if (buscarProducto(producto.getId() + "")== null) {
			throw new ElementoNoEncontradoExcepcion("El producto:" + producto.getNombre() + "no se encuentra registrado");
		} else {

			try {
				
				Producto aux = buscarProducto(producto.getId() + "");
				
				aux.setCalificacion(producto.getCalificacion());
				aux.setCalificaciones(producto.getCalificaciones());
				aux.setCantidad(producto.getCantidad());
				aux.setCategoria(producto.getCategoria());
				aux.setComentarios(producto.getComentarios());
				aux.setDescripcion(producto.getDescripcion());
				aux.setDetalleCompra(producto.getDetalleCompra());
				aux.setDisponibilidad(producto.getDisponibilidad());
				aux.setFavorito(producto.getFavorito());
				aux.setFecha_limite(producto.getFecha_limite());
				aux.setId(producto.getId());
				aux.setImagen(producto.getImagen());
				aux.setNombre(producto.getNombre());
				aux.setPrecio(producto.getPrecio());
				aux.setUsuario(producto.getUsuario());
				entityManager.merge(aux);

				return aux;

			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
		}

	}
	/**
	 * Metodo que permite buscar un Producto
	 * @param codigo
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public Producto buscarProducto(String codigo) throws ElementoNoEncontradoExcepcion {
		if (entityManager.find(Producto.class, Long.parseLong(codigo)) == null) {
			throw new ElementoNoEncontradoExcepcion("El producto que está buscando no se encuentra registrado");
		}

		try {
			TypedQuery<Producto> producto = entityManager.createNamedQuery(Producto.BUSCAR_POR_ID, Producto.class);
			producto.setParameter("id", Long.parseLong(codigo));
			return producto.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Metodo que sirve para listar todos los Productos registrados
	 * @return
	 */
	public List<Producto> listarProductos() {
		try {
			TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_TODOS,
					Producto.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}
	/**
	 * Permite listar los productos registrados por categoria
	 * @param categoria
	 * @return
	 */
	public List<Producto> listarProductosPorCategoria (Categoria categoria){

		try {
			TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_DISPONIBLES_CATEGORIA,
					Producto.class);
			query.setParameter("categoria", categoria);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Permite ver el detalle de un producto en especifico
	 * @param codigo
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	public String verDetalleProducto(String codigo) throws ElementoNoEncontradoExcepcion{
		if (buscarProducto(codigo) == null) {
			throw new ElementoNoEncontradoExcepcion("Ya existe un producto con ese nombre");
		} else {
			try {
				
				Producto producto = buscarProducto(codigo);
				return producto.getDescripcion();
			} catch (Exception e) {
				return null;
			}
		}
	}
}
