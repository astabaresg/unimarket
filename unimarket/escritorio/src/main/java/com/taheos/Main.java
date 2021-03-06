package com.taheos;

import com.taheos.controlador.ManejadorEscenarios;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Clase encargada de iniciar la ejecución de la aplicacion y cargar la ventana
 * principal
 * 
 * @author com.taheos
 * @version 1.0
 */
public class Main extends Application {

	/**
	 * Se lanza la aplicaicion
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Se carga y edita el escenario inicial de la app
	 * 
	 * @param primaryStage escenario de a la aplicacion
	 * @throws Exception en caso de no poder cargar el escenario
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		new ManejadorEscenarios(primaryStage);
	}

}
