//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.30 at 06:15:58 PM CDT 
//


package sps.schema.generated;

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
 *         &lt;element name="userRequest" type="{}userRequestType" minOccurs="0"/>
 *         &lt;element name="questionRequest" type="{}questionRequestType" minOccurs="0"/>
 *         &lt;element name="gameSessionRequest" type="{}gameSessionRequestType" minOccurs="0"/>
 *         &lt;element name="answerRequest" type="{}answerRequestType" minOccurs="0"/>
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
    "userRequest",
    "questionRequest",
    "gameSessionRequest",
    "answerRequest"
})
@XmlRootElement(name = "spsRequest")
public class SpsRequest {

    protected UserRequestType userRequest;
    protected QuestionRequestType questionRequest;
    protected GameSessionRequestType gameSessionRequest;
    protected AnswerRequestType answerRequest;

    /**
     * Gets the value of the userRequest property.
     * 
     * @return
     *     possible object is
     *     {@link UserRequestType }
     *     
     */
    public UserRequestType getUserRequest() {
        return userRequest;
    }

    /**
     * Sets the value of the userRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRequestType }
     *     
     */
    public void setUserRequest(UserRequestType value) {
        this.userRequest = value;
    }

    /**
     * Gets the value of the questionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link QuestionRequestType }
     *     
     */
    public QuestionRequestType getQuestionRequest() {
        return questionRequest;
    }

    /**
     * Sets the value of the questionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionRequestType }
     *     
     */
    public void setQuestionRequest(QuestionRequestType value) {
        this.questionRequest = value;
    }

    /**
     * Gets the value of the gameSessionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GameSessionRequestType }
     *     
     */
    public GameSessionRequestType getGameSessionRequest() {
        return gameSessionRequest;
    }

    /**
     * Sets the value of the gameSessionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GameSessionRequestType }
     *     
     */
    public void setGameSessionRequest(GameSessionRequestType value) {
        this.gameSessionRequest = value;
    }

    /**
     * Gets the value of the answerRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AnswerRequestType }
     *     
     */
    public AnswerRequestType getAnswerRequest() {
        return answerRequest;
    }

    /**
     * Sets the value of the answerRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnswerRequestType }
     *     
     */
    public void setAnswerRequest(AnswerRequestType value) {
        this.answerRequest = value;
    }

}
