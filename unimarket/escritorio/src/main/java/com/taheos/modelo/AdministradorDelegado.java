package com.taheos.modelo;

import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.taheos.ejbs.AdminEJBRemote;
import com.taheos.excepciones.ElementoNoEncontradoExcepcion;
import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.util.Utilidades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Delegado que permite conectar la capa de negocio con la de presentación
 * 
 * @author com.taheos
 * @version 1.0
 */
public class AdministradorDelegado  {

	/**
	 * instancia que representa el ejb remoto de administrador
	 */
	private AdminEJBRemote adminEJB;
	/**
	 * permite manejar una unica instancia para le manejo de delegados
	 */
	public static AdministradorDelegado administradorDelegado = instancia();

	/**
	 * constructor para conectar con la capa de negocio
	 */
	private AdministradorDelegado() {
		try {
			adminEJB = (AdminEJBRemote) new InitialContext().lookup(AdminEJBRemote.JNDI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permite devolver una unica instancia de delegado
	 * 
	 * @return instancia unica del delegado
	 */
	private static AdministradorDelegado instancia() {

		if (administradorDelegado == null) {
			administradorDelegado = new AdministradorDelegado();
			return administradorDelegado;
		}
		return administradorDelegado;
	}


	public Persona iniciarSesion (String email, String clave) {
		return adminEJB.iniciarSesion(email, clave);
	}
	/**
	 * pemite registar un nuevo usuario
	 * 
	 * @param usuario empleado a agregar
	 * @return devuelve true si el usuario fue registrado
	 */
	public boolean registrarU(Usuario usuario) {
		try {
			return adminEJB.registrarUsuario(usuario) != null;
		} catch (ElementoRepetidoExcepcion e) {
			Utilidades.mostrarMensaje("Error al registrar un usuario", e.getMessage());
			return false;
		}
	}

	/**
	 * devuelve la lista de usuario que estan en la base de datos
	 * 
	 * @return todos los usuario
	 */
	public List<Usuario> listarUsuarios() {
		return adminEJB.listarUsuarios();
	}

	/**
	 * permite eliminar un usuario mediante su cedula
	 * 
	 * @return true si el usuario si fue eliminado
	 */
	public boolean eliminarUsuario(Usuario usuario) {
		try {
			return adminEJB.eliminarUsuario(usuario.getId());
		} catch (ElementoNoEncontradoExcepcion e) {
			Utilidades.mostrarMensaje("Error al eliminar un usuario", e.getMessage());
			return false;
		}
	}

	public boolean modificarUsuario(Usuario u) {
		try {
			return adminEJB.modificarUsuario(u)!=null;
		} catch (Exception e) {
			Utilidades.mostrarMensaje("Error al modificar un usuario", e.getMessage());
			return false;
		}
	}
	/**
	 * Permite listar todos los productos de una categoria
	 * 
	 * @param categoria
	 * @return
	 */
	public List<Producto> listarProductosCategoria(Categoria categoria) {
		return adminEJB.listarProductosPorCategoria(categoria);
	}

	/**
	 * Permite listar todos los productos
	 * 
	 * @return
	 */
	public List<Producto> listarTodosLosProductos() {
		return adminEJB.listarProductos();
	}

	/**
	 * Permite ver el detalle de un producto especifico
	 * 
	 * @param codigo
	 * @return
	 */
	public String verDetalleProducto(String codigo) {
		try {
			return adminEJB.verDetalleProducto(codigo);
		} catch (ElementoNoEncontradoExcepcion e) {
			Utilidades.mostrarMensaje("Error", e.getMessage());
			return null;
		}
	}

	/**
	 * genera una lista de usuarios observables
	 * 
	 * @return todos los usuarios obsevables
	 */
	public ObservableList<UsuarioObservable> listarUsuariosObservables() {
		List<Usuario> usuarios = adminEJB.listarUsuarios();
		ObservableList<UsuarioObservable> usuariosObservables = FXCollections.observableArrayList();
		for (Usuario usuario : usuarios) {
			usuariosObservables.add(new UsuarioObservable(usuario));
		}
		return usuariosObservables;
	}

	/**
	 * Genera una lista de productos observables
	 * 
	 * @return
	 */
	public ObservableList<ProductoObservable> listarProductosObservables() {
		List<Producto> productos = listarTodosLosProductos();
		ObservableList<ProductoObservable> productosObservables = FXCollections.observableArrayList();
		for (Producto producto : productos) {
			productosObservables.add(new ProductoObservable(producto));
		}
		return productosObservables;
	}
	
	/**
	 * Genera una lista de productos observables por categoria
	 * 
	 * @return
	 */
	public ObservableList<ProductoObservable> listarProductosObservablesCategoria(Categoria c) {
		List<Producto> productos = listarProductosCategoria(c);
		ObservableList<ProductoObservable> productosObservables = FXCollections.observableArrayList();
		for (Producto producto : productos) {
			productosObservables.add(new ProductoObservable(producto));
		}
		return productosObservables;
	}
	
	public void enviarCorreo(String email) {

		// Para la direccion nomcuenta@gmail.com
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", "herbariomasteruq@gmail.com");
		props.put("mail.smtp.clave", "herbariouniquindio"); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticaci�n mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		BodyPart texto = new MimeBodyPart();
		MimeMultipart multiParte = new MimeMultipart();

		try {
			texto.setText(
					"la clave asociada con el correo proporcionado es " + adminEJB.obtenerClave(email));
			
			multiParte.addBodyPart(texto);

			message.setFrom(new InternetAddress("herbariomasteruq@gmail.com"));
			message.addRecipients(Message.RecipientType.TO, email);

			message.setSubject("Recuperacion de clave uniMarket:");
			message.setText("prueba");
			message.setContent(multiParte);

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "herbariomasteruq@gmail.com", "herbariouniquindio");

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			Utilidades.mostrarMensaje("Bien", "Se ha enviado el mensaje a su correo");

		} catch (MessagingException | ElementoNoEncontradoExcepcion me) {
			me.printStackTrace();
			Utilidades.mostrarMensaje("Error", "No se ha podido enviar el mensaje");
		}
	}


}
