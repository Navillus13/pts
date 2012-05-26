package sps.db.tables.utils.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.GameSession;
import sps.db.tables.Question;
import sps.db.tables.utils.GameSessionUtil;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.QuestionUtil;


public class QuestionUtilTest extends TestCase {
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
		QuestionUtil qu = new QuestionUtil(entityManager);
		long id;

		//Add the user manually creating the record
		entityManager.getTransaction().begin();
		Question question = new Question();
		question.setCategory("RUSHING-YARDS");
		question.setPerformerId(1);
		question.setCorrectAnswer("");
		question.setAnswerNum(500);
		question.setAnswerType("NUMERIC");
		question.setDefaultAnswer("100");
		question.setQuestionText("How many rushing yards will Matt Forte have in week 1?");
		question = qu.addQuestion(question);
		
		id = question.getId();
		assertTrue(id > 0);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		question = null;
		question = qu.findQuestion(id);
				
		assertNotNull(question);
		
		//Test updating question
		question.setAnswerNum(123);
		question = qu.updateQuestion(question);
		assertTrue(question.getId() > 0);
		assertEquals(123.0, question.getAnswerNum());
		entityManager.getTransaction().commit();
		
		//Test finding questions
		entityManager.getTransaction().begin();
		assertTrue(qu.findQuestionByPerformerId(1).size() > 0);
		assertTrue(qu.findQuestionByCategory("RUSHING-YARDS").size() > 0);
		entityManager.getTransaction().commit();
	}
	
}
