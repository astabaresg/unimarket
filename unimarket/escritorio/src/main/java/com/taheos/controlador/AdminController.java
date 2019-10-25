package com.taheos.controlador;

import com.jfoenix.controls.JFXButton;
import com.taheos.modelo.UsuarioObservable;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.util.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class AdminController {

	@FXML
	private JFXButton btnGestionarUsuario;

	@FXML
	private JFXButton btnListaProductos;

	@FXML
	private AnchorPane panelGestionarUsuario;

	@FXML
	private AnchorPane panelListaProductos;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblDireccion;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblCedula;

	@FXML
	private Label lblTelefono;

	@FXML
	private JFXButton btnAgregarUsuario;

	@FXML
	private JFXButton btnModificarUsuario;

	@FXML
	private JFXButton btnEliminarUsuario;

	private UsuarioObservable usuarioObservable;

	@FXML
	private TableView<UsuarioObservable> tablaUsuarios;

	@FXML
	private TableColumn<UsuarioObservable, String> columnCedula;

	@FXML
	private TableColumn<UsuarioObservable, String> columnNombre;

	@FXML
	private TableColumn<UsuarioObservable, String> columnDireccion;

	@FXML
	private TableColumn<UsuarioObservable, String> columnEmail;

	@FXML
	private TableColumn<UsuarioObservable, String> columnTelefono;

	private ManejadorEscenarios manejador;

	@FXML
	void initialize() {

		columnCedula.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getCedula());
		columnNombre.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getNombre());
		columnDireccion.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getDireccion());
		columnEmail.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getEmail());
		columnTelefono.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getNum_telefono());

		mostrarDetalleUsuario(null);

		tablaUsuarios.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleUsuario(newValue));
	}

	/**
	 * permite mostrar la informacion del usuario seleccionado
	 * 
	 * @param usuario usuario al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleUsuario(UsuarioObservable usuario) {

		if (usuario != null) {
			usuarioObservable = usuario;
			lblCedula.setText(usuarioObservable.getCedula().getValue());
			lblNombre.setText(usuarioObservable.getNombre().getValue());
			lblEmail.setText(usuarioObservable.getEmail().getValue());
			lblTelefono.setText(usuarioObservable.getNum_telefono().getValue());
			lblDireccion.setText(usuarioObservable.getDireccion().getValue());
		} else {
			lblCedula.setText("");
			lblNombre.setText("");
			lblEmail.setText("");
			lblTelefono.setText("");
			lblDireccion.setText("");
		}

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		tablaUsuarios.setItems(manejador.getUsuariosObservables());
	}

	@FXML
	void onAgregarUsuario(ActionEvent event) {

	}

	@FXML
	void onEliminarUsuario(ActionEvent event) {

		int indice = tablaUsuarios.getSelectionModel().getSelectedIndex();

		Usuario usuario = tablaUsuarios.getItems().get(indice).getUsuario();

		if (manejador.eliminarUsuario(usuario)) {
			tablaUsuarios.getItems().remove(indice);
			Utilidades.mostrarMensaje("Borrar", "El usuario ha sido eliminado con exito");
		} else {
			Utilidades.mostrarMensaje("Error", "El usuario no pudo ser eliminado");
		}

	}

	@FXML
	void onGestionarUsuario(ActionEvent event) {

		panelListaProductos.setVisible(false);
		panelGestionarUsuario.setVisible(true);

	}

	@FXML
	void onListaProductos(ActionEvent event) {

		panelGestionarUsuario.setVisible(false);
		panelListaProductos.setVisible(true);

	}

	@FXML
	void onModificarUsuario(ActionEvent event) {

	}

	/**
	 * permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

}