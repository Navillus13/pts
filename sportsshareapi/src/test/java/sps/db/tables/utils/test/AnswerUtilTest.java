package sps.db.tables.utils.test;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.Answer;
import sps.db.tables.GameSession;
import sps.db.tables.GameSessionQuestion;
import sps.db.tables.GameSessionUser;
import sps.db.tables.Question;
import sps.db.tables.Users;
import sps.db.tables.utils.AnswerUtil;
import sps.db.tables.utils.GameSessionUtil;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.QuestionUtil;
import sps.db.tables.utils.UsersUtil;


public class AnswerUtilTest extends TestCase {
	private EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	private EntityManager entityManager;

	@Override
	protected void setUp() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	protected void tearDown() throws Exception {

	}

	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() throws Exception {
		AnswerUtil au = new AnswerUtil(entityManager);
		UsersUtil uu = new UsersUtil(entityManager);
		GameSessionUtil gsu = new GameSessionUtil(entityManager);
		QuestionUtil qu = new QuestionUtil(entityManager);
		
		long id;

		//Add the user manually creating the record
		entityManager.getTransaction().begin();
		//Add user
		Users user = new Users("answertest@testing.com", "answer","Answer","Test",      //Name
				"352 E Forest Knoll Drive","","","","IL","Palatine","60074",   //Billing Address
				"352 E Forest Knoll Dr","","","","IL","Palatine","60074",     //ShippingAddress
				null);														   //User Sessions
		assertNotNull(uu.addUser(user));
		assertTrue(user.getId() > 0);
		
		//Add Game Session
		GameSession gameSession = new GameSession();
		gameSession.setCategory("NFL Football");
		gameSession.setDescription("NFL Fantasy Weekly League");
		gameSession.setMaxUsers(500);
		gameSession.setPerformerId(1);		
		gameSession = gsu.addGameSession(gameSession);		
		assertTrue(gameSession.getId() > 0);			
		entityManager.getTransaction().commit();	

		entityManager.getTransaction().begin();
		GameSessionUser gameSessionUser = gsu.addGameSessionUser(gameSession, user);		
		Question question = new Question();
		question.setCategory("RUSHING-YARDS");
		question.setPerformerId(1);
		question.setCorrectAnswer("");
		question.setAnswerNum(500);
		question.setAnswerType("NUMERIC");
		question.setDefaultAnswer("100");
		question.setQuestionText("How many rushing yards will Matt Forte have in week 1?");
		question = qu.addQuestion(question);	
		assertTrue(question.getId() > 0);		
		GameSessionQuestion gameSessionQuestion = gsu.addGameSessionQuestion(gameSession, question);
		assertTrue(gameSessionQuestion.getId()>0);
		entityManager.getTransaction().commit();	
		
		entityManager.getTransaction().begin();		
		Answer answer = new Answer();
		answer = au.addAnswer(answer,question,gameSessionUser);		
		assertTrue(answer.getId() > 0);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		gameSessionUser = gsu.findGameSessionUserByIds(user.getId(), gameSession.getId());
		assertTrue(au.findAnswersByGameSessionUser(gameSessionUser).size()>0);
		assertNotNull(gameSessionUser.getAnswers());
		entityManager.close();
	
	}
	
}
