package com.taheos.unimarket.pruebas;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.glassfish.enterprise.iiop.impl.PEORBConfigurator;
import org.glassfish.jersey.gf.ejb.internal.EjbComponentInterceptor;
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

import com.taheos.unimarket.entidades.Administrador;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Rol;


/**
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestTapasco {

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
	/**
	 * buscar Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void buscarPersona(){
		
		Persona a= entityManager.find(Persona.class, "02");
		System.out.println("La persona que trajo fue la : "+ a.getNombre());
		
		Assert.assertEquals("pepito@gmail.com", a.getCorreo());
		
	}
	/**
	 * registrar Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void crearAdministrador(){
		Persona  admin= new Administrador();
		admin.setNombre("nuevo");
		admin.setId("04");
		admin.setNum_telefono("322454353");
		admin.setClave("nuevopepito123");
		admin.setCorreo("nuevopepito@gmail.com");
		admin.setDireccion("la casa el nuevo cliente");
		
		entityManager.persist(admin);
		
		Administrador buscada= entityManager.find(Administrador.class, "04");
	
		Assert.assertEquals("nuevo", buscada.getNombre());
		
	}
	/**
	 * eliminar Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void borrarPersona(){
		
		entityManager.remove(entityManager.find(Persona.class, "02"));
	
		Assert.assertNull( entityManager.find(Persona.class, "02"));
		
	}
	/**
	 * eliminar Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void modificaPersona(){
		
		Persona cambioPersona=entityManager.find(Persona.class, "03");
		
		System.out.println("Correo viejo: " + cambioPersona.getCorreo());
		
		cambioPersona.setCorreo("nuevoCorreo@gmail.com");
		entityManager.merge(cambioPersona);
		
		Persona cambiado= entityManager.find(Persona.class, "03");
		System.out.println("Correo viejo: " + cambiado.getCorreo());
		
	}
	
	
}
