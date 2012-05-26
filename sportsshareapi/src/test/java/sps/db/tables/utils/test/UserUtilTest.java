package sps.db.tables.utils.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.Users;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.UsersUtil;


public class UserUtilTest extends TestCase {
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
	public void testBasicUsage() {
		boolean cleanup = true;  //True for most scenarios, false if you want to keep all data in the databse after the test has run
		UsersUtil uu = new UsersUtil(entityManager);

		//Add the user manually creating the record
		entityManager.getTransaction().begin();
		Users user = new Users("scott@scott.com", "PWN","Scott","Gordon",      //Name
				"352 E Forest Knoll Drive","","","","IL","Palatine","60074",   //Billing Address
				"352 E Forest Knoll Dr","","","","IL","Palatine","60074",     //ShippingAddress
				null);														   //User Sessions
		assertNotNull(uu.addUser(user));
		assertTrue(user.getId() > 0);
		entityManager.getTransaction().commit();		
		
		//Add a quick user with only e-mail address and password
		entityManager.getTransaction().begin();
		assertNotNull(uu.addUser("simple@somple.com", "pass"));
		entityManager.getTransaction().commit();
		
		//Attempt login with user
		entityManager.getTransaction().begin();
		assertNotNull(uu.login("scott@scott.com", "PWN", "SOMESESSION"));
		entityManager.getTransaction().commit();
				
		//Find the user by email
		entityManager.clear();
		entityManager.getTransaction().begin();
		user = uu.findUser("scott@scott.com");
		assertNotNull(user);		
		assertTrue(user.getBillingAddress1().length() > 0);
		assertTrue(user.getShippingAddress1().length() > 0);		
		//Make sure a session was created
		assertNotNull(user.getUserSessions());
		entityManager.getTransaction().commit();
		
		//Login to a second session
		entityManager.getTransaction().begin();
		assertNotNull(uu.login("scott@scott.com", "PWN", "SOMESESSION2"));
		entityManager.getTransaction().commit();
		
		//Log the user out of the first session
		entityManager.getTransaction().begin();
		assertTrue(uu.logout(user.getId(), "SOMESESSION"));
		entityManager.getTransaction().commit();
		
		//Attempt to login with an invalid user

		entityManager.getTransaction().begin();
		assertNull(uu.login("invalid@invalid.com", "invalid", "invalid"));
		entityManager.getTransaction().commit();		
		
		//Make sure only the one correct session remains
		entityManager.getTransaction().begin();		
		assertFalse(uu.isValidSession(user.getId(), "SOMESESSION"));
		assertTrue(uu.isValidSession(user.getId(), "SOMESESSION2"));
		entityManager.getTransaction().commit();
		
		
		//Update the users password to NEWPASS
		entityManager.getTransaction().begin();
		user = uu.updateUser(user.getId(), "", "NEWPASS");
		assertTrue(user.getPassword().equals("NEWPASS"));
		//Be sure the email address doesn't change since we passed in blank
		assertTrue(user.getEmail().equals("scott@scott.com"));		
		user.setId(user.getId());
		user.setEmail("NEWAGAIN");
		user.setPassword("PASSNEW2");
		user.setBillingAddress1("123 noob street");
		user.setBillingCity("Las Vegas");
		user.setBillingState("NV");
		user.setBillingZip("55540");
		user.setFirstName("NEWFIRST");
		user.setLastName("NEWLAST");
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		Users user2 = uu.updateUser(user);
		assertNotNull(user2);
		assertEquals("123 noob street", user2.getBillingAddress1());
		entityManager.getTransaction().commit();
		
		if(cleanup) {
			//Remove the user
			entityManager.getTransaction().begin();
			assertTrue(uu.removeUser(user.getId()));
			entityManager.getTransaction().commit();
		}
	}
	
}
