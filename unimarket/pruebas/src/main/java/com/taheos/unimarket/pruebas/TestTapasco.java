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
import com.taheos.unimarket.entidades.Calificacion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
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
	
	//////////////////////////////////////
	/*
	* Persona 	
	*/
	//////////////////////////////////////	

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
	 * Modificar Administrador
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void modificarUsuario(){
		
		Usuario cambioPersona=entityManager.find(Usuario.class, "03");
		
		System.out.println("Correo viejo: " + cambioPersona.getCorreo());
		
		cambioPersona.setCorreo("nuevoCorreo@gmail.com");
		entityManager.merge(cambioPersona);
		
		Usuario cambiado= entityManager.find(Usuario.class, "03");
		System.out.println("Correo viejo: " + cambiado.getCorreo());
		
	}
	/**
	 * buscar Usuario
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void buscarUsuario(){
		
		Usuario a= entityManager.find(Usuario.class, "02");
		System.out.println("La persona que trajo fue la : "+ a.getNombre());
		
		Assert.assertEquals("pepito@gmail.com", a.getCorreo());
		
	}
	/**
	 * eliminar Usuario
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void borrarUsuario(){
		
		entityManager.remove(entityManager.find(Usuario.class, "02"));
	
		Assert.assertNull( entityManager.find(Usuario.class, "02"));
		
	}
	/**
	 * registrar Adminsitrador
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
	 * eliminar Administrador
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void borrarAdministrador(){
		
		entityManager.remove(entityManager.find(Administrador.class, "01"));
	
		Assert.assertNull( entityManager.find(Administrador.class, "01"));
		
	}
	/**
	 * Modificar Administrador
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Persona.json"})
	public void modificarAdministrador(){
		
		Administrador cambioPersona=entityManager.find(Administrador.class, "01");
		
		System.out.println("Correo viejo: " + cambioPersona.getCorreo());
		
		cambioPersona.setCorreo("nuevoCorreo@gmail.com");
		entityManager.merge(cambioPersona);
		
		Administrador cambiado= entityManager.find(Administrador.class, "01");
		System.out.println("Correo viejo: " + cambiado.getCorreo());
		
	}
	
	//////////////////////////////////////
	/*
	 * Calificacion 	
	 */
	//////////////////////////////////////
	/**
	 * buscar calificicaion
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Calificacion.json"})
	public void buscarCalificacion(){
	
		Calificacion a= entityManager.find(Calificacion.class, 100L);
		System.out.println("La calificiacion que trajo fue la : "+ a.getId_calificacion());
		Assert.assertNotNull("Es null", a);
		
	}
	
	/**
	 * registrar Calificacion
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Calificacion.json"})
	public void crearCalificacion(){
		
		Calificacion nueva= new Calificacion();
		
		nueva.setId_calificacion(103L);
		nueva.setProducto(new Producto());
		nueva.setPuntaje(33.9);
		nueva.setUsuario(new Usuario());
		
		entityManager.persist(nueva);
		
		Calificacion buscada= entityManager.find(Calificacion.class, 103L);
	
		Assert.assertNotNull("No hallo a : " + 103L,buscada);
		
	}
	
	/**
	 * modificar calificacion
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Calificacion.json"})
	public void modificarCalificacion(){
	
		Calificacion a= entityManager.find(Calificacion.class, 100L);
		System.out.println("puntaje actual:  " + a.getPuntaje());
		a.setPuntaje(100.0);
		
		entityManager.merge(a);
		
		Calificacion b= entityManager.find(Calificacion.class, 100L);
		System.out.println("puntaje posterior:  " + b.getPuntaje());
		
	}
	
	/**
	 * eliminar calificacion
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"Calificacion.json"})
	public void eliminarCalificacion(){
	
		entityManager.remove(entityManager.find(Calificacion.class, 102L));
		
		Calificacion buscada=entityManager.find(Calificacion.class, 102L);
		
		Assert.assertNotNull("Si eliminio ",buscada);
		
	}
	
}
