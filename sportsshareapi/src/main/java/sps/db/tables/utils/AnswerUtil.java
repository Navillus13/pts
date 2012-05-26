package sps.db.tables.utils;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sps.db.tables.Answer;
import sps.db.tables.GameSessionUser;
import sps.db.tables.Question;
import sps.db.tables.ShareTransaction;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class AnswerUtil {
	protected EntityManager entityManager;
	
	public AnswerUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Answer addAnswer(Answer answer, Question question, GameSessionUser gameSessionUser) {

		answer.setQuestion(question);
		answer.setGameSessionUser(gameSessionUser);
		answer.setCreateDate(Calendar.getInstance());
		answer.setLastUpdateDate(Calendar.getInstance());
		entityManager.persist(answer);
		
        return answer;
	}
	
	public Answer findAnswer(long id) {
		Answer answer = null;;
		try {
			answer = entityManager.find(Answer.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return answer;
	}
	
	public List<Answer> findAnswersByGameSessionUser(GameSessionUser gameSessionUser) {
		List<Answer> result = null;
		String hql = "";
		try {
			hql = "From Answer where gameSessionUser = :gameSessionUser";
			Query q = entityManager.createQuery(hql);
			q.setParameter("gameSessionUser", gameSessionUser);
			
			result = q.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}

	
	public Answer updateAnswer(Answer answer) throws Exception {
		try {
			answer.setLastUpdateDate(Calendar.getInstance());
			entityManager.persist(answer);
			return answer;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to update question: " + answer.getId());
		}
	}

	
}
