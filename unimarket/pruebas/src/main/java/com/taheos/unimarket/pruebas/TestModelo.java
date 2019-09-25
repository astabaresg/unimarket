package com.taheos.unimarket.pruebas;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Rol;


/**
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestModelo {

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
	public void generarTest() {
		
	}

	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	public void crearUsuario() {

		Usuario u = new Usuario();
		u.setNombre("Sebastian");
		u.setId("12345");
		u.setDireccion("Oro negro ocaso");
		u.setCorreo("astg@mail.com");
		u.setClave("1212");
		u.setNum_telefono("31231232");
		u.setRol(Rol.VENDEDOR);
		
		entityManager.persist(u);
		
		Usuario aux = entityManager.find(Usuario.class, "12345");
		
		Assert.assertNotNull(aux);
	}

}
