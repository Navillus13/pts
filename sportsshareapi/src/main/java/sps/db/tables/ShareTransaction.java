package sps.db.tables;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "SHARE_TRANSACTION")
public class ShareTransaction {
    private Long id;

    private Users user;

	private long performerId;
    private long gameSessionId;
    private BigDecimal pricePer;
    private int pointsPer;
    private int quantity;
    private BigDecimal totalPrice;
    private long totalPoints;
    private String status;;

	private String sessionId;

	private Calendar createDate;
    private Calendar lastUpdateDate;

	public ShareTransaction() {
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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public long getPerformerId() {
		return performerId;
	}

	public void setPerformerId(long performerId) {
		this.performerId = performerId;
	}

    public long getGameSessionId() {
		return gameSessionId;
	}

	public void setGameSessionId(long gameSessionId) {
		this.gameSessionId = gameSessionId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getPricePer() {
		return pricePer;
	}

	public void setPricePer(BigDecimal pricePer) {
		this.pricePer = pricePer;
	}
	
	public BigDecimal getTotalPrice() {
		this.totalPrice = pricePer.multiply(new BigDecimal(quantity));
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
    public int getPointsPer() {
		return pointsPer;
	}

	public void setPointsPer(int pointsPer) {
		this.pointsPer = pointsPer;
	}

	public long getTotalPoints() {
		totalPoints = pointsPer * quantity; 
		return totalPoints;
	}

	public void setTotalPoints(long totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

}