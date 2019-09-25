package com.taheos.unimarket.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comentario
 *
 */
@NamedQueries({ @NamedQuery(name = Comentario.LISTAR_TODOS, query = "select c from Comentario c"),
	@NamedQuery(name = Comentario.LISTAR_POR_CREDENCIALES, query = "select c from Comentario c where c.id_comentario =:id_comentario")})
@Entity
public class Comentario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	/**
	 * constante que representa la consulta LISTARTODOS
	 */
	public static final String LISTAR_TODOS = "ListarComentarios";
	/**
	 * constante que representa la consulta LISTARPORCREDENCIALES
	 */
	public static final String LISTAR_POR_CREDENCIALES = "ListarComentariosPorCredenciales";

	/**
	 * Identificador unico de la compra
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long id_comentario;
	
	/**
	 * Usuario que genera el comentario
	 */
	@ManyToOne
	private Usuario usuario;
	
	/**
	 * Producto comentado
	 */
	@ManyToOne
	private Producto producto;
	
	/**
	 * Cuerpo del comentario
	 */
	private String texto;
	public Comentario() {
		super();
	}
	public Long getId_comentario() {
		return id_comentario;
	}
	public void setId_comentario(Long id_comentario) {
		this.id_comentario = id_comentario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_comentario == null) ? 0 : id_comentario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		if (id_comentario == null) {
			if (other.id_comentario != null)
				return false;
		} else if (!id_comentario.equals(other.id_comentario))
			return false;
		return true;
	}
   
}
