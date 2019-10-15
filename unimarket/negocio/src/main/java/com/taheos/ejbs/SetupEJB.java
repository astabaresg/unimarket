package com.taheos.ejbs;


import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.taheos.unimarket.entidades.Administrador;


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

		}

	}

}
