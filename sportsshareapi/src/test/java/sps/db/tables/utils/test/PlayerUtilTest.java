package sps.db.tables.utils.test;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.Player;
import sps.db.tables.Team;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.PlayerUtil;
import sps.db.tables.utils.TeamUtil;


public class PlayerUtilTest extends TestCase {
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
		PlayerUtil pu = new PlayerUtil(entityManager);
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
		
		Player player = new Player();
		player.setLastName("Gordon");
		player.setFirstName("Scott");
		player.setAge(31);
		player.setBirthDate(Calendar.getInstance());
		player.setCollege("Northern Illinois University");
		player.setHeight(68);
		player.setNumber(66);
		player.setPosition("QB");
		player.setWeight(180);
		player.setYears(8);
		player.setTeam(team);
		pu.addPlayer(player).getId();
		id = player.getId();
		assertTrue(id > 0);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		player = null;
		player = pu.findPlayer(id);
				
		assertNotNull(player);
		
		//Test updating question
		player.setDescription("New Description");
		player = pu.updatePlayer(player);
		assertTrue(player.getId() > 0);
		assertEquals("New Description", player.getDescription());
		entityManager.getTransaction().commit();
		
	}
	
}
