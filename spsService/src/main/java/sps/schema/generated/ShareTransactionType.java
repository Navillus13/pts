//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.30 at 07:48:14 PM CDT 
//


package sps.schema.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for shareTransactionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="shareTransactionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="user" type="{}userType"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pointsPer" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pricePer" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPoints" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gameSession" type="{}gameSessionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shareTransactionType", propOrder = {
    "id",
    "user",
    "quantity",
    "pointsPer",
    "pricePer",
    "totalPrice",
    "totalPoints",
    "status",
    "gameSession"
})
public class ShareTransactionType {

    protected long id;
    @XmlElement(required = true)
    protected UserType user;
    protected int quantity;
    protected int pointsPer;
    protected double pricePer;
    protected double totalPrice;
    protected long totalPoints;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected GameSessionType gameSession;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setUser(UserType value) {
        this.user = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the pointsPer property.
     * 
     */
    public int getPointsPer() {
        return pointsPer;
    }

    /**
     * Sets the value of the pointsPer property.
     * 
     */
    public void setPointsPer(int value) {
        this.pointsPer = value;
    }

    /**
     * Gets the value of the pricePer property.
     * 
     */
    public double getPricePer() {
        return pricePer;
    }

    /**
     * Sets the value of the pricePer property.
     * 
     */
    public void setPricePer(double value) {
        this.pricePer = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     */
    public void setTotalPrice(double value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the totalPoints property.
     * 
     */
    public long getTotalPoints() {
        return totalPoints;
    }

    /**
     * Sets the value of the totalPoints property.
     * 
     */
    public void setTotalPoints(long value) {
        this.totalPoints = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the gameSession property.
     * 
     * @return
     *     possible object is
     *     {@link GameSessionType }
     *     
     */
    public GameSessionType getGameSession() {
        return gameSession;
    }

    /**
     * Sets the value of the gameSession property.
     * 
     * @param value
     *     allowed object is
     *     {@link GameSessionType }
     *     
     */
    public void setGameSession(GameSessionType value) {
        this.gameSession = value;
    }

}
