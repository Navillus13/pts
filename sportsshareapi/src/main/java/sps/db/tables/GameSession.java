package sps.db.tables;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "GAME_SESSION")
public class GameSession {
    private Long id;

    private long maxUsers;
    private String description;
    private String category;
    private long performerId;
    
    private Set<GameSessionQuestion> gameSessionQuestions;
    private Set<GameSessionUser> gameSessionUsers;
    
    private Calendar signupExpirationDate;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar createDate;
    private Calendar lastUpdateDate;

	public GameSession() {
		// this form used by Hibernate
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
		return id;
    }

    public void setId(Long id) {
		this.id = id;
    }

	public long getPerformerId() {
		return performerId;
	}

	public void setPerformerId(long performerId) {
		this.performerId = performerId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(long maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SIGNUP_EXPIRATION_DATE")
	public Calendar getSignupExpirationDate() {
		return signupExpirationDate;
	}

	public void setSignupExpirationDate(Calendar signupExpirationDate) {
		this.signupExpirationDate = signupExpirationDate;
	}

    @OneToMany(cascade=CascadeType.ALL, mappedBy="gameSession")
	public Set<GameSessionQuestion> getGameSessionQuestions() {
		return gameSessionQuestions;
	}

	public void setGameSessionQuestions(
			Set<GameSessionQuestion> gameSessionQuestions) {
		this.gameSessionQuestions = gameSessionQuestions;
	}

	@OneToMany(mappedBy="gameSession")
	public Set<GameSessionUser> getGameSessionUsers() {
		return gameSessionUsers;
	}

	public void setGameSessionUsers(Set<GameSessionUser> gameSessionUsers) {
		this.gameSessionUsers = gameSessionUsers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}	

}