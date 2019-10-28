package com.taheos.controlador;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.taheos.util.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginController {
	
    @FXML
    private AnchorPane escenarioLogin;

    @FXML
    private JFXTextField textUser;

    @FXML
    private JFXPasswordField textPassword;

    @FXML
    private JFXButton buttonLogin;
    
	private ManejadorEscenarios manejador;


    public FXMLLoader loginP;
    public static Pane loginPane;
	

    @FXML
    void onLoginButton(ActionEvent event) throws IOException {


//    	if(manejador.iniciciarSesion(textUser.getText(), textPassword.getText())) {
//
//    	}else {
//    		Utilidades.mostrarMensaje("Error", "Datos incorrectos, por favor verifique");
//    	}
    	manejador.getEscenario().close();
    	manejador.cargarEscenarioAdmin();
    	
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
