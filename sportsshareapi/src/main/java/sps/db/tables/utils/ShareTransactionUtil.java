package sps.db.tables.utils;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import sps.db.tables.ShareTransaction;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class ShareTransactionUtil {
	protected EntityManager entityManager;
	
	public ShareTransactionUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public static String STATUS_PENDING = "Pending";
	public static String STATUS_COMPLETE = "Complete";
	public static String STATUS_FAILED = "Failed";
	public static String STATUS_DENIED = "Denied";
	

	public ShareTransaction startShareTransaction(ShareTransaction userShare) {
		
		userShare.setCreateDate(Calendar.getInstance());
		userShare.setLastUpdateDate(Calendar.getInstance());
		
		//Add the transaction to the database
		userShare.setStatus(STATUS_PENDING);
		entityManager.persist(userShare);
		
        return userShare;
	}
	
	public ShareTransaction findShareTransaction(long id) {
		ShareTransaction userShare = null;
		try {
			userShare = entityManager.find(ShareTransaction.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return userShare;
	}
	
	public List<ShareTransaction> findShareTransactionsByUser(long userId) {
		String hql = "From ShareTransaction where user.id = :userId order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("userId", userId);
		List<ShareTransaction> result = q.getResultList();
		
		return result;
	}
	
	public List<ShareTransaction> findShareTransactionsByGame(long gameSessionId) {
		String hql = "From ShareTransaction where gameSessionId = :gameSessionId order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("gameSessionId", gameSessionId);
		List<ShareTransaction> result = q.getResultList();
		
		return result;
	}
	
	public List<ShareTransaction> findShareTransactionsByPerformer(long performerId) {
		String hql = "From ShareTransaction where performerId = :performerId order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("performerId", performerId);
		List<ShareTransaction> result = q.getResultList();
		
		return result;
	}	
	
	public List<ShareTransaction> findShareTransactionsByStatus(String status) {
		String hql = "From ShareTransaction where status = :status order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("status", status);
		List<ShareTransaction> result = q.getResultList();
		
		return result;
	}	
	
	
	public ShareTransaction completeShareTransaction(long id) throws Exception {
		ShareTransaction share = null;
		try {
			share = entityManager.find(ShareTransaction.class, id);
			//TODO: Other stuff here associated with finishing the transaction
			share.setStatus(STATUS_COMPLETE);
			entityManager.persist(share);
			return share;
		} catch (Exception ex) {
			throw new Exception("Failed to complete transaction: " + id);
		}
	}
	
	public ShareTransaction denyShareTransaction(long id) throws Exception {
		ShareTransaction share = null;
		try {
			share = entityManager.find(ShareTransaction.class, id);
			//TODO: Other stuff here associated with failing the transaction
			share.setStatus(STATUS_DENIED);
			entityManager.persist(share);
			return share;
		} catch (Exception ex) {
			throw new Exception("Failed to complete transaction: " + id);
		}
	}
	
	public ShareTransaction updateShareTransaction(ShareTransaction share) throws Exception {
		try {
			share.setLastUpdateDate(Calendar.getInstance());
			entityManager.persist(share);
			return share;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to update transaction: " + share.getId());
		}
	}

	
}
