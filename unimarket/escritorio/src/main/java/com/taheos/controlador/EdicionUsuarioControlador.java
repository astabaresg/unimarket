package com.taheos.controlador;

import com.taheos.modelo.UsuarioObservable;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Rol;
import com.taheos.util.Utilidades;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Permite controlar la vista editar_usuario
 * 
 * @author com.taheos version 1.0
 */
public class EdicionUsuarioControlador {

	/**
	 * campo para la cedula
	 */
	@FXML
	private TextField cmpCedula;
	/**
	 * campo para el nombre
	 */
	@FXML
	private TextField cmpNombre;

	/**
	 * campo para el email
	 */
	@FXML
	private TextField cmpEmail;

	/**
	 * campo para la fecha de nacimiento
	 */
	@FXML
	private TextField cmpNum_Telefono;
	/**
	 * campo para la direccion
	 */
	@FXML
	private TextField cmpDireccion;
	/**
	 * representa el escenario en donde se agrega la vista
	 */
	private Stage escenarioEditar;
	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	/**
	 * 
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * permite cargar la informacion de un persona para realizar una edicion
	 * 
	 * @param empleado empleado a editar
	 */
	public void cargarPersona(UsuarioObservable usuario) {

		cmpCedula.setText(usuario.getCedula().getValue());
		cmpNombre.setText(usuario.getNombre().getValue());
		cmpEmail.setText(usuario.getEmail().getValue());
		cmpNum_Telefono.setText(usuario.getNum_telefono().getValue());
		cmpDireccion.setText(usuario.getDireccion().getValue());
	}

	/**
	 * permite registrar una persona en la base de datos
	 */
	@FXML
	public void registrarPersona() {

		Usuario usuario = new Usuario();

		usuario.setCorreo(cmpEmail.getText());
		usuario.setDireccion(cmpDireccion.getText());
		usuario.setId(cmpCedula.getText());
		usuario.setNombre(cmpNombre.getText());
		usuario.setNum_telefono(cmpNum_Telefono.getText());
		usuario.setRol(Rol.COMPRADOR);

		if (manejador.registrarUsuario(usuario)) {
			manejador.agregarALista(usuario);
			Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
			escenarioEditar.close();
		} else {
			Utilidades.mostrarMensaje("Registro", "Error en registro!!");
		}
	}

	/**
	 * permite editar la informacion de una persona
	 */
	@FXML
	private void modificar() {
		// TODO terminar modificar empleado
		escenarioEditar.close();
	}

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioEditar.close();
	}

	/**
	 * permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

	/**
	 * permite modificar el escenario
	 * 
	 * @param escenarioEditar
	 */
	public void setEscenarioEditar(Stage escenarioEditar) {
		this.escenarioEditar = escenarioEditar;
	}

}
