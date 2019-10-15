package com.taheos.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Permite manejar las operaciones generales de la capa de presentacion
 * @author com.taheos
 * @version 1.0
 */
public class Utilidades {

	/**
	 * permite mostrar un texto informativo en pantalla
	 * @param titulo subtitulo de la alerta
	 * @param mensaje mensaje principal
	 */
	public static void mostrarMensaje( String titulo, String mensaje ) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacion");
		alert.setHeaderText(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();	
	}
}
