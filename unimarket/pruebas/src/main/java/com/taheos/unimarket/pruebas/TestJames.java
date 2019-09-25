package com.taheos.unimarket.pruebas;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
	public void agregarProductoTest() {
		
		Producto nuevoP = new Producto();
		nuevoP.setId(1000301L);
		nuevoP.setNombre("Reloj Rolex");
		nuevoP.setDescripcion("Reloj Rolex Original");
		nuevoP.setPrecio(1000000.0);
		nuevoP.setDisponibilidad(Disponibilidad.DISPONIBLE);
		nuevoP.setCategoria(Categoria.JOYAS);
		nuevoP.setCalificacion(10);
		
		
	}
}
