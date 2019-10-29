package com.taheos.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.taheos.modelo.ProductoObservable;
import com.taheos.modelo.UsuarioObservable;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Rol;
import com.taheos.util.Utilidades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AdminController {

	@FXML
	private JFXButton btnGestionarUsuario;

	@FXML
	private JFXButton btnListaProductos;

	@FXML
	private VBox contenedorUsuarioDetalle;

	@FXML
	private VBox contenedorUsuarioModificar;

	@FXML
	private VBox contenedorUsuarioCrear;

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
	private Label lblCedulaModificar;

	@FXML
	private Label lblNombreProducto;

	@FXML
	private Label lblPrecio;

	@FXML
	private Label lblCategoria;

	@FXML
	private Label lblDisponibilidad;

	@FXML
	private Label lblFecha;

	@FXML
	private JFXTextField txtNombre;

	@FXML
	private JFXTextField txtDireccion;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextField txtTelefono;

	@FXML
	private JFXTextField txtCrearNombre;

	@FXML
	private JFXTextField txtCrearCedula;

	@FXML
	private JFXTextField txtCrearDireccion;

	@FXML
	private JFXTextField txtCrearEmail;

	@FXML
	private JFXTextField txtCrearTelefono;

	@FXML
	private JFXPasswordField txtCrearContrasena;

	@FXML
	private JFXButton btnAgregarUsuario;

	@FXML
	private JFXButton btnModificarUsuario;

	@FXML
	private JFXButton btnEliminarUsuario;

	@FXML
	private JFXButton btnModificarUsuarioDone;

	@FXML
	private JFXButton btnCrearUsuarioDone;

	@FXML
	private JFXButton btnCancelarCrear;

	@FXML
	private JFXButton btnCancelarModificar;

	private UsuarioObservable usuarioObservable;
	private ProductoObservable productoObservable;

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

	@FXML
	private TableView<ProductoObservable> tablaProducto;

	@FXML
	private TableColumn<ProductoObservable, String> columnNombreProducto;

	@FXML
	private TableColumn<ProductoObservable, String> columnPrecioProducto;

	@FXML
	private TableColumn<ProductoObservable, String> columnCategoriaProducto;

	@FXML
	private TableColumn<ProductoObservable, String> columnDisponibilidadProducto;

	@FXML
	private TableColumn<ProductoObservable, String> columnFechaProducto;

	@FXML
	private JFXComboBox<String> comboBoxProductos;

	@FXML
	private ImageView lblImagen;
	private ManejadorEscenarios manejador;

	private ObservableList<String> productosItems = FXCollections.observableArrayList("Todos","Tecnologia", "Deporte", "Moda",
			"Libros", "Joyas");

	@FXML
	void initialize() {

		columnCedula.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getCedula());
		columnNombre.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getNombre());
		columnDireccion.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getDireccion());
		columnEmail.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getEmail());
		columnTelefono.setCellValueFactory(usuarioCelda -> usuarioCelda.getValue().getNum_telefono());

		comboBoxProductos.setItems(productosItems);
		mostrarDetalleUsuario(null);

		tablaUsuarios.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleUsuario(newValue));

		columnNombreProducto.setCellValueFactory(productoCelda -> productoCelda.getValue().getNombre());
		columnPrecioProducto.setCellValueFactory(productoCelda -> productoCelda.getValue().getPrecio());
		columnCategoriaProducto.setCellValueFactory(productoCelda -> productoCelda.getValue().getCategoria());
		columnDisponibilidadProducto.setCellValueFactory(productoCelda -> productoCelda.getValue().getDisponibilidad());
		columnFechaProducto.setCellValueFactory(productoCelda -> productoCelda.getValue().devolverFechaLimite());

		tablaProducto.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleProducto(newValue));

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
	 * permite mostrar la informacion del usuario seleccionado
	 * 
	 * @param usuario usuario al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleProducto(ProductoObservable producto) {

		if (producto != null) {
			productoObservable = producto;

			lblNombreProducto.setText(producto.getNombre().getValue());
			lblPrecio.setText(producto.getPrecio().getValue());
			lblCategoria.setText(producto.getCategoria().getValue());
			lblDisponibilidad.setText(producto.getDisponibilidad().getValue());
			lblFecha.setText(producto.getFechaLimite().getValue().toString());

			String img = "/Users/astar/git/unimarket/unimarket/escritorio/src/main/resources/img/pc.jpg";
			File path = new File(img);
			createImageView(path);

		} else {
			lblNombreProducto.setText("");
			lblPrecio.setText("");
			lblCategoria.setText("");
			lblDisponibilidad.setText("");
			lblFecha.setText("");
		}

	}

	/**
	 * OnAction botones del panel principal del administrador
	 */

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

	/**
	 * OnAction botones del panel detallesUsuario
	 */

	@FXML
	void onAgregarUsuario(ActionEvent event) {

		contenedorUsuarioDetalle.setVisible(false);
		contenedorUsuarioCrear.setVisible(true);

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
	void onModificarUsuario(ActionEvent event) {

		contenedorUsuarioDetalle.setVisible(false);
		contenedorUsuarioModificar.setVisible(true);

		txtDireccion.setText(lblDireccion.getText());
		txtEmail.setText(lblEmail.getText());
		txtNombre.setText(lblNombre.getText());
		txtTelefono.setText(lblTelefono.getText());
		lblCedulaModificar.setText(lblCedula.getText());

	}

	/**
	 * OnAction botones del panel modificarUsuario
	 */

	@FXML
	void onCancelarModificar(ActionEvent event) {

		contenedorUsuarioModificar.setVisible(false);
		contenedorUsuarioDetalle.setVisible(true);
		txtDireccion.setText("");
		txtEmail.setText("");
		txtNombre.setText("");
		txtTelefono.setText("");
		lblCedulaModificar.setText("");

	}

	@FXML
	void onModificarUsuarioDone(ActionEvent event) {
		Usuario u = new Usuario();
		u.setNombre(txtNombre.getText());
		u.setDireccion(txtDireccion.getText());
		u.setCorreo(txtEmail.getText());
		u.setNum_telefono(txtTelefono.getText());
		u.setId(lblCedulaModificar.getText());
		u.setRol(Rol.COMPRADOR);

		if (manejador.modificarUsuario(u)) {
			Utilidades.mostrarMensaje("Borrar", "El usuario ha sido modificado con exito");
			actualizarTabla();
			txtDireccion.setText("");
			txtEmail.setText("");
			txtNombre.setText("");
			txtTelefono.setText("");
			lblCedulaModificar.setText("");
			contenedorUsuarioDetalle.setVisible(true);
			contenedorUsuarioModificar.setVisible(false);
		} else {
			Utilidades.mostrarMensaje("Error", "El usuario no pudo ser modificado");
		}
	}

	/**
	 * OnAction botones del panel crearUsuario
	 */

	@FXML
	void onCrearUsuarioDone(ActionEvent event) {

		Usuario u = new Usuario();
		u.setClave(txtCrearContrasena.getText());
		u.setCorreo(txtCrearEmail.getText());
		u.setDireccion(txtCrearDireccion.getText());
		u.setId(txtCrearCedula.getText());
		u.setNombre(txtCrearNombre.getText());
		u.setNum_telefono(txtCrearTelefono.getText());
		u.setRol(Rol.COMPRADOR);

		if (manejador.registrarUsuario(u)) {
			Utilidades.mostrarMensaje("Exito", "El usuario se ha registrado correctamente");
			actualizarTabla();
			contenedorUsuarioDetalle.setVisible(true);
			contenedorUsuarioCrear.setVisible(false);
			txtCrearCedula.setText("");
			txtCrearContrasena.setText("");
			txtCrearDireccion.setText("");
			txtCrearEmail.setText("");
			txtCrearNombre.setText("");
			txtCrearTelefono.setText("");

		} else {
			Utilidades.mostrarMensaje("Error", "Fallo algo a la hora de registrar");
		}
	}

	@FXML
	void onCancelarCrear(ActionEvent event) {

		contenedorUsuarioCrear.setVisible(false);
		contenedorUsuarioDetalle.setVisible(true);
		txtCrearCedula.setText("");
		txtCrearContrasena.setText("");
		txtCrearDireccion.setText("");
		txtCrearEmail.setText("");
		txtCrearNombre.setText("");
		txtCrearTelefono.setText("");

	}

	/**
	 * Filtra los productos por categoria
	 * @param event
	 */
	@FXML
	void onComboProductos(ActionEvent event) {

		
		if(comboBoxProductos.getValue().equals("Todos")) {
			
		}
		
		if(comboBoxProductos.getValue().equals("Tecnologia")) {
			
		}
		
		if(comboBoxProductos.getValue().equals("Deporte")) {
			
		}
		
		if(comboBoxProductos.getValue().equals("Moda")) {
			
		}
		
		if(comboBoxProductos.getValue().equals("Libros")) {
			
		}
		
		if(comboBoxProductos.getValue().equals("Joyas")) {
			
		}
		
	}

	public void actualizarTabla() {
		tablaUsuarios.setItems(null);
		tablaUsuarios.setItems(manejador.listarUsuariosObservables());
	}

	/**
	 * permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		tablaUsuarios.setItems(null);
		tablaUsuarios.setItems(manejador.listarUsuariosObservables());
		tablaProducto.setItems(null);
		tablaProducto.setItems(manejador.getProductosObservables());
	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		tablaUsuarios.setItems(null);
		tablaUsuarios.setItems(manejador.listarUsuariosObservables());
		tablaProducto.setItems(null);
		tablaProducto.setItems(manejador.getProductosObservables());
	}

	private void createImageView(final File imageFile) {
		// DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
		// The last two arguments are: preserveRatio, and use smooth (slower)
		// resizing

		try {
			final Image image = new Image(new FileInputStream(imageFile), 150, 0, true, true);
			lblImagen.setImage(image);
			lblImagen.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

						if (mouseEvent.getClickCount() == 2) {

							manejador.cargarEscenaImagen(imageFile);

						}
					}
				}
			});
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}