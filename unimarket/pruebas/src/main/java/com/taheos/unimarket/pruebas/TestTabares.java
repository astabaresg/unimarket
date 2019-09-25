package com.taheos.unimarket.pruebas;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

import com.taheos.unimarket.entidades.Comentario;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;

/**
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestTabares {
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
	 * permite probar crear un comentario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Persona.json", "Producto.json" })
	public void crearComentarioTest() {
		Comentario c = new Comentario();
		c.setId_comentario(new Long(5));
		c.setProducto(entityManager.find(Producto.class, new Long(1234)));
		c.setTexto(null);
		c.setUsuario(entityManager.find(Usuario.class, "02"));
		
		
		entityManager.persist(c);
		
		Comentario a = entityManager.find(Comentario.class, new Long(5));
		
		Assert.assertNotNull(a);
	}
	/**
	 * permite probar eliminar un comentario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Comentario.json" })
	public void eliminarComentarioTest() {

		
		
		entityManager.remove(entityManager.find(Comentario.class, new Long(1)));
		
		Comentario a = entityManager.find(Comentario.class, new Long(1));
		
		Assert.assertNull(a);
	}
	/**
	 * permite probar obtener un comentario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Comentario.json", "Persona.json", "Producto.json" })
	public void obtenerComentarioTest() {
		Comentario c = entityManager.find(Comentario.class, new Long(2));
		Assert.assertNotNull(c);
	}

	/**
	 * permite probar obtener el nombre de una familia por especie
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Comentario.json"})
	public void modificarComentarioTest() {
		Comentario c = entityManager.find(Comentario.class, new Long(2));
		c.setTexto("Esta es una prueba");
		entityManager.merge(c);
		
		
		Assert.assertEquals("Esta es una prueba", c.getTexto());
	}
	
	
}
