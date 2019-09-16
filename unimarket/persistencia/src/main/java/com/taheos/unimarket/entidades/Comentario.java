package com.taheos.unimarket.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comentario
 *
 */
@Entity

public class Comentario implements Serializable {

	
	private static final long serialVersionUID = 1L;


	/**
	 * Identificador unico de la compra
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id_comentario;
	
	public Comentario() {
		super();
	}
   
}
