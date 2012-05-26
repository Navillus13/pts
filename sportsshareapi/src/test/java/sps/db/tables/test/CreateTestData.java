package sps.db.tables.test;

import java.math.BigDecimal;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import sps.db.tables.Performer;
import sps.db.tables.ShareTransaction;
import sps.db.tables.Users;
import sps.db.tables.utils.PerformerUtil;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.ShareTransactionUtil;
import sps.db.tables.utils.UsersUtil;

public class CreateTestData {

	/**
	 * Class used to generate a data set
	 * Allows us to create large sets of simple data in a small amount of time
	 * @param args
	 */
	
	private static long USER_MAX = 100;
	private static long PERFORMERS_MAX = 10;
	private static long SHARE_TRANSACTION_MAX = 500;
	
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		UsersUtil uu = new UsersUtil(entityManager);
		String emailDomain = "";
		String street = "";
		String city = "";
		String state = "";
		String firstName = "";
		String lastName = "";
		long requestUserId = 0;
		Random randomGenerator = new Random();
		//Create alot of users
		for(int i=0;i<USER_MAX;i++) {
			Users user = new Users();
			//Generate a few different domain names
			if(i%5 == 0) {
				emailDomain = "@something.com";
				street = "Walnut Avenue";
				city = "Schaumburg";
				state = "CA";
				firstName = "Scott";
				lastName = "Gordon";
			} else if(i%4 == 0) {
				emailDomain = "@hotmail.com";
				street = "Devonshire Lane";
				city = "Schaumburg";
				state = "IL";
				firstName = "John";
				lastName = "Doe";
			} else if(i%3 == 0) {
				emailDomain = "@gmail.com";
				street = "Daniel Drive";
				city = "Crystal Lake";
				state = "IL";
				firstName = "Sean";
				lastName = "Sullivan";
			} else if(i%2 == 0) {
				emailDomain = "@yahoo.com";
				street = "Stonbridge Lane";
				city = "Crystal Lake";
				state = "WI";
				firstName = "Whydon";
				lastName = "Chablowme";
			} else {
				emailDomain = "@email.com";
				street = "Alameda Avenue";
				city = "Chicago";
				state = "IL";			
				firstName = "Paint";
				lastName = "Sniffer";
			}
			user.setEmail("user" + i + emailDomain);
			user.setPassword("user" + i);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			int streetNumber = randomGenerator.nextInt(99999);
			int zip = randomGenerator.nextInt(99999);
			user.setBillingAddress1(streetNumber + " " + street);
			user.setBillingCity(city);
			user.setBillingState(state);
			user.setBillingZip(zip + "");
			user.setShippingAddress1(streetNumber + " " + street);
			user.setShippingCity(city);
			user.setShippingState(state);
			user.setShippingZip(zip + "");

			requestUserId = uu.addUser(user).getId();
			
			//Performers
			PerformerUtil pu = new PerformerUtil();
			Performer performer = new Performer();
			performer.setName("Chicago Bears");
			performer.setCategory("NFL Football");
			performer.setSharePrice(new BigDecimal(randomGenerator.nextInt(5)));
			performer.setDescription("The NFL team hailing from the great city of Chicago");
			pu.addPerformer(performer);
			
			performer = new Performer();
			performer.setName("Detroit Lions");
			performer.setCategory("NFL Football");
			performer.setSharePrice(new BigDecimal(randomGenerator.nextInt(5)));
			performer.setDescription("Detroit Sucks, but i like the show Hardcore Pawn");
			pu.addPerformer(performer);
			
			performer = new Performer();
			performer.setName("Green Bay Packers");
			performer.setCategory("NFL Football");
			performer.setSharePrice(new BigDecimal(randomGenerator.nextInt(5)));
			performer.setDescription("Green 'Theres nothing else to do here' Bay");
			pu.addPerformer(performer);
			
			performer = new Performer();
			performer.setName("Minnesota Vikings");
			performer.setCategory("NFL Football");
			performer.setSharePrice(new BigDecimal(randomGenerator.nextInt(5)));
			performer.setDescription("Adrian Peterson");
			pu.addPerformer(performer);
			
					
			//Share Transactions
			for(i = 0;i<SHARE_TRANSACTION_MAX;i++) {
				ShareTransactionUtil su = new ShareTransactionUtil(entityManager);
				ShareTransaction share = new ShareTransaction();
				share.setPerformerId(randomGenerator.nextInt(4));
				share.setQuantity(randomGenerator.nextInt(100));
				share.setPricePer(new BigDecimal(randomGenerator.nextDouble() * 10));
				share.setPointsPer(randomGenerator.nextInt(10));
				share.setUser(uu.findUser(randomGenerator.nextInt((int) USER_MAX)));
				su.startShareTransaction(share);
				if(i%3 == 0) {
					try {
						su.completeShareTransaction(share.getId());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
			}
			
			

		}
	System.out.println("Data successfully created!");
	
	}

}
