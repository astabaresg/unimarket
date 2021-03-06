package com.taheos.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.taheos.modelo.AdministradorDelegado;
import com.taheos.modelo.ProductoObservable;
import com.taheos.modelo.UsuarioObservable;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Disponibilidad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Permite manejar los escenarios que tiene la aplicacion
 * 
 * @author com.taheos
 * @version 1.0
 */
public class ManejadorEscenarios{

	private double xOffSet, yOffSet;

	/**
	 * contenedor prinpipal de la aplicacion
	 */
	private Stage escenario,s2;
	/**
	 * tipo de panel inicial
	 */
	public Pane loginPane, adminPane;

	public static FXMLLoader loader, loginP;

	/**
	 * para almacenar usuarios observables
	 */
	private ObservableList<UsuarioObservable> usuariosObservables;
	/**
	 * para almacenar productos observables
	 */
	private ObservableList<ProductoObservable> productosObservables;
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


		administradorDelegado = AdministradorDelegado.administradorDelegado;
		usuariosObservables = FXCollections.observableArrayList();
		productosObservables = FXCollections.observableArrayList(); 
		this.escenario = escenario;

		try {
			
			// se inicializa el escenario
			// se carga la vista
			loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
			loginPane = loader.load();

			
			// permite mover la vista
			loginPane.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					xOffSet = event.getSceneX();
					yOffSet = event.getSceneY();

				}
			});

			loginPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					escenario.setX(event.getScreenX() - xOffSet);
					escenario.setY(event.getScreenY() - yOffSet);
				}
			});

			LoginController controlador = loader.getController();
			controlador.setManejador(this);

			// se carga la escena

			escenario.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(loginPane);
			scene.setFill(Color.TRANSPARENT);
			escenario.setScene(scene);
			escenario.show();
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			escenario.setX((primScreenBounds.getWidth() - escenario.getWidth()) / 2);
			escenario.setY((primScreenBounds.getHeight() - escenario.getHeight()) / 2);


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	public void cargarEscenaImagen(final File imageFile) {
        try { 
	           BorderPane borderPane = new BorderPane(); 
	           ImageView imageView = new ImageView(); 
	           Image image = new Image(new FileInputStream(imageFile)); 
	           imageView.setImage(image); 
	           imageView.setStyle("-fx-background-color: BLACK"); 
	           imageView.setFitHeight(s2.getHeight() - 10); 
	           imageView.setPreserveRatio(true); 
	           imageView.setSmooth(true); 
	           imageView.setCache(true); 
	           borderPane.setCenter(imageView); 
	           borderPane.setStyle("-fx-background-color: BLACK"); 
	           Stage newStage = new Stage(); 
	           newStage.setWidth(s2.getWidth()); 
	           newStage.setHeight(s2.getHeight()); 
	           newStage.setTitle(imageFile.getName()); 
	           Scene scene = new Scene(borderPane,Color.BLACK); 
	           newStage.setScene(scene); 
	           newStage.show(); 
	          } catch (FileNotFoundException e) { 
	           e.printStackTrace(); 
	          } 

	         
	        
	}
	/**
	 * muestra el escenario del administrador
	 */

	public void cargarEscenarioAdmin() {

		try {
			usuariosObservables = administradorDelegado.listarUsuariosObservables();
			productosObservables = administradorDelegado.listarProductosObservables();
			s2 = new Stage();
			loginP = new FXMLLoader(getClass().getResource("/views/adminView.fxml"));
			loginPane = loginP.load();
			((AdminController) loginP.getController()).setManejador(this);
			((AdminController) loginP.getController()).setEscenarioInicial(this);;

			s2.setScene(new Scene(loginPane));
			s2.setTitle("uniMarket");
			s2.show();
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			s2.setX((primScreenBounds.getWidth() - s2.getWidth()) / 2);
			s2.setY((primScreenBounds.getHeight() - s2.getHeight()) / 2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Generar usuarios observables
	 * 
	 * @return
	 */
	public ObservableList<UsuarioObservable> getUsuariosObservables() {
		return usuariosObservables;
	}

	
	public ObservableList<ProductoObservable> getProductosObservables() {
		return productosObservables;
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
	 * Permite modificar un usuario
	 */
	public boolean modificarUsuario(Usuario u) {
		return administradorDelegado.modificarUsuario(u);
	}
	/**
	 * permite registrar una persona en la base de datos
	 * 
	 * @param usuario a registrar
	 * @return true si quedo registrado
	 */
	public boolean registrarUsuario(Usuario usuario) {
		try {
			return administradorDelegado.registrarU(usuario);
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
	public boolean eliminarUsuario(Usuario usuario) {
		return administradorDelegado.eliminarUsuario(usuario);
	}
	
	public List<String> devolverCategorias(){
		return administradorDelegado.devolverCategorias();
	}

	public Categoria devolverCategoria (String nombre) {
		return administradorDelegado.devolverCategoria(nombre);
	}
	/**
	 * devuelve la lista de usuario que estan en la base de datos
	 * 
	 * @return todos los usuario
	 */
	public List<Usuario> listarUsuarios() {
		return administradorDelegado.listarUsuarios();
	}
	/**
	 * Permite listar todos los productos de una categoria
	 * 
	 * @param categoria
	 * @return
	 */
	public List<Producto> listarProductosCategoria(Categoria categoria) {
		return administradorDelegado.listarProductosCategoria(categoria);
	}
	
	/**
	 * Permite listar todos los productos
	 * 
	 * @return
	 */
	public List<Producto> listarTodosLosProductos() {
		return administradorDelegado.listarTodosLosProductos();
	}
	/**
	 * Permite ver el detalle de un producto especifico
	 * 
	 * @param codigo
	 * @return
	 */
	public String verDetalleProducto(String codigo) {
		return administradorDelegado.verDetalleProducto(codigo);
	}
	/**
	 * genera una lista de usuarios observables
	 * 
	 * @return todos los usuarios obsevables
	 */
	public ObservableList<UsuarioObservable> listarUsuariosObservables() {
		return administradorDelegado.listarUsuariosObservables();
	}
	/**
	 * Genera una lista de productos observables
	 * 
	 * @return
	 */
	public ObservableList<ProductoObservable> listarProductosObservables() {
		return administradorDelegado.listarProductosObservables();
	}
	/**
	 * Genera una lista de productos observables por categoria
	 * 
	 * @return
	 */
	public ObservableList<ProductoObservable> listarProductosObservablesCategoria(Categoria c) {
		return administradorDelegado.listarProductosObservablesCategoria(c);
	}
	/**
	 * Permite enviar el email a una persona
	 * @param email
	 */
	public void enviarCorreo(String email) {
		administradorDelegado.enviarCorreo(email);
	}
	
	public boolean iniciciarSesion (String email, String clave) {
		
		if(administradorDelegado.iniciarSesion(email, clave)== null) {
			return false;
		}else {
			return true;
		}
		
	}
	public AdministradorDelegado getAdministradorDelegado() {
		return administradorDelegado;
	}
	
}
