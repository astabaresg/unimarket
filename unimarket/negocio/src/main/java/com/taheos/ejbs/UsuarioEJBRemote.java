package com.taheos.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import com.taheos.unimarket.entidades.Comentario;
import com.taheos.unimarket.entidades.DetalleCompra;
import com.taheos.unimarket.entidades.Persona;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.entidades.Usuario;

@Remote
public interface UsuarioEJBRemote {

	String JNDI = "java:global/ear/negocio/UsuarioEJB!com.taheos.ejbs.UsuarioEJBRemote";

	/**
	 * Permite iniciar sesion a un usuario
	 * 
	 * @param email email de la persona que va a entrar
	 * @param clave clave de la persona que va a entrar
	 * @return la persona si existe registrada, null en caso contrario
	 */
	public Persona iniciarSesion(String email, String clave) throws Exception;

	/**
	 * Permite a un usuario registrarse s
	 * 
	 * @param usuario es la persona que desea registrarse
	 * @throws Exception clase que nos permite lanzar una excepcion
	 */
	public void registrarUsuario(Usuario usuario) throws Exception;

	/**
	 * permite buscar una personas usando su email
	 * 
	 * @param email email de la presona
	 * @return persona con el email especificado
	 */
	public Persona buscarPersonaPorEmail(String email);

	/**
	 * Permite registrar los productos que quiere vender un usuario
	 * 
	 * @param usu es el usuario al que queremos registrarle un producto
	 * @param pro es el producto que queremos registrar
	 * @throws Exception si el usuario no es tiene de rol vendedor, o si ya esta en
	 *                   su lista de productos
	 */
	public void registrarProductoVendido(Usuario usu, Producto pro) throws Exception;

	/**
	 * Permite listar los producto por su categoria
	 * 
	 * @return La lista de productos por producto
	 * @throws Exception
	 */
	public List<Producto> listaProductosGenero();

	/**
	 * Permite registrar los comentarios que quiere vender un usuario
	 * 
	 * @param pro    es el producto al que queremos registrarle el comentario
	 * @param coment es el Comentario que queremos registrar
	 * @param usu    es el usuario que hara el comentarios
	 */
	public void registrarComentarioProducto(Producto pro, Comentario coment, Usuario usu) throws Exception;

	/**
	 * Permite agregar un nuevo favorito a la lista de favoritos de un usuario
	 * 
	 * @param usu         es el ususaurio al que le vamosa gregar el nuevo favorito
	 * 
	 * @param productoFav es el producto que se desea se quiere agregar como
	 *                    favorito
	 */

	public void agregarListaFavorito(Usuario usu, Producto productoFav) throws Exception;

	/**
	 * Permite eliminar un elemento favorito de la lista de favoritos de un usuario
	 * 
	 * @param usu         es el usuario al que le removeremos el producto favorito
	 * @param id_producto es la Id de el producto que vamos a eliminar de la lista
	 *                    de productos favoritos de el ususario
	 * @throws Exception
	 */
	public void eliminarListaFavorito(Usuario usu, Long id_producto) throws Exception;

	/**
	 * Permite agregar varios detalleCompra a la lista de una compra segun su ID
	 * 
	 * @param usu      es el usuario que realiza la compra el cual debe estar en el
	 *                 ROl de vendedor
	 * @param idCompra es la id de la compra a la que agregaremos los detalleCompra
	 * @param dc       es la lista de detalleCompra que deseo agregar
	 * @throws Exception
	 */
	public void agregarProdutosCompra(Usuario usu, Long idCompra, ArrayList<DetalleCompra> dc) throws Exception;

	/**
	 * permite a un usuario darle una calificaciona un producto
	 * 
	 * @param usu     es el usuario que desea hacer la calificacion
	 * @param p       es el producto que calificaremos
	 * @param puntaje es el puntaje que el Usuario desea darle a el producto
	 * @throws Exception
	 */
	public void calificarProducto(Usuario usu, Producto p, double puntaje) throws Exception;
}
