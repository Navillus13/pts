package sps.db.tables.utils;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import sps.db.tables.Performer;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class PerformerUtil {
	private EntityManagerFactory entityManagerFactory;
	
	
	public PerformerUtil() {
		entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	}

	public long addPerformer(Performer performer) {
		EntityManager entityManager = PersistenceUtil.getEntityManager();
		
		performer.setCreateDate(Calendar.getInstance());
		performer.setLastUpdateDate(Calendar.getInstance());
		
		//Add the user to the database
		entityManager.getTransaction().begin();
		entityManager.persist(performer);
		entityManager.getTransaction().commit();
		entityManager.close();
		
        return performer.getId();
	}
	
	public Performer findPerformer(long id) {
		EntityManager entityManager = PersistenceUtil.getEntityManager();
		Performer performer = null;
		try {
			entityManager.getTransaction().begin();
			performer = entityManager.find(Performer.class, id);
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return performer;
	}

	
}
