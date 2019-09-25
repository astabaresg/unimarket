package com.taheos.unimarket.pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ctc.wstx.evt.WNotationDeclaration;
import com.taheos.unimarket.entidades.Compra;
import com.taheos.unimarket.entidades.DetalleCompra;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Disponibilidad;
import com.taheos.unimarket.enums.MetodoPago;

/**
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestJames {
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
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "prueba.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	}

	/**
	 * Permite probar: agregar un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Producto.json" })
	public void agregarProductoTest() {

		Producto nuevoP = new Producto();
		nuevoP.setId(new Long(12345));
		nuevoP.setNombre("Reloj Rolex");
		nuevoP.setDescripcion("Reloj Rolex Original");
		nuevoP.setPrecio(1000000.0);
		nuevoP.setDisponibilidad(Disponibilidad.DISPONIBLE);
		nuevoP.setCategoria(Categoria.JOYAS);
		nuevoP.setFecha_limite(new Date(2019 - 10 - 20));
		nuevoP.setCantidad(20);
		nuevoP.setCalificacion(10);

		entityManager.persist(nuevoP);

		Producto registrado = entityManager.find(Producto.class, 12345L);
		Assert.assertEquals(nuevoP, registrado);

	}

	/**
	 * Permite probar: buscar un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Producto.json" })
	public void buscarProductoTest() {
		Producto producto = entityManager.find(Producto.class, 1234L);
		Assert.assertEquals("Pc ASUS", producto.getNombre());
	}

	/**
	 * Permite probar: eliminar un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Producto.json" })
	public void eliminarProductoTest() {
		entityManager.remove(entityManager.find(Producto.class, 4321L));

		Producto nuevo = entityManager.find(Producto.class, 4321L);
		Assert.assertNull(nuevo);

	}

	/**
	 * Permite probar: modificar un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Producto.json" })
	public void modificarProductoTest() {

		Producto cambio = entityManager.find(Producto.class, 6789L);
		cambio.setDisponibilidad(Disponibilidad.NO_DISPONIBLE);
		entityManager.merge(cambio);
		Producto nuevo = entityManager.find(Producto.class, 6789L);
		Assert.assertEquals(Disponibilidad.NO_DISPONIBLE, nuevo.getDisponibilidad());
	}

	/**
	 * Permite probar: agregar una compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Compra.json", "Producto.json", "Persona.json" })
	public void agregarCompraTest() {

		
		Usuario usuario =  entityManager.find(Usuario.class, "02");
		
		Assert.assertNotNull(usuario);
		
		Compra nuevaC = new Compra();
		nuevaC.setId_compra(new Long(12345789));
		nuevaC.setMetodoPago(MetodoPago.CREDITO);
		nuevaC.setTotal_compra(60000.0);
		nuevaC.setUsuario(usuario);
		entityManager.persist(nuevaC);

		Compra registrada = entityManager.find(Compra.class, 12345789L);
		Assert.assertEquals(nuevaC, registrada);

	}

	/**
	 * Permite probar: buscar una compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Compra.json", "Producto.json", "Persona.json" })
	public void buscarCompraTest() {
		Compra compra = entityManager.find(Compra.class, 4321L);
		Assert.assertEquals(MetodoPago.CREDITO, compra.getMetodoPago());
	}

	/**
	 * Permite probar: eliminar una compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Compra.json", "Producto.json", "Persona.json" })
	public void eliminarCompraTest() {
		entityManager.remove(entityManager.find(Compra.class, 12345L));

		Compra nueva = entityManager.find(Compra.class, 12345L);
		Assert.assertNull(nueva);

	}

	/**
	 * Permite probar: modificar una compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Compra.json", "Producto.json", "Persona.json" })
	public void modificarCompraTest() {

		Compra cambio = entityManager.find(Compra.class, 4321L);
		cambio.setMetodoPago(MetodoPago.EFECTIVO);
		entityManager.merge(cambio);
		Compra nuevo = entityManager.find(Compra.class, 4321L);
		Assert.assertEquals(MetodoPago.EFECTIVO, nuevo.getMetodoPago());
	}
	
	
	/**
	 * Permite probar: agregar un detalle compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "DetalleCompra.json", "Compra.json", "Producto.json", "Persona.json" })
	public void agregarDetalleCompraTest() {

		Compra compra = entityManager.find(Compra.class, 1324L);
		Producto producto = entityManager.find(Producto.class, 1234L);
		
		DetalleCompra detalleC = new DetalleCompra();
		detalleC.setId_detalleCompra(new Long(918273));
		detalleC.setCantidad(3);
		detalleC.setPrecioCompra(12000.0);
		detalleC.setCompra(compra);
		detalleC.setProducto(producto);
		entityManager.persist(detalleC);

		DetalleCompra registrada = entityManager.find(DetalleCompra.class, 918273L);
		Assert.assertEquals(detalleC, registrada);

	}

	/**
	 * Permite probar: buscar un detalle compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "DetalleCompra.json", "Compra.json", "Producto.json", "Persona.json" })
	public void buscarDetalleCompraTest() {
		DetalleCompra dCompra = entityManager.find(DetalleCompra.class, 9876L);
		Assert.assertEquals(7, dCompra.getCantidad());
	}

	/**
	 * Permite probar: eliminar un detalle compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "DetalleCompra.json", "Compra.json", "Producto.json", "Persona.json" })
	public void eliminarDetalleCompraTest() {
		entityManager.remove(entityManager.find(DetalleCompra.class, 5678L));

		DetalleCompra nueva = entityManager.find(DetalleCompra.class, 5678L);
		Assert.assertNull(nueva);

	}

	/**
	 * Permite probar: modificar un detalle compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "DetalleCompra.json", "Compra.json", "Producto.json", "Persona.json" })
	public void modificarDetalleCompraTest() {

		DetalleCompra cambio = entityManager.find(DetalleCompra.class, 9786L);
		cambio.setCantidad(10);;
		entityManager.merge(cambio);
		DetalleCompra nuevo = entityManager.find(DetalleCompra.class, 9786L);
		Assert.assertEquals(10, nuevo.getCantidad());
	}

}
