package com.taheos.ejbs;

import java.util.List;

import javax.ejb.Remote;

import com.taheos.excepciones.ElementoNoEncontradoExcepcion;
import com.taheos.excepciones.ElementoRepetidoExcepcion;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;
import com.taheos.unimarket.enums.Categoria;

/**
 * Interfaz que representa las transacciones que se pueden realizar desde la
 * capa de presentación
 * 
 * @author Alvaro Sebastian Tabares Gaviria
 * @version 1.0
 *
 */
@Remote
public interface AdminEJBRemote {

	String JNDI = "java:global/ear/negocio/AdminEJB!com.taheos.ejbs.AdminEJBRemote";

	Persona iniciarSesion(String email, String clave);

	Usuario registrarUsuario(Usuario usuario) throws ElementoRepetidoExcepcion;

	boolean eliminarUsuario(String cedula) throws ElementoNoEncontradoExcepcion;

	Usuario modificarUsuario(Usuario usuario) throws ElementoNoEncontradoExcepcion;

	Usuario buscarUsuario(String cedula) throws ElementoNoEncontradoExcepcion;

	List<Usuario> listarUsuarios();

	/**
	 * Metodo que permite obtener la clave de un usuario
	 * @param cedula
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	String obtenerClave(String email) throws ElementoNoEncontradoExcepcion;
	/**
	 * Sirve para registrar un producto
	 * 
	 * @param usuario
	 * @return
	 * @throws ElementoRepetidoExcepcion
	 * @throws ElementoNoEncontradoExcepcion
	 */
	Producto registrarProducto(Producto producto) throws  ElementoNoEncontradoExcepcion;

	/**
	 * Metodo para eliminar un producto de la base de datos
	 * 
	 * @param codigo
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	boolean eliminarProducto(String codigo) throws ElementoNoEncontradoExcepcion;

	/**
	 * Metodo que permite modificar un usuario
	 * 
	 * @param usuario
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	Producto modificarProducto(Producto producto) throws ElementoNoEncontradoExcepcion;

	/**
	 * Metodo que permite buscar un Producto
	 * @param codigo
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	Producto buscarProducto(String codigo) throws ElementoNoEncontradoExcepcion;
	/**
	 * Metodo que sirve para listar todos los Productos registrados
	 * @return
	 */
	List<Producto> listarProductos();
	/**
	 * Permite listar los productos registrados por categoria
	 * @param categoria
	 * @return
	 */
	List<Producto> listarProductosPorCategoria (Categoria categoria);
	/**
	 * Permite ver el detalle de un producto en especifico
	 * @param codigo
	 * @return
	 * @throws ElementoNoEncontradoExcepcion
	 */
	String verDetalleProducto(String codigo) throws ElementoNoEncontradoExcepcion;
	/**
	 * Retorna las categorias de productos disponibles
	 * @return
	 */
	List<String> devolverCategorias();
	
	/**
	 * Retorna una categoria a partir de un nombre
	 * @param nombre
	 * @return
	 */
	Categoria devolverCategoria (String nombre) ;
}
