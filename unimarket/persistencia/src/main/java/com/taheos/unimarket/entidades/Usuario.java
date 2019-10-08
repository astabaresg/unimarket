package com.taheos.unimarket.entidades;

import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.enums.Rol;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */


@Entity
@NamedQueries({@NamedQuery(name = Usuario.BUSCAR_USUARIO_POR_CORREO, query = "select u from Usuario u where u.correo =:correo"),
	           @NamedQuery(name = Usuario.LISTAR_TODOS_LOS_USUARIOS, query = "select u from Usuario u")})
public class Usuario extends Persona implements Serializable {



	/**
	 * Constante que representa la consulta listar todo los usuarios
	 */
	public static final String LISTAR_TODOS_LOS_USUARIOS= "UsuariosListados";
	/**
	 * constante que representa la consulta PersonaPorCorreo
	 */
	public static final String BUSCAR_USUARIO_POR_CORREO = "UsuarioPorCorreo";

	/**
	 * Rol del usuario
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Rol rol;
	/**
	 * Compras realizadas por un usuario
	 */
	@OneToMany(mappedBy = "usuario")
	private List<Compra> compras;

	/**
	 * Lista de favoritos de un usuario
	 */
	@OneToMany(mappedBy = "usuario")
	private List<Favorito> favoritos;

	/**
	 * Comentarios hechos por un usuario
	 */
	@OneToMany(mappedBy = "usuario")
	private List<Comentario> comentarios;

	/**
	 * Calificaciones que ha generado un usuario
	 */
	@OneToMany(mappedBy = "usuario")
	private List<Calificacion> calificaciones;

	/**
	 * Productos subidos por un usuario en el rol de vendedor
	 */
	@OneToMany(mappedBy = "usuario")
	private List<Producto> productos;
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public List<Favorito> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
