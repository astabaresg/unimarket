package com.taheos.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("vEntero")
public class ValidarEntero implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String numS=value.toString();
		
		try {
			Integer.parseInt(numS);
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage("Los caracteres ingresados no son numeros"));
		}
		
	}

	
	
}
