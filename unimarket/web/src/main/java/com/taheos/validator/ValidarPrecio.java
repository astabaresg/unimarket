package com.taheos.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("vPrecio")
public class ValidarPrecio implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String precio=value.toString().trim();
		
		if(precio.length()==0) {
			throw new ValidatorException(new FacesMessage("Este campo  no puede estar vacio"));
		}
		
		try {
			Double.parseDouble(precio);
			} catch (Exception e) {
			throw new ValidatorException(new FacesMessage("El precio solo puede llevar numeros"));
		}
	}

	
	
}
