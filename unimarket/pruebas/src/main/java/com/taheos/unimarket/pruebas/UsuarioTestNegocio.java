package com.taheos.unimarket.pruebas;

import java.util.ArrayList;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.taheos.ejbs.UsuarioEJB;
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

/**
 * Clase de pruebas dedicada para la pruebas de las entidades de usuario
 * 
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class UsuarioTestNegocio {

	/**
	 * EJB que se desea probar
	 */
	@EJB
	private UsuarioEJB usuEJB;

	/**
	 * instancia para realizar las transaciones con las entidades
	 * 
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * genera el archivo del despliegue de pruebas
	 * 
	 * @return genera un archivo de configuracion web
	 */
	@Deployment
	public static Archive<?> createDeploymentPackage() {
		return ShrinkWrap.create(JavaArchive.class).addClass(UsuarioEJB.class).addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * permite a un usuario agregar un producto a su lista de productos a vender
	 */
	@Test
	@UsingDataSet({ "Persona.json", "Producto.json" })
	public void agregarProductoVender() {

		Usuario buscado = (Usuario) entityManager.find(Persona.class, "02");
		Producto prod = entityManager.find(Producto.class, 1234L);

		try {
			usuEJB.registrarProductoVendido(buscado, prod);
		} catch (Exception e) {
			System.out.println("/n no funciona  agregar producto vendedor");
			e.printStackTrace();
		}

		Usuario actualizado = (Usuario) entityManager.find(Persona.class, "02");

		for (Producto p : actualizado.getProductos()) {
			System.out.println("Nombre: " + p.getNombre() + " Codigo: " + p.getId());
		}
	}

	/**
	 * Permite listar los productos por su categoria
	 */
	@Test
	@UsingDataSet({ "Producto.json" })
	public void listaProductosDisponibles() {

		List<Producto> proy = null;
		try {
			proy = usuEJB.listaProductosGenero();

			for (Producto p : proy) {
				System.out.println("Nombre: " + p.getNombre() + " Categoria: " + p.getCategoria());
			}
		} catch (Exception e) {
			System.out.println("/n no funciona listar Productos");
			e.printStackTrace();
		}

	}

	/**
	 * Permite agregar un comentario a un producto
	 */
	@Test
	@UsingDataSet({ "Producto.json", "Persona.json", "Comentario.json" })
	public void agregarComentarioProducto() {

		Usuario usu = (Usuario) entityManager.find(Persona.class, "03");
		Producto pro = entityManager.find(Producto.class, 1234L);
		Comentario coment = new Comentario();

		coment.setTexto("este es el comentario");
		coment.setUsuario(usu);
		coment.setProducto(pro);
		coment.setId_comentario(4L);
		usu.getComentarios().add(coment);

		try {
			usuEJB.registrarComentarioProducto(pro, coment, usu);

			Producto d = entityManager.find(Producto.class, 1234L);

			for (Comentario c : d.getComentarios()) {

				System.out.println("Id: " + c.getId_comentario() + "Nombre producto: " + c.getProducto().getNombre()
						+ " Usuario: " + c.getUsuario().getClave() + " - " + c.getUsuario().getId() + " - Comentario: "
						+ c.getTexto());
			}

		} catch (Exception e) {
			System.out.println("/n no funciona comentario");
			e.printStackTrace();
		}

	}

	/**
	 * Permite agregar un producto a la lista de favoritos de un usuario
	 */
	@Test
	@UsingDataSet({ "Producto.json", "Persona.json", "Favorito.json" })
	public void agregarProductoFavoritoUsuario() {

		Usuario usu = (Usuario) entityManager.find(Persona.class, "03");
		Producto pro = entityManager.find(Producto.class, 4321L);
		System.out.println(pro.getNombre());
		try {

			usuEJB.agregarListaFavorito(usu, pro);
			Usuario ac = (Usuario) entityManager.find(Persona.class, "03");
			System.out.println("\n Lista de favoritos: \n");

			for (Favorito fav : ac.getFavoritos()) {

				System.out.println("Nombre Producto: " + fav.getProductos().getNombre());

			}

		} catch (Exception e) {
			System.out.println("/n no funciona agregar favorito producto");
			e.printStackTrace();
		}

	}

	/**
	 * Permite agregar un producto a la lista de favoritos de un usuario
	 */
	@Test
	@UsingDataSet({ "Producto.json", "Persona.json", "Favorito.json" })
	public void eliminarProductoFavoritoUsuario() {

		Usuario usu = (Usuario) entityManager.find(Persona.class, "03");

		try {

			usuEJB.eliminarListaFavorito(usu, 1L);
			Usuario ac = (Usuario) entityManager.find(Persona.class, "03");
			System.out.println("\n Lista de favoritos: \n");

			for (Favorito fav : ac.getFavoritos()) {

				System.out.println("Nombre Producto: " + fav.getProductos().getNombre());

			}

		} catch (Exception e) {
			System.out.println("/n no funciona agregar favorito producto");
			e.printStackTrace();
		}

	}

	/**
	 * Permite agregar un detalleCompra a la lista de compras de un usuario
	 */
	@Test
	@UsingDataSet({ "Producto.json", "Persona.json", "DetalleCompra.json", "Compra.json" })
	public void agregarProductosComprados() {

		Usuario usu = (Usuario) entityManager.find(Persona.class, "03");
		Compra actual = entityManager.find(Compra.class, 4321L);

		System.out.println("\n Antes");

		for (DetalleCompra d : actual.getDetallesCompra()) {
			System.out.println("Id_detalleCompra " + d.getId_detalleCompra() + " Producto: "
					+ d.getProducto().getNombre() + "Cantidad: " + d.getCantidad());
		}
		try {
			ArrayList<DetalleCompra> dc = new ArrayList<DetalleCompra>();

			DetalleCompra uno = new DetalleCompra();

			uno.setCantidad(4);
			uno.setProducto(entityManager.find(Producto.class, 4321L));
			uno.setPrecioCompra(uno.getProducto().getPrecio());

			DetalleCompra dos = new DetalleCompra();

			dos.setCantidad(1);
			dos.setProducto(entityManager.find(Producto.class, 1234L));
			dos.setPrecioCompra(dos.getProducto().getPrecio());

			dc.add(uno);
			dc.add(dos);

			usuEJB.agregarProdutosCompra(usu, 4321L, dc);

		} catch (Exception e) {
			System.out.println("/n no funciona agregar favorito producto");
			e.printStackTrace();
		}

		Compra siguiente = entityManager.find(Compra.class, 4321L);

		System.out.println("\n Despues");

		for (DetalleCompra d : siguiente.getDetallesCompra()) {
			System.out.println("Id_detalleCompra " + d.getId_detalleCompra() + " Producto: "
					+ d.getProducto().getNombre() + "Cantidad: " + d.getCantidad());
		}
	}

	/**
	 * Permite calificar un producto a un usuario
	 */
	@Test
	@UsingDataSet({ "Producto.json", "Persona.json", "Calificacion.json" })
	public void calificarProducto() {

		Usuario usu = (Usuario) entityManager.find(Persona.class, "02");
		Producto p = entityManager.find(Producto.class, 1234L);

		System.out.println("\n Antes \n");

		for (Calificacion c : usu.getCalificaciones()) {

			System.out.println("Producto: " + c.getProducto().getNombre() + " - Calificacion " + c.getPuntaje());

		}

		try {

			usuEJB.calificarProducto(usu, p, 4.5);

			Usuario ac = (Usuario) entityManager.find(Persona.class, "02");

			System.out.println("\n Despues \n");

			for (Calificacion c : ac.getCalificaciones()) {

				System.out.println("Producto: " + c.getProducto().getNombre() + " - Calificacion " + c.getPuntaje());

			}

		} catch (Exception e) {
			System.out.println("/n no funciona calificar producto \n");
			e.printStackTrace();
		}

	}

	/**
	 * Permite listar los productos dada una condicion
	 */
	@Test
	@UsingDataSet({ "Producto.json", "Persona.json", "Calificacion.json" })
	public void listarProductos() {

		try {

			List<Producto> listaCategoria = usuEJB.ListarProductosPorCategoria(Categoria.TECNOLOGIA);
			System.out.println("\n Categoria \n");

			for (Producto p : listaCategoria) {

				System.out.println(p.getNombre() + " - " + p.getCategoria());
			}

			List<Producto> listaCategoria2 = usuEJB.ListarProductosPorPrecio(0, 1000000.0, Disponibilidad.DISPONIBLE);
			System.out.println("\n Precio \n");

			for (Producto p : listaCategoria2) {

				System.out.println(p.getNombre() + " - " + p.getPrecio());
			}

			List<Producto> listaCategoria3 = usuEJB.ListarProductosPorCantidad(2, Disponibilidad.DISPONIBLE);
			System.out.println("\n Cantidad \n");

			for (Producto p : listaCategoria3) {

				System.out.println(p.getNombre() + " - " + p.getCantidad());
			}

		} catch (Exception e) {
			System.out.println("/n no funciona calificar producto \n");
			e.printStackTrace();
		}

	}

	/**
	 * permite hacer manejo de las listas de un usuario
	 */
	@Test
	@UsingDataSet({ "Persona.json", "Producto.json", "Compra.json" })
	public void gestionarListaUsuario() {
		
		Producto prod = entityManager.find(Producto.class, 1234L);

		try {
			List<Compra> compras = usuEJB.ListarCompras("02");
			System.out.println("\n Compras \n");

			for (Compra compra : compras) {
				System.out.println(compra.getId_compra() + " - " + compra.getMetodoPago());
			}
			/////////
			List<Producto> productos = usuEJB.ListarVentas("02");
			System.out.println("\n Productos venta 1 \n");

			for (Producto producto : productos) {
				System.out.println(producto.getNombre());
			}
			
			usuEJB.agregarProductoVenta("02", prod);
			
			List<Producto> productos2 = usuEJB.ListarVentas("02");
			System.out.println("\n Productos venta 2 \n");

			for (Producto producto : productos2) {
				System.out.println(producto.getNombre());
			}
			
			usuEJB.eliminarProductoVenta("02", 1234L);
			
			List<Producto> productos3 = usuEJB.ListarVentas("02");
			System.out.println("\n Productos venta 3 \n");

			for (Producto producto : productos3) {
				System.out.println(producto.getNombre());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
