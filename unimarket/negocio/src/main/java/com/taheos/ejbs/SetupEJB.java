package com.taheos.ejbs;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.taheos.unimarket.entidades.Administrador;
import com.taheos.unimarket.entidades.Producto;
import com.taheos.unimarket.enums.Categoria;
import com.taheos.unimarket.enums.Disponibilidad;


/**
 * Session Bean implementation class SetupEJB
 */
@Singleton
@LocalBean
@Startup
public class SetupEJB {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public SetupEJB() {
	}

	/**
	 * Permite registrar un administrador si no hay aún registrados
	 */
	@PostConstruct
	public void init() {

		TypedQuery<Long> query = entityManager.createNamedQuery(Administrador.CONTAR_ADMIN, Long.class);
		long numeroAdmins = query.getSingleResult();

		if (numeroAdmins == 0) {

			Administrador admin = new Administrador();
			admin.setClave("12345");
			admin.setCorreo("astg0616@gmail.com");
			admin.setDireccion("Oro negro");
			admin.setId("123456");
			admin.setNombre("Alvaro Sebastian Tabares Gaviria");
			admin.setNum_telefono("31232132");
			
			entityManager.persist(admin);
			
			String img1 = "/src/main/resources/img/pc.jpg";
			
			ArrayList<String> im1 = new ArrayList<String>();
			
			im1.add(img1);
			
			SimpleDateFormat converter = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date a = converter.parse("12-12-2019");
				Producto p1 = new Producto();
				p1.setCantidad(5);
				p1.setCategoria(Categoria.TECNOLOGIA);
				p1.setDescripcion("PC gamer");
				p1.setDisponibilidad(Disponibilidad.DISPONIBLE);
				p1.setFecha_limite(a);
				p1.setId(new Long(2));
				p1.setImagen(im1);
				p1.setNombre("PC Gamer");
				p1.setPrecio(123000);
			
				
				entityManager.persist(p1);
			} catch (ParseException e) {
				e.printStackTrace();
			}


		}
	}

}
