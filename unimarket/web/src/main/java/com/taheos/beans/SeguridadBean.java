package com.taheos.beans;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.taheos.ejbs.UsuarioEJB;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.util.Util;


/**
 * Permite manejar la sesion en la aplicacion web
 * 
 * @author com.taheos
 *
 */
@FacesConfig(version = Version.JSF_2_3)
@Named("seguridadBean")
@SessionScoped
public class SeguridadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Usario que inicia sesion
	 */
	private Persona usuario;
	/**
	 * dice si la persona inicio sesion o no
	 */
	private boolean autenticado;

	private String email;
	private boolean usuarios;

	/**
	 * referencia a la capa de negocio
	 */
	@EJB
	private UsuarioEJB usuarioEJB;
	/**
	 * inicializar la informacion base de la sesion
	 */

	@PostConstruct
	private void init() {
		usuario = new Persona();
		autenticado = false;
	}

	/**
	 * permite iniciar sesión
	 */
	public void iniciarSesion() {

		Persona u;
		try {
			u = usuarioEJB.iniciarSesion(usuario.getCorreo(), usuario.getClave());
			if (u == null) {
				Util.mostrarMensaje("No se pudo iniciar sesion verifique sus credenciales",
						"No se pudo iniciar sesion verifique sus credenciales");
			} else {
				usuario = u;
				autenticado = true;

				if (u instanceof Usuario) {
					usuarios = true;
				} else {
					Util.mostrarMensaje("No se pudo iniciar sesion verifique sus credenciales",
							"No se pudo iniciar sesion verifique sus credenciales");
				}
			}
		} catch (Exception e) {
			Util.mostrarMensaje("No se pudo iniciar sesion verifique sus credenciales",
					"No se pudo iniciar sesion verifique sus credenciales");
		}
	}

	/**
	 * permite cerrar sesion
	 */
	public String cerrarSesion() {
		if (usuario != null) {
			usuario = null;
			autenticado = false;

			usuarios = false;

			init();
		}
		
		return "/index";
	}

	/**
	 * Metodo para recuperar la contrasenia
	 * 
	 * @param destinatario
	 * @param asunto
	 * @param cuerpo
	 * @return
	 */

	public String enviarCorreo() {
		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente tambi�n.

		// Para la direcci�n nomcuenta@gmail.com
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
					"la clave asociada con el correo proporcionado es ");
			
			multiParte.addBodyPart(texto);

			message.setFrom(new InternetAddress("herbariomasteruq@gmail.com"));
			message.addRecipients(Message.RecipientType.TO, email);

			message.setSubject("Recuperacion de clave Herbario Universidad del quindio:");
			message.setText("prueba");
			message.setContent(multiParte);

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "herbariomasteruq@gmail.com", "herbariouniquindio");

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			Util.mostrarMensaje("Enhorabuena", "Se ha enviado el mensaje a su correo");

			return "/index";
		} catch (MessagingException me) {
			me.printStackTrace();
		}

		Util.mostrarMensaje("Error", "Paila");

		return "/index";
	}

	public Persona getUsuario() {
		return usuario;
	}

	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	

	public boolean isUsuarios() {
		return usuarios;
	}

	public void setUsuarios(boolean usuarios) {
		this.usuarios = usuarios;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
