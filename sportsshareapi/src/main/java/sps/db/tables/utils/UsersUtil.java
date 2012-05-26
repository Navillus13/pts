package sps.db.tables.utils;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sps.db.tables.UserSession;
import sps.db.tables.Users;


/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

//TODO: Add ability to send in message(String, XML, JSON, etc) to utility class and process accordingly


public class UsersUtil {
	protected EntityManager entityManager;
	
	public UsersUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	/**
	 * Verify that login parameters are correct and update login tables accordingly
	 * @param email
	 * @param password
	 * @return userId if success
	 */
	public Users login(String email, String password, String sessionId)
	{
		//Check to see if the login is valid
		String hql = "From Users where email = :email and password = :password";
		Query q = entityManager.createQuery(hql);
		
		q.setParameter("email", email);
		q.setParameter("password", password);
		List result = q.getResultList();
		for (Users user : (List<Users>) result ) {
			System.out.println("Users ( " + user.getEmail() + ", " + user.getPassword() + ", " + user.getCreateDate() + ", " + user.getLastUpdateDate() + ")" );
			user.setLastLoginDate(Calendar.getInstance());
			entityManager.persist(user);
	        //Add the user session
	        entityManager.persist(new UserSession(user, sessionId));
	        return user;
		}
		return null;
	}
	
	public boolean logout(long userId, String sessionId)
	{
		boolean success = true;
		
		UserSession userSession = null;
		//Find Session to remove
		String hql = "From UserSession where userId = :userId and sessionId = :sessionId";
		Query q = entityManager.createQuery(hql);
		q.setParameter("userId", userId);
		q.setParameter("sessionId", sessionId);
		userSession = (UserSession) q.getSingleResult();
		entityManager.remove(userSession);
		
        return success;
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	public Users addUser(Users user) {
		
		user.setCreateDate(Calendar.getInstance());
		user.setLastUpdateDate(Calendar.getInstance());
		entityManager.persist(user);
		
        return user;
	}
	
	/**
	 * Add a user to the database
	 * @param email
	 * @param password
	 * @return true if add user sucess, false if failed
	 */
	public Users addUser(String email, String password)
	{
		Users user = new Users(email, password);
			
		return addUser(user);
	}
	
	/**
	 * update a user
	 * @param email
	 * @param password
	 * @return true if add user sucess, false if failed
	 */
	public Users updateUser(long userId, String email, String password)
	{
		Users user = entityManager.find(Users.class, userId);

		if(email != null && email.length() > 0) {
			user.setEmail(email);
		}
		
		if(password != null && password.length() > 0) {
			user.setPassword(password);
		}
		
		user = updateUser(user);
		
		return user;
	}
	
	public Users updateUser(Users user)
	{
		user.setLastUpdateDate(Calendar.getInstance());
		entityManager.persist(user);
        return user;
	}
	
	/**
	 * Return the user by email address
	 * @param email
	 * @return
	 */
	public Users findUser(String email) {
		Users user = null;
		String hql = "From Users where email=:email";
		Query q = null;
		List<Users> results;
		
		q = entityManager.createQuery(hql);
		q.setParameter("email", email);
		results = (List<Users>) q.getResultList();
		
		if(results != null && results.size() > 0) {
			user = results.get(0);
		} 		
		
		return user;
	}
	
	public Users findUser(long id) {
		Users user = null;
		try {
			user = entityManager.find(Users.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return user;
	}
	
	public boolean isValidSession(long userId, String sessionId) {
		boolean success = false;
		UserSession userSession = null;
		String hql = "From UserSession where userId = :userId and sessionId = :sessionId";
		Query q = entityManager.createQuery(hql);
		
		q.setParameter("userId", userId);
		q.setParameter("sessionId", sessionId);
		
		if(q.getResultList().size() > 0) {
			success = true;
		}
		
		return success;
		
	}
	
	/**
	 * Remove a user from the database
	 * @param id - id number of user to remove
	 * @return true if user removed, false if failed
	 * @throws Exception 
	 */
	public boolean removeUser(long id)
	{
		boolean success = false;
		Users user = null;
		
		//Find user
		user = entityManager.find(Users.class, id);

		if(user != null) {
			//Attempt to delete the user
			entityManager.remove(user);
			success = true;
		}			

        return success;
	}
	
}
