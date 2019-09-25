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

import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Disponibilidad;

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

	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
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

	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Producto.json" })
	public void buscarProductoTest() {
		Producto producto = entityManager.find(Producto.class, 1234L);
		Assert.assertEquals("Pc ASUS", producto.getNombre());
	}

	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void eliminarProductoTest() {
		entityManager.remove(entityManager.find(Producto.class, 4321L));

		Producto nuevo = entityManager.find(Producto.class, 4321L);
		Assert.assertNull(nuevo);

	}

	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void modificarProductoTest() {

		Producto cambio = entityManager.find(Producto.class, 6789L);
		cambio.setDisponibilidad(Disponibilidad.NO_DISPONIBLE);
		entityManager.merge(cambio);
		Producto nuevo = entityManager.find(Producto.class, 6789L);
		Assert.assertEquals(Disponibilidad.NO_DISPONIBLE, nuevo.getDisponibilidad());
	}

}
