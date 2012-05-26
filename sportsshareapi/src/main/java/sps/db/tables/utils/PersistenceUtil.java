package sps.db.tables.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class PersistenceUtil {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public static EntityManagerFactory getEntityManagerFactory() {
		if(entityManagerFactory == null) {
			// EntityManagerFactory is set up once for an application
			// IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
			entityManagerFactory = Persistence.createEntityManagerFactory( "sps.db.jpa" );
		}
		return entityManagerFactory;
	}
	
	public static EntityManager getEntityManager() {

		entityManager = getEntityManagerFactory().createEntityManager();
		
		return entityManager;
	}
	

	
}
