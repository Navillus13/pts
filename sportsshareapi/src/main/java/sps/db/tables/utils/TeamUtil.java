package sps.db.tables.utils;

import java.util.Calendar;

import javax.persistence.EntityManager;

import sps.db.tables.Team;

/**
 * @author scottgordon
 * 
 * <p> Wrapper for access to the database.  There should be a minimalisitc view on public interfaces for this class
 * 		Keep it clean and easy to understand</p>
 * 
 */

public class TeamUtil {
	protected EntityManager entityManager;
	
	public TeamUtil(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Team addTeam(Team team) {
				
		team.setCreateDate(Calendar.getInstance());
		team.setLastUpdateDate(Calendar.getInstance());
		//Add the transaction to the database
		entityManager.persist(team);
		
        return team;
	}
	
	public Team findTeam(long id) {
		Team team = null;
		try {
			team = entityManager.find(Team.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return team;
	}
	
	public Team updateTeam(Team team) throws Exception {
		try {
			team.setLastUpdateDate(Calendar.getInstance());
			entityManager.persist(team);
			return team;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to update team: " + team.getId());
		}
	}

	
}
