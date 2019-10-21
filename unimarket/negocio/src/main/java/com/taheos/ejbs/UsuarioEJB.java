package com.taheos.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.taheos.unimarket.entidades.Administrador;
import com.taheos.unimarket.entidades.Calificacion;
import com.taheos.unimarket.entidades.Comentario;
import com.taheos.unimarket.entidades.Compra;
import com.taheos.unimarket.entidades.DetalleCompra;
import com.taheos.unimarket.entidades.Favorito;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Disponibilidad;
import com.taheos.unimarket.enums.Rol;

/**
 * Session Bean implementation class UsuarioEJB
 */
@Stateless
@LocalBean
public class UsuarioEJB implements UsuarioEJBRemote {

	/**
	 * Default constructor.
	 */
	public UsuarioEJB() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Permite iniciar sesion a un usuario
	 * 
	 * @param email email de la persona que va a entrar
	 * @param clave clave de la persona que va a entrar
	 * @return la persona si existe registrada, null en caso contrario
	 */
	public Persona iniciarSesion(String email, String clave) throws Exception {

		try {

			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.INICIAR_SESION, Persona.class);
			query.setParameter("correo", email);
			query.setParameter("clave", clave);
			Persona buscada = query.getSingleResult();
			// verifcamos que si sea una usuario

			if (buscada instanceof Administrador) {
				throw new Exception(
						"La persona ingresada no es un Usuario es un administrador " + "/n" + "Es un adminitrador");
			}

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * Permite a un usuario registrarse s
	 * 
	 * @param usuario es la persona que desea registrarse
	 * @throws Exception clase que nos permite lanzar una excepcion
	 */
	public void registrarUsuario(Usuario usuario) throws Exception {
		if (entityManager.find(Usuario.class, usuario.getClave()) != null) {
			throw new Exception("El usuario ya se encuentra registrado");
		}
		if (buscarPersonaPorEmail(usuario.getCorreo()) != null) {
			throw new Exception("El email ya se encuentra en uso");
		}
		entityManager.persist(usuario);
	}

	/**
	 * permite buscar una personas usando su email
	 * 
	 * @param email email de la presona
	 * @return persona con el email especificado
	 */
	public Persona buscarPersonaPorEmail(String email) {

		try {
			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.BUSCAR_PERSONA_POR_CORREO,
					Persona.class);
			query.setParameter("correo", email);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * Permite registrar los productos que quiere vender un usuario
	 * 
	 * @param usu es el usuario al que queremos registrarle un producto
	 * @param pro es el producto que queremos registrar
	 * @throws Exception si el usuario no es tiene de rol vendedor, o si ya esta en
	 *                   su lista de productos
	 */
	public void registrarProductoVendido(Usuario usu, Producto pro) throws Exception {

		if (usu.getRol().equals(Rol.COMPRADOR)) {
			throw new Exception("Debe tener el rol de VENDEDOR, si es un COMPRADOR no puede tener productos a vender");
		}

		List<Producto> lisP = usu.getProductos();

		for (Producto pL : lisP) {
			if (pL.equals(pro)) {
				throw new Exception("El producto: " + pro.getId() + "-" + pro.getNombre()
						+ " ya se encuentra en la lista de ventas del Usuario: " + usu.getClave() + "-"
						+ usu.getNombre());
			}
		}
		// agregamos el producto
		usu.getProductos().add(pro);

		entityManager.merge(usu);
	}

	/**
	 * Permite listar los producto por su categoria
	 * 
	 * @return La lista de productos por producto
	 * @throws Exception
	 */
	public List<Producto> listaProductosGenero() {

		List<Producto> listP;

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_DISPONIBLES_CATEGORIA_DOS,
				Producto.class);
		query.setParameter("disponibilidad", Disponibilidad.DISPONIBLE);
		listP = query.getResultList();

		return listP;
	}

	/**
	 * Permite registrar los comentarios que quiere vender un usuario
	 * 
	 * @param pro    es el producto al que queremos registrarle el comentario
	 * @param coment es el Comentario que queremos registrar
	 * @param Usu    es el Usuario que hara el comentario
	 */
	public void registrarComentarioProducto(Producto pro, Comentario coment, Usuario usu) throws Exception {

		if (coment == null) {
			throw new Exception("El comentario no puede ser null");
		}
		if (pro == null) {
			throw new Exception("El Producto no puede ser null");
		}
		if (coment.getProducto().equals(pro) == false) {
			throw new Exception("Este comentarios tiene un producto distinto asociado,\n Info_ProductoComentario: "
					+ coment.getProducto().getId() + "__" + coment.getProducto().getNombre() + "\n Info_Producto: "
					+ pro.getId() + "__" + pro.getNombre());
		}
		if (usu.equals(null)) {
			throw new Exception("El Comentario debe tener un usuario asigando, no puede ser null");
		}

		// agregamos el comentario a la base de datos
		entityManager.persist(coment);

		// actualizamos el usuario y producto
		entityManager.merge(usu);

		Comentario nuevo = entityManager.find(Comentario.class, 4L);

		pro.getComentarios().add(nuevo);

		entityManager.merge(pro);
	}

	/**
	 * Permite agregar un nuevo favorito a la lista de favoritos de un usuario
	 * 
	 * @param usu         es el ususaurio al que le vamosa gregar el nuevo favorito
	 * @param productoFav es el producto que se desea se quiere agregar como
	 *                    favorito
	 */

	public void agregarListaFavorito(Usuario usu, Producto productoFav) throws Exception {

		if (productoFav == null) {
			throw new Exception("El producto no puede ser null");
		}

		if (usu == null) {
			throw new Exception("El Usuario ya es favorito de este usuario");
		}

		for (Favorito fav : usu.getFavoritos()) {
			if (fav.getProductos().equals(productoFav)) {
				throw new Exception("Este producto ya es favorito de este usuario");
			}
		}

		Favorito nuevo = new Favorito();

		nuevo.setId_favoritos(4L);
		nuevo.setProducto(productoFav);
		nuevo.setUsuario(usu);

		usu.getFavoritos().add(nuevo);

		// Actualizamos el usuario
		entityManager.merge(usu);
	}

	/**
	 * Permite eliminar un elemento favorito de la lista de favoritos de un usuario
	 * 
	 * @param usu         es el usuario al que le removeremos el producto favorito
	 * @param id_producto es la Id de el producto que vamos a eliminar de la lista
	 *                    de productos favoritos de el ususario
	 * @throws Exception
	 */
	public void eliminarListaFavorito(Usuario usu, Long id_producto) throws Exception {

		if (usu == null) {
			throw new Exception("El Usuario no se encontro");
		}
		Favorito buscado = null;

		for (Favorito fav : usu.getFavoritos()) {
			if (fav.getProductos().getId().equals(id_producto)) {
				buscado = fav;
			}
		}

		if (buscado == null) {
			throw new Exception("Id de el producto favorito NO pertenece a la lista de favoritos de este usuario");
		}
		// eliminamos el favorito
		entityManager.remove(buscado);
	}

	/**
	 * Permite agregar varios detalleCompra a la lista de una compra segun su ID
	 * 
	 * @param usu      es el usuario que realiza la compra el cual debe estar en el
	 *                 ROl de vendedor
	 * @param idCompra es la id de la compra a la que agregaremos los detalleCompra
	 * @param dc       es la lista de detalleCompra que deseo agregar
	 * @throws Exception
	 */
	public void agregarProdutosCompra(Usuario usu, Long idCompra, ArrayList<DetalleCompra> dc) throws Exception {

		if (usu == null) {
			throw new Exception("El Usuario ya es favorito de este usuario");
		}

		if (usu.getRol().equals(Rol.VENDEDOR)) {
			throw new Exception("El Usuario debe esta en rol de vendedor");
		}

		Compra buscada = entityManager.find(Compra.class, idCompra);

		if (buscada == null) {
			throw new Exception("Compra no encontrada");
		}

		if (usu.equals(buscada.getUsuario()) == false) {
			throw new Exception("Esta compra no pertenece a el usuario actual");
		}

		boolean bandera = false;

		for (DetalleCompra detalleCompra : dc) {

			for (DetalleCompra detalleCompra2 : buscada.getDetallesCompra()) {

				if (detalleCompra.getProducto().equals(detalleCompra2.getProducto())) {
					detalleCompra2.setCantidad(detalleCompra.getCantidad() + detalleCompra2.getCantidad());
					bandera = true;
					break;
				}

			}

			if (bandera == false) {
				detalleCompra.setCompra(buscada);
				entityManager.persist(detalleCompra);
				buscada.getDetallesCompra().add(detalleCompra);
			} else {
				bandera = false;
			}

		}
		// volvemos a contar el total de la compra
		// reiniciamos la cuenta
		buscada.setTotal_compra(0);

		for (DetalleCompra detalleCompra2 : buscada.getDetallesCompra()) {

			buscada.setTotal_compra(buscada.getTotal_compra() + detalleCompra2.totalDetalleCompra());

		}
		// actualizamos la compra
		entityManager.merge(buscada);
	}

	/**
	 * permite a un usuario darle una calificaciona un producto
	 * 
	 * @param usu     es el usuario que desea hacer la calificacion
	 * @param p       es el producto que calificaremos
	 * @param puntaje es el puntaje que el Usuario desea darle a el producto
	 * @throws Exception
	 */
	public void calificarProducto(Usuario usu, Producto p, double puntaje) throws Exception{
		
		if (usu == null) {
			throw new Exception("El Usuario no encontrado");
		}
		
		if(p==null) {
			throw new Exception("Producto no encontrado");
		}
				
		if(puntaje<0 || puntaje>5) {
			throw new Exception("La calificacion solo puede llegar estar entre 0 y 5");
		}
		Calificacion nueva= new Calificacion();
		Calificacion buscada=null; 
		
		nueva.setId_calificacion(104L);
		nueva.setPuntaje(puntaje);
		nueva.setUsuario(usu);
		nueva.setProducto(p);
		p.getCalificaciones().add(nueva);
		usu.getCalificaciones().add(nueva);

		for (Calificacion c : usu.getCalificaciones()) {
			if(c.getProducto().equals(p)) {
				c.setPuntaje(nueva.getPuntaje());
				buscada=c;			
				break;
			}
		}
		//agregamos la nueva calificacion o modificamos
		
		if(buscada!=null) {
			entityManager.merge(buscada);
		}else {
			entityManager.persist(nueva);
		}
		
		
		
		
	}

}