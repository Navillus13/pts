package sps.db.tables.utils.test;

import java.math.BigDecimal;

import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;
import sps.db.tables.Performer;
import sps.db.tables.utils.PerformerUtil;


public class PerformerUtilTest extends TestCase {
	private EntityManagerFactory entityManagerFactory;

	@Override
	protected void setUp() throws Exception {

	}

	@Override
	protected void tearDown() throws Exception {

	}

	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() {
		boolean cleanup = true;  //True for most scenarios, false if you want to keep all data in the databse after the test has run
		PerformerUtil pu = new PerformerUtil();
		long id;

		//Add the user manually creating the record
		Performer performer = new Performer();
		performer.setName("Chicago Bears");
		performer.setCategory("NFL Football Team");
		performer.setMaxShares(100000);
		performer.setSharePrice(new BigDecimal(1));
		performer.setDescription("The best team in the NFL....in 1985");
		id = pu.addPerformer(performer);
		assertTrue(id > 0);		
		
		//Find the user by email
		performer = null;
		performer = pu.findPerformer(id);
		assertNotNull(performer);
		
	}
	
}
