/**
 * 
 */
package com.taheos.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Clase que contiene todos los metodos reusables
 * @author alvar
 *
 */
public class Util {

	/**
	 * Permite mostrar un mensaje en la pagina web
	 * 
	 * @param titulo titulo del mensaje
	 * @param mensaje texto a destacar
	 */
	public static void mostrarMensaje (String titulo, String mensaje) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje); 
		FacesContext.getCurrentInstance().addMessage(null, facesMsg); 

	}
	
	/**
	 * 
	 * @return
	 */
	public static ResourceBundle getResourceBundle () {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context,"msg");
		
		return bundle;
	}
}
