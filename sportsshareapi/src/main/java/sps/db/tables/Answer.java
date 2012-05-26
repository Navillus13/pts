package sps.db.tables;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "ANSWER")
public class Answer {
    private Long id;

    private Question question;
    private GameSessionUser gameSessionUser;

	private String answerType;

    private String answer;
    private int confidence;
    private double score;

    private Calendar createDate;
    private Calendar lastUpdateDate;

	public Answer() {
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
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="question_id", nullable=false)	
    public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_session_user_id", nullable=false)
    public GameSessionUser getGameSessionUser() {
		return gameSessionUser;
	}

	public void setGameSessionUser(GameSessionUser gameSessionUser) {
		this.gameSessionUser = gameSessionUser;
	}
	
	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}


	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
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