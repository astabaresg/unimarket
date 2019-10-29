package com.taheos.unimarket.pruebas;

import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.taheos.ejbs.AdminEJB;
import com.taheos.excepciones.ElementoNoEncontradoExcepcion;
import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Rol;
import com.taheos.util.Utilidades;

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
	public void crearUsuarioTest() {
		
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
	@UsingDataSet({ "Persona.json" })
	public void logginTest() {

		Persona p = adminEJB.iniciarSesion("taheos@gmail.com", "taheos123");
		
		Assert.assertNotNull(p);
	}
	
	@Test
	@UsingDataSet({ "Persona.json" })
	public void listarUsuariosTest() {
		
		List<Usuario> usuarios = adminEJB.listarUsuarios();
		
		Assert.assertNotNull(usuarios);
	}
	@Test
	@UsingDataSet({ "Producto.json" })
	public void listarProductosTest() {
		
		List<Producto> productos = adminEJB.listarProductosPorCategoria(Categoria.TECNOLOGIA);
		
		Assert.assertEquals( 2, productos.size());
	}
	@Test
	@UsingDataSet({ "Producto.json" })
	public void detalleProductoTest() {
		
		String detalle;
		try {
			detalle = adminEJB.verDetalleProducto("1234");
			Assert.assertEquals( "Pc ASUS Gamer", detalle);
		} catch (ElementoNoEncontradoExcepcion e) {
			e.printStackTrace();
		}
	}
//	@Test
//	@UsingDataSet({ "Persona.json" })
//	public void enviarCorreoTest() {
//		// Para la direccion nomcuenta@gmail.com
//				Properties props = System.getProperties();
//				props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
//				props.put("mail.smtp.user", "herbariomasteruq@gmail.com");
//				props.put("mail.smtp.clave", "unimarket"); // La clave de la cuenta
//				props.put("mail.smtp.auth", "true"); // Usar autenticaci�n mediante usuario y clave
//				props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
//				props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google
//				props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//				Session session = Session.getDefaultInstance(props);
//				MimeMessage message = new MimeMessage(session);
//				BodyPart texto = new MimeBodyPart();
//				MimeMultipart multiParte = new MimeMultipart();
//
//				try {
//					texto.setText(
//							"la clave asociada con el correo proporcionado es " + adminEJB.obtenerClave("astg0616@gmail.com"));
//					
//					multiParte.addBodyPart(texto);
//
//					message.setFrom(new InternetAddress("herbariomasteruq@gmail.com"));
//					message.addRecipients(Message.RecipientType.TO, "astg0616@gmail.com");
//
//					message.setSubject("Recuperacion de clave uniMarket:");
//					message.setText("prueba");
//					message.setContent(multiParte);
//
//					Transport transport = session.getTransport("smtp");
//					transport.connect("smtp.gmail.com", "herbariomasteruq@gmail.com", "unimarket");
//
//					transport.sendMessage(message, message.getAllRecipients());
//					transport.close();
//					
//					Utilidades.mostrarMensaje("Bien", "Se ha enviado el mensaje a su correo");
//
//				} catch (MessagingException | ElementoNoEncontradoExcepcion me) {
//					me.printStackTrace();
//					Utilidades.mostrarMensaje("Error", "No se ha podido enviar el mensaje");
//				}
//			}
}