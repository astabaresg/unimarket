package com.taheos.controlador;

import com.taheos.modelo.UsuarioObservable;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.util.Utilidades;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author com.taheos
 */
public class UsuarioControlador {

	/**
	 * table donde se almacena la informacion de los usuarios
	 */
	@FXML
	private TableView<UsuarioObservable> tablaUsuarios;
	/**
	 * hace referencia a la columna con las cedulas
	 */
	@FXML
	private TableColumn<UsuarioObservable, String> cedulaColumna;
	/**
	 * hace referencia a la columna de los nombres de los usuarios
	 */
	@FXML
	private TableColumn<UsuarioObservable, String> nombreColumna;
	/**
	 * etiqueta de cedula
	 */
	@FXML
	private Label txtCedula;
	/**
	 * etiqueta de nombre
	 */
	@FXML
	private Label txtNombre;

	/**
	 * etiqueta de email
	 */
	@FXML
	private Label txtEmail;

	/**
	 * etiqueta de numero de telefono
	 */
	@FXML
	private Label txtNum_Telefono;
	/**
	 * Etiqueta de direccion del usuario
	 */
	@FXML
	private Label txtDireccion;
	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios escenarioInicial;

	private UsuarioObservable usuarioObservable;

	public UsuarioControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		cedulaColumna.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getCedula());
		nombreColumna.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getNombre());

		mostrarDetalleEmpleado(null);

		tablaUsuarios.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleEmpleado(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
		tablaUsuarios.setItems(escenarioInicial.getUsuariosObservables());
	}

	/**
	 * permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param empleado empleado al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleEmpleado(UsuarioObservable usuario) {

		if (usuario != null) {
			usuarioObservable = usuario;
			txtCedula.setText(usuarioObservable.getCedula().getValue());
			txtNombre.setText(usuarioObservable.getNombre().getValue());
			txtEmail.setText(usuarioObservable.getEmail().getValue());
			txtNum_Telefono.setText(usuarioObservable.getNum_telefono().getValue());
			txtDireccion.setText(usuarioObservable.getDireccion().getValue());
		} else {
			txtCedula.setText("");
			txtNombre.setText("");
			txtEmail.setText("");
			txtNum_Telefono.setText("");
			txtDireccion.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar usuario
	 */
	@FXML
	public void agregarUsuario() {
		escenarioInicial.cargarEscenarioCrearEmpleado();
		tablaUsuarios.refresh();
	}

	/**
	 * permite eliminar un usuario seleccionado
	 */
	@FXML
	public void eliminarUsuario() {

		int indice = tablaUsuarios.getSelectionModel().getSelectedIndex();

	
		Usuario usuario = tablaUsuarios.getItems().get(indice).getUsuario();

		if (escenarioInicial.eliminarEmpleado(usuario)) {
			tablaUsuarios.getItems().remove(indice);
			Utilidades.mostrarMensaje("Borrar", "El usuario ha sido eliminado con exito");
		} else {
			Utilidades.mostrarMensaje("Error", "El usuario no pudo ser eliminado");
		}

	}

}
