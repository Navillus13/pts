package sps.db.tables.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import sps.db.tables.GameSession;
import sps.db.tables.Question;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class QuestionUtil {
	protected EntityManager entityManager;
	
	public QuestionUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Question addQuestion(Question question) {
				
		question.setCreateDate(Calendar.getInstance());
		question.setLastUpdateDate(Calendar.getInstance());
		//Add the transaction to the database
		entityManager.persist(question);
		
        return question;
	}
	
	public Question findQuestion(long id) {
		Question question = null;
		try {
			question = entityManager.find(Question.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return question;
	}
	
	public List<Question> findQuestionByPerformerId(long performerId) {
		String hql = "From Question where performerId = :performerId order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("performerId", performerId);
		List<Question> result =  (List<Question>) q.getResultList();
		
		return result;
	}
	
	public List<Question> findQuestionByCategory(String category) {
		String hql = "From Question where category = :category order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("category", category);
		List<Question> result =  (List<Question>) q.getResultList();
		
		return result;
	}
	
	public Question updateQuestion(Question question) throws Exception {
		try {
			question.setLastUpdateDate(Calendar.getInstance());
			entityManager.persist(question);
			return question;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to update question: " + question.getId());
		}
	}

	
}
