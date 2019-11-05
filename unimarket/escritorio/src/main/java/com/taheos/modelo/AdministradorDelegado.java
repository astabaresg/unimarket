package com.taheos.modelo;

import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
public class AdministradorDelegado {

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

	public Persona iniciarSesion(String email, String clave) {
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
	 * pemite registar un nuevo usuario
	 * 
	 * @param usuario empleado a agregar
	 * @return devuelve true si el usuario fue registrado
	 */
	public boolean registrarP(Producto p) {
		try {
			return adminEJB.registrarProducto(p) != null;
		} catch (ElementoNoEncontradoExcepcion e) {
			Utilidades.mostrarMensaje("Error al registrar un producto", e.getMessage());
			return false;
		}
	}

	/**
	 * devuelve la lista de usuario que estan en la base de datos
	 * 
	 * @return todos los usuario
	 */
	public List<Usuario> listarUsuarios() {
		try {
			return adminEJB.listarUsuarios();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

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
			return adminEJB.modificarUsuario(u) != null;
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
		List<Usuario> usuarios = listarUsuarios();
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
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");

		Session sesion = Session.getDefaultInstance(propiedad);
		String correoEnvia = "unimarketuq@gmail.com";
		String contrasena = "unimarket1234";
		String receptor = email;
		String asunto = "Recuperacion contraseña.";
		String mensaje;
		try {
			mensaje = "La contraseña asociada a la cuenta " + email + " es " + adminEJB.obtenerClave(email);
			MimeMessage mail = new MimeMessage(sesion);
			mail.setFrom(new InternetAddress(correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			mail.setSubject(asunto);
			mail.setText(mensaje);

			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contrasena);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
			transportar.close();

			Utilidades.mostrarMensaje("Exito", "El correo ha sido enviado correctamente");

		} catch (ElementoNoEncontradoExcepcion | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Utilidades.mostrarMensaje("Error", "El correo no se ha podido enviar correctamente");
		}

	}

	public List<String> devolverCategorias() {
		return adminEJB.devolverCategorias();
	}

	public Categoria devolverCategoria(String nombre) {
		return adminEJB.devolverCategoria(nombre);
	}
}
