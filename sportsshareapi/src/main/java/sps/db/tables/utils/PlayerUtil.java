package sps.db.tables.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import sps.db.tables.GameSession;
import sps.db.tables.Player;
import sps.db.tables.Question;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class PlayerUtil {
	protected EntityManager entityManager;
	
	public PlayerUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Player addPlayer(Player player) {
				
		player.setCreateDate(Calendar.getInstance());
		player.setLastUpdateDate(Calendar.getInstance());
		//Add the transaction to the database
		entityManager.persist(player);
		
        return player;
	}
	
	public Player findPlayer(long id) {
		Player player = null;
		try {
			player = entityManager.find(Player.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return player;
	}
	
	public Player updatePlayer(Player player) throws Exception {
		try {
			player.setLastUpdateDate(Calendar.getInstance());
			entityManager.persist(player);
			return player;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to update player: " + player.getId());
		}
	}

	
}
