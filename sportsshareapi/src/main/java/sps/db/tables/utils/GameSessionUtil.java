package sps.db.tables.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sps.db.tables.Answer;
import sps.db.tables.GameSession;
import sps.db.tables.GameSessionQuestion;
import sps.db.tables.GameSessionUser;
import sps.db.tables.Question;
import sps.db.tables.Users;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class GameSessionUtil {
	protected EntityManager entityManager;
	
	public GameSessionUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public GameSession addGameSession(GameSession gameSession) {
		gameSession.setCreateDate(Calendar.getInstance());
		gameSession.setLastUpdateDate(Calendar.getInstance());
		//Add the transaction to the database
		entityManager.persist(gameSession);
		
        return gameSession;
	}
	
	public GameSessionQuestion addGameSessionQuestion(GameSession gameSession, Question question) {
		GameSessionQuestion gameSessionQuestion = new GameSessionQuestion();
		
		//Add the transaction to the database
		gameSessionQuestion.setQuestion(question);		
		gameSessionQuestion.setGameSession(gameSession);
		gameSessionQuestion.setCreateDate(Calendar.getInstance());
		gameSessionQuestion.setLastUpdateDate(Calendar.getInstance());
		entityManager.persist(gameSessionQuestion);
		
        return gameSessionQuestion;
	}
	
	public GameSessionUser addGameSessionUser(GameSession gameSession, Users user) {
		GameSessionUser gameSessionUser = new GameSessionUser();
		
		gameSessionUser.setUser(user);
		gameSessionUser.setGameSession(gameSession);
		gameSessionUser.setLastUpdateDate(Calendar.getInstance());
		gameSessionUser.setCreateDate(Calendar.getInstance());
		entityManager.persist(gameSessionUser);
		
		return gameSessionUser;
		
	}

	public GameSession findGameSession(long id) {
		GameSession gameSession = null;
		try {
			gameSession = entityManager.find(GameSession.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return gameSession;
	}
	
	public List<GameSession> findGameSessionByPerformerId(long performerId) {
		String hql = "From GameSession where performerId = :performerId order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("performerId", performerId);
		List<GameSession> result = q.getResultList();
		
		return result;
	}
	
	public List<GameSession> findGameSessionByStartDate(Date dateMax, Date dateMin) {
		String hql = "From GameSession where startDate < :dateMax and startDate > :dateMin order by startDate";

		Query q = entityManager.createQuery(hql);
		q.setParameter("dateMax", dateMax);
		q.setParameter("dateMin", dateMin);
		List<GameSession> result = q.getResultList();
		
		return result;
	}	
	
	public List<GameSession> findAllGameSessions() {
		String hql = "From GameSession order by startDate";

		Query q = entityManager.createQuery(hql);

		List<GameSession> result = q.getResultList();
		
		return result;
	}	
	
	public GameSessionUser findGameSessionUserByIds(long userId, long gameSessionId ) {
		String hql = "From GameSessionUser where user.id = :userId and gameSession.id = :gameSessionId order by lastUpdateDate";
		GameSessionUser result = null;
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("userId", userId);
		q.setParameter("gameSessionId", gameSessionId);
		List<GameSessionUser> results = q.getResultList();
		
		if(results != null && results.size() > 0) {
			result = results.get(0);
		} 
		return result;
	}	
	
	public List<GameSessionUser> findGameSessionUsersByUser(long userId) {
		String hql = "From GameSessionUser where user.id = :userId order by lastUpdateDate";
		
		Query q = entityManager.createQuery(hql);
		q.setParameter("userId", userId);
		List<GameSessionUser> results = q.getResultList();
		
		return results;
	}	

	public GameSession updateGameSession(GameSession gameSession) throws Exception {
		
		try {
			gameSession.setLastUpdateDate(Calendar.getInstance());
			entityManager.persist(gameSession);
			return gameSession;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to update transaction: " + gameSession.getId());
		}
	}
	
	

	
}
