package sps.db.tables.utils.test;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.ShareTransaction;
import sps.db.tables.Users;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.ShareTransactionUtil;
import sps.db.tables.utils.UsersUtil;


public class ShareTransactionUtilTest extends TestCase {
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
		ShareTransactionUtil usu = new ShareTransactionUtil(entityManager);
		UsersUtil uu = new UsersUtil(entityManager);
		Users user = null;
		long id;

		//Add the user manually creating the record
		entityManager.getTransaction().begin();
		ShareTransaction userShare = new ShareTransaction();
		userShare.setPerformerId(1);
		userShare.setPricePer(new BigDecimal(5));
		userShare.setPointsPer(10);
		userShare.setQuantity(2);
		user = uu.addUser("test@tesy.com","password");
		userShare.setUser(user);
		userShare.setGameSessionId(1);

		userShare =usu.startShareTransaction(userShare);
				
		id = userShare.getId();
		//Test Price and Points calculations
		assertEquals(10,userShare.getTotalPrice().intValue());		
		assertEquals(20, userShare.getTotalPoints());
		assertTrue(id > 0);	
		assertEquals("Pending", userShare.getStatus());
		entityManager.getTransaction().commit();
		
		entityManager.clear();  //Force fresh data
		entityManager.getTransaction().begin();
		userShare = null;
		userShare = usu.findShareTransaction(id);
		assertNotNull(userShare);
		assertEquals("Pending", userShare.getStatus());		
		
		//Test Price and Points calculations after find
		assertEquals(10,userShare.getTotalPrice().intValue());		
		assertEquals(20, userShare.getTotalPoints());
		
		//Test Completing transaction
		userShare = usu.completeShareTransaction(id);
		assertEquals("Complete", userShare.getStatus());
		entityManager.getTransaction().commit();
		
		//Test updating transaction
		entityManager.getTransaction().begin();
		userShare = usu.findShareTransaction(id);
		userShare.setPointsPer(90);
		userShare.setQuantity(5);
		userShare = usu.updateShareTransaction(userShare);
		assertTrue(userShare.getQuantity() > 0);
		assertEquals(90, userShare.getPointsPer());
		entityManager.getTransaction().commit();

		//Test denying transaction
		entityManager.clear();  //force fresh data
		entityManager.getTransaction().begin();
		userShare = null;
		userShare = usu.findShareTransaction(id);
		userShare = usu.denyShareTransaction(id);
		assertEquals("Denied", userShare.getStatus());
		entityManager.getTransaction().commit();
		
		//Test finding user transactions
		entityManager.getTransaction().begin();
		assertTrue(usu.findShareTransactionsByUser(user.getId()).size() > 0);
		assertTrue(usu.findShareTransactionsByGame(1).size() > 0);
		assertTrue(usu.findShareTransactionsByPerformer(1).size() > 0);
		assertTrue(usu.findShareTransactionsByStatus("Denied").size() > 0);
		
		//Make sure after finding lazy assocaitions are already intitialized
		user = uu.findUser(user.getId());
		assertNotNull(user.getShareTransactions());
		assertTrue(user.getShareTransactions().size() > 0);
		entityManager.getTransaction().commit();

	
	}
	
}
