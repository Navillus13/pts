package sps.db.tables.utils.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.GameSession;
import sps.db.tables.GameSessionQuestion;
import sps.db.tables.GameSessionUser;
import sps.db.tables.Question;
import sps.db.tables.Users;
import sps.db.tables.utils.GameSessionUtil;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.QuestionUtil;
import sps.db.tables.utils.UsersUtil;


public class GameSessionUtilTest extends TestCase {
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
		GameSessionUtil gsu = new GameSessionUtil(entityManager);
		UsersUtil uu = new UsersUtil(entityManager);
		long id;

		entityManager.getTransaction().begin();
		Users user = new Users("gamesessiontest@gamesessiontest.com", "PWN","game session","test",      //Name
				"352 E Forest Knoll Drive","","","","IL","Palatine","60074",   //Billing Address
				"352 E Forest Knoll Dr","","","","IL","Palatine","60074",     //ShippingAddress
				null);														   //User Sessions
		//Add the user
		assertNotNull(uu.addUser(user));
		assertTrue(user.getId() > 0);
		
		//Add the game session
		GameSession gameSession = new GameSession();
		gameSession.setCategory("NFL Football");
		gameSession.setDescription("NFL Fantasy Weekly League");
		gameSession.setMaxUsers(500);
		gameSession.setPerformerId(1);		
		gameSession = gsu.addGameSession(gameSession);
		id = gameSession.getId();
		assertTrue(id > 0);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		gameSession = null;
		gameSession = gsu.findGameSession(id);
		
		assertNotNull(gameSession);
		
		//Add a user to the gameSession
		user = uu.findUser(user.getId());
		GameSessionUser gameSessionUser = gsu.addGameSessionUser(gameSession, user);
		assertNotNull(gameSessionUser);
		
		//Test updating game session
		gameSession.setMaxUsers(300);
		gameSession = gsu.updateGameSession(gameSession);
		assertTrue(gameSession.getId() > 0);
		assertEquals(300, gameSession.getMaxUsers());
		entityManager.getTransaction().commit();
		
		//Test finding game sessions
		entityManager.getTransaction().begin();
		assertTrue(gsu.findGameSessionByPerformerId(1).size() > 0);
		
		QuestionUtil qu = new QuestionUtil(entityManager);

		//Create a question
		Question question = new Question();
		question.setCategory("RUSHING-YARDS");
		question.setPerformerId(1);
		question.setCorrectAnswer("");
		question.setAnswerNum(500);
		question.setAnswerType("NUMERIC");
		question.setDefaultAnswer("100");
		question.setQuestionText("How many rushing yards will Matt Forte have in week 1?");
		question = qu.addQuestion(question);
		entityManager.getTransaction().commit();
		
		//Add question to a game session
		entityManager.getTransaction().begin();
		GameSessionQuestion gameSessionQuestion = gsu.addGameSessionQuestion(gameSession, question);
		assertNotNull(gameSessionQuestion);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		gameSession = gsu.findGameSession(id);
		gameSession.setMaxUsers(1000);
		gameSession = gsu.updateGameSession(gameSession);
		assertEquals(1000, gameSession.getMaxUsers());
		assertTrue(gameSession.getGameSessionQuestions().size() > 0);
		entityManager.getTransaction().commit();

		
	}
	
}
