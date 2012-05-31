//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.30 at 07:48:14 PM CDT 
//


package sps.schema.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userReply" type="{}userReplyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="questionReply" type="{}questionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="gameSessionReply" type="{}gameSessionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="answerReply" type="{}answerType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="errors" type="{}errorType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userReply",
    "questionReply",
    "gameSessionReply",
    "answerReply",
    "errors"
})
@XmlRootElement(name = "spsReply")
public class SpsReply {

    protected List<UserReplyType> userReply;
    protected List<QuestionType> questionReply;
    protected List<GameSessionType> gameSessionReply;
    protected List<AnswerType> answerReply;
    protected List<ErrorType> errors;

    /**
     * Gets the value of the userReply property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userReply property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserReply().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserReplyType }
     * 
     * 
     */
    public List<UserReplyType> getUserReply() {
        if (userReply == null) {
            userReply = new ArrayList<UserReplyType>();
        }
        return this.userReply;
    }

    /**
     * Gets the value of the questionReply property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the questionReply property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuestionReply().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuestionType }
     * 
     * 
     */
    public List<QuestionType> getQuestionReply() {
        if (questionReply == null) {
            questionReply = new ArrayList<QuestionType>();
        }
        return this.questionReply;
    }

    /**
     * Gets the value of the gameSessionReply property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gameSessionReply property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGameSessionReply().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GameSessionType }
     * 
     * 
     */
    public List<GameSessionType> getGameSessionReply() {
        if (gameSessionReply == null) {
            gameSessionReply = new ArrayList<GameSessionType>();
        }
        return this.gameSessionReply;
    }

    /**
     * Gets the value of the answerReply property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the answerReply property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnswerReply().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnswerType }
     * 
     * 
     */
    public List<AnswerType> getAnswerReply() {
        if (answerReply == null) {
            answerReply = new ArrayList<AnswerType>();
        }
        return this.answerReply;
    }

    /**
     * Gets the value of the errors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorType }
     * 
     * 
     */
    public List<ErrorType> getErrors() {
        if (errors == null) {
            errors = new ArrayList<ErrorType>();
        }
        return this.errors;
    }

}
