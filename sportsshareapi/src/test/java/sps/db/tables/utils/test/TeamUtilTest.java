package sps.db.tables.utils.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.Team;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.TeamUtil;


public class TeamUtilTest extends TestCase {
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
		TeamUtil tu = new TeamUtil(entityManager);
		long id;

		//Add the user manually creating the record
		entityManager.getTransaction().begin();
		Team team = new Team();
		team.setName("Chicago Bears");
		team.setLocation("Chicago, IL");
		team.setDescription("Chicago NFL Football team");
		team.setSport("NFL FOOTBALL");
		tu.addTeam(team);
		id = team.getId();
		assertTrue(id > 0);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		team = null;
		team = tu.findTeam(id);
				
		assertNotNull(team);
		
		//Test updating question
		team.setDescription("New Description");
		team = tu.updateTeam(team);
		assertTrue(team.getId() > 0);
		assertEquals("New Description", team.getDescription());
		entityManager.getTransaction().commit();
		
	}
	
}
