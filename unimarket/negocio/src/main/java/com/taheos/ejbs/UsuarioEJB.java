package com.taheos.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.taheos.excepciones.ElementoNoEncontradoExcepcion;
import com.taheos.excepciones.ElementoRepetidoExcepcion;
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

	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * Default constructor.
	 */
	public UsuarioEJB() {
		// TODO Auto-generated constructor stub
	}


	public List<String> devolverCategorias(){
		List<String> categorias = new ArrayList<String>();
		categorias.add("TECNOLOGIA");
		categorias.add("DEPORTE");
		categorias.add("MODA");
		categorias.add("LIBROS");
		categorias.add("JOYAS");
		return categorias;
	}
	public Categoria devolverCategoria (String nombre) {
		switch (nombre) {
		case "TECNOLOGIA":
			return Categoria.TECNOLOGIA;
		case "DEPORTE":
			return Categoria.DEPORTE;
		case "MODA":
			return Categoria.MODA;
		case "LIBROS":
			return Categoria.LIBROS;
		case "JOYAS":
			return Categoria.JOYAS;
		default:
			return null;
		}
	}

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
		//agregamos la nueva calificacion o la modificamos
		
		if(buscada!=null) {
			entityManager.merge(buscada);
		}else {
			entityManager.persist(nueva);
		}
		
		
	}
	
	/**
	 * Permite listar los productos dada una categoria
	 * @param productoCategoria es la categoria que queremos buscar
	 * @return la lista de producto que que tengan la categoria mencionada
	 */
	public List<Producto> ListarProductosPorCategoria(Categoria productoCategoria) {

		try {
		
			TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_DISPONIBLES_CATEGORIA,Producto.class);
			query.setParameter("categoria", productoCategoria);
			return query.getResultList();
		
		} catch (NoResultException e) {
			return null;
		}

	}
	
	/**
	 * Permite listar los productos que esten dentro de un intervalo basado en su precio
	 * @param ini es el valor inicial de el intervalo
	 * @param fin es el valor final de el intervalo
	 * @param dis si se quiere buscar en aquellos que estan disponibles o en aquellos que no
	 * @return la lista de producto cuyo precio se encuentre en el intervalo dado
	 */
	public List<Producto> ListarProductosPorPrecio(double ini,double fin,Disponibilidad dis) {

		try {
		
			TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_DISPONIBLES_PRECIO,Producto.class);
			
			query.setParameter("precioIni", ini);
			query.setParameter("precioFin", fin);
			query.setParameter("disponibilidad", dis);
			
			return query.getResultList();
		
		} catch (NoResultException e) {
			return null;
		}

	}
	
	/**
	 * Permite listar todos los producto que tenga una cantidad igual o mayor que la dada en "cantidad"
	 * @param cantidad es el numero que se tiene como base para la consulta
	 * @param dis es pasa saber si buscar en aquellos que esten disponibles o en aquellos que no
	 * @return los productos que tengan esta "cantidad" o mas
	 */
	public List<Producto> ListarProductosPorCantidad(int cantidad,Disponibilidad dis) {

		try {
		
			TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_DISPONIBLES_CANTIDAD,Producto.class);
			
			query.setParameter("cantidad", cantidad);
			query.setParameter("disponibilidad", dis);
			
			return query.getResultList();
		
		} catch (NoResultException e) {
			return null;
		}

	}
	
	/**
	 * Permite mostrar las compras de un usuario
	 * @param u es la Id de el usuario que mostraremos sus compras
	 * @return la lista de compras de el usaurio encontrado
	 */
	public List<Compra> ListarCompras(String u) {

		try {
		
			return entityManager.find(Usuario.class, u).getCompras();
					
		} catch (NoResultException e) {
			return null;
		}

	}
	/**
	 * Permite mostrar los productos que el usuario vende 
	 * @param u es la Id de el usuario que mostraremos sus compras
	 * @return la lista de productos de el usaurio encontrado
	 */
	public List<Producto> ListarVentas(String u) {

		try {
		
			return entityManager.find(Usuario.class, u).getProductos();
					
		} catch (NoResultException e) {
			return null;
		}

	}
	/**
	 * permite agregar un producto a la lista de productos de el usuario
	 * @param u es la Id de el usuario
	 * @param nuevo es el produto que queremos agregar
	 */
	public void agregarProductoVenta(String u, Producto nuevo) throws Exception {

		Usuario buscado=entityManager.find(Usuario.class, u);
		
		if(nuevo==null) {
			throw new Exception("El produto no puede es null");
		}
		
		if(buscado==null) {
			throw new Exception("El Usuario no se encontro");
		}
		
		
		buscado.getProductos().add(nuevo);
		
		entityManager.merge(buscado);

	}

	/**
	 * permite eliminar un producto de la lista de productos de el usuario
	 * 
	 * @param u es la Id de el usuario
	 * @param p es la Id de el producto
	 */
	public void eliminarProductoVenta(String u, Long p) {

		Usuario buscado=entityManager.find(Usuario.class, u);
		Producto pro=entityManager.find(Producto.class, p);
		try {
			buscado.getProductos().remove(pro);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		entityManager.merge(buscado);

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
			try {
				entityManager.persist(producto);
				return producto;
			} catch (Exception e) {
				return null;
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

	
}
