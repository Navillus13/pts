package sps.db.tables;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "USERS")
public class Users {
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String billingAddress1;
    private String billingAddress2;
    private String billingAddress3;
    private String billingAddress4;
    private String billingState;
    private String billingCity;
    private String billingZip;    
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingAddress3;
    private String shippingAddress4;
	private String shippingState;
    private String shippingCity;
    private String shippingZip;
    
    private Set<GameSessionUser> gameSessionUsers;
    private Set<ShareTransaction> shareTransactions;
    private Calendar createDate;
    private Calendar lastLoginDate;
    private Calendar lastUpdateDate;

	public Users() {
		// this form used by Hibernate
	}

	/**
	 * Email and Password are the only required fields.  createDate and lastUpdateDate are also both updated
	 * @param email
	 * @param password
	 */
	public Users(String email, String password) {
		// for application use, to create new events
		this.email = email;
		this.password = password;
		this.createDate = Calendar.getInstance();
		this.lastUpdateDate = Calendar.getInstance();
	}


	
	/**
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param billingAddress1
	 * @param billingAddress2
	 * @param billingAddress3
	 * @param billingAddress4
	 * @param billingState
	 * @param billingCity
	 * @param billingZip
	 * @param shippingAddress1
	 * @param shippingAddress2
	 * @param shippingAddress3
	 * @param shippingAddress4
	 * @param shippingState
	 * @param shippingCity
	 * @param shippingZip
	 * @param createDate
	 * @param lastLoginDate
	 * @param lastUpdateDate
	 * @param userSessions
	 */
	public Users(String email, String password, String firstName,
			String lastName, String billingAddress1, String billingAddress2,
			String billingAddress3, String billingAddress4,
			String billingState, String billingCity, String billingZip,
			String shippingAddress1, String shippingAddress2,
			String shippingAddress3, String shippingAddress4,
			String shippingState, String shippingCity, String shippingZip,
			Set<UserSession> userSessions) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.billingAddress1 = billingAddress1;
		this.billingAddress2 = billingAddress2;
		this.billingAddress3 = billingAddress3;
		this.billingAddress4 = billingAddress4;
		this.billingState = billingState;
		this.billingCity = billingCity;
		this.billingZip = billingZip;
		this.shippingAddress1 = shippingAddress1;
		this.shippingAddress2 = shippingAddress2;
		this.shippingAddress3 = shippingAddress3;
		this.shippingAddress4 = shippingAddress4;
		this.shippingState = shippingState;
		this.shippingCity = shippingCity;
		this.shippingZip = shippingZip;
		this.userSessions = userSessions;
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

    @Column(nullable=false,unique=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBillingAddress1() {
		return billingAddress1;
	}

	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}

	public String getBillingAddress2() {
		return billingAddress2;
	}

	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}

	public String getBillingAddress3() {
		return billingAddress3;
	}

	public void setBillingAddress3(String billingAddress3) {
		this.billingAddress3 = billingAddress3;
	}

	public String getBillingAddress4() {
		return billingAddress4;
	}

	public void setBillingAddress4(String billingAddress4) {
		this.billingAddress4 = billingAddress4;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	public String getShippingAddress1() {
		return shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public String getShippingAddress2() {
		return shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	public String getShippingAddress3() {
		return shippingAddress3;
	}

	public void setShippingAddress3(String shippingAddress3) {
		this.shippingAddress3 = shippingAddress3;
	}

	public String getShippingAddress4() {
		return shippingAddress4;
	}

	public void setShippingAddress4(String shippingAddress4) {
		this.shippingAddress4 = shippingAddress4;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingZip() {
		return shippingZip;
	}

	public void setShippingZip(String shippingZip) {
		this.shippingZip = shippingZip;
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
	@Column(name = "LOGIN_DATE")
	public Calendar getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Calendar lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	private Set<UserSession> userSessions;
	@OneToMany(mappedBy="user")
	@Cascade({CascadeType.ALL})
	public Set<UserSession> getUserSessions() {
		return this.userSessions;
	}
	public void setUserSessions(Set<UserSession> sessions) {
		this.userSessions = sessions;
	}
    
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	public Set<GameSessionUser> getGameSessionUsers() {
		return gameSessionUsers;
	}

	public void setGameSessionUsers(Set<GameSessionUser> gameSessionUsers) {
		this.gameSessionUsers = gameSessionUsers;
	}
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	public Set<ShareTransaction> getShareTransactions() {
		return shareTransactions;
	}

	public void setShareTransactions(Set<ShareTransaction> shareTransactions) {
		this.shareTransactions = shareTransactions;
	}
	
	
}