package com.taheos.controlador;

import java.io.IOException;

import com.sun.enterprise.module.bootstrap.Main;
import com.taheos.modelo.AdministradorDelegado;
import com.taheos.modelo.UsuarioObservable;
import com.taheos.unimarket.entidades.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Permite manejar los escenarios que tiene la aplicacion
 * 
 * @author com.taheos
 * @version 1.0
 */
public class ManejadorEscenarios {

	/**
	 * contenedor prinpipal de la aplicacion
	 */
	private Stage escenario;
	/**
	 * tipo de panel inicial
	 */
	private BorderPane bordePanel;
	/**
	 * para almacenar usuarios observables
	 */
	private ObservableList<UsuarioObservable> usuariosObservables;
	/**
	 * conexion con capa de negocio
	 */
	private AdministradorDelegado administradorDelegado;

	/**
	 * recibe el escenario principla de la aplicacion
	 * 
	 * @param escenario inicial
	 */
	public ManejadorEscenarios(Stage escenario) {

		this.escenario = escenario;

		administradorDelegado = AdministradorDelegado.administradorDelegado;
		usuariosObservables = FXCollections.observableArrayList();

		try {
			// se inicializa el escenario
			escenario.setTitle("UniMarket");

			// se carga la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/inicio.fxml"));

			bordePanel = (BorderPane) loader.load();

			// se carga la escena
			Scene scene = new Scene(bordePanel);
			escenario.setScene(scene);
			escenario.show();

			cargarEscena();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * carga una escena en el centro del escenario
	 */
	public void cargarEscena() {

		try {

			
			usuariosObservables = administradorDelegado.listarEmpleadosObservables();

			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(Main.class.getResource("../vista/detalle_usuario.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader2.load();
			bordePanel.setCenter(panelAncho);

			UsuarioControlador controlador = loader2.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * muestra el escenario para crear un empleado nuevo
	 */
	public void cargarEscenarioCrearEmpleado() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/editar_usuario.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Crear");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			EdicionUsuarioControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenarioEditar(escenarioCrear);
			empleadoControlador.setManejador(this);

			// se crea el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	/**
	 * Generar usuarios observables
	 * @return
	 */
	public ObservableList<UsuarioObservable> getUsuariosObservables() {
		return usuariosObservables;
	}

	/**
	 * permite agrega un usuario a la lista obsevable
	 * 
	 * @param empleado
	 */
	public void agregarALista(Usuario usuario) {
		usuariosObservables.add(new UsuarioObservable(usuario));
	}

	/**
	 * deveulve una instancia del escenario
	 * 
	 * @return escenario
	 */
	public Stage getEscenario() {
		return escenario;
	}

	/**
	 * permite registrar una persona en la base de datos
	 * 
	 * @param usuario a registrar
	 * @return true si quedo registrado
	 */
	public boolean registrarUsuario(Usuario usuario) {
		try {
			return administradorDelegado.registrarUsuario(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * permite eliminar un usuario
	 * 
	 * @param usuario a ser eliminado
	 * @return true si fue eliminado false si no
	 */
	public boolean eliminarEmpleado(Usuario usuario) {
		return administradorDelegado.eliminarUsuario(usuario);
	}

}
