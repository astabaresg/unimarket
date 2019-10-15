package com.taheos.unimarket.pruebas;

import java.util.Date;

import javax.ejb.EJB;
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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.taheos.ejbs.AdminEJB;
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
import com.taheos.unimarket.enums.MetodoPago;
import com.taheos.unimarket.enums.Rol;

/**
 * Clase de pruebas dedicada para la pruebas de las entidades
 * 
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class TestNegocio {


	/**
	 * EJB que se desea probar
	 */
	@EJB
	private AdminEJB adminEJB;


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
		return ShrinkWrap.create(JavaArchive.class).addClass(AdminEJB.class).addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
//////////////////////////////////////
	/*
	 * Administrador
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
		
		try {
			adminEJB.registrarUsuario(u);
		} catch (ElementoRepetidoExcepcion e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void loggin() {

		Persona p = adminEJB.iniciarSesion("astg0616@gmail.com", "12345");
		
		Assert.assertNotNull(p);
	}
	
}