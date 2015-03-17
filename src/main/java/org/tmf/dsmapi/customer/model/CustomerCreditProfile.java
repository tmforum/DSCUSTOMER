//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.03.17 à 03:29:55 PM CET 
//


package org.tmf.dsmapi.customer.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.commons.utils.CustomDateTimeAdapter;


/**
 * <p>Classe Java pour CustomerCreditProfile complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="CustomerCreditProfile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creditProfileDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://orange.com/api/customer/tmf/v2/model/business}ValidFor" minOccurs="0"/>
 *         &lt;element name="creditRiskRating" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditScore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerCreditProfile", propOrder = {
    "creditProfileDate",
    "validFor",
    "creditRiskRating",
    "creditScore"
})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity(name = "CustomerCreditProfile")
@Table(name = "CUSTOMER_CREDIT_PROFILE")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomerCreditProfile
    implements Serializable
{

    private final static long serialVersionUID = 11L;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(CustomDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date creditProfileDate;
    protected ValidFor validFor;
    protected String creditRiskRating;
    protected String creditScore;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    /**
     * Obtient la valeur de la propriété creditProfileDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "CREDIT_PROFILE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreditProfileDate() {
        return creditProfileDate;
    }

    /**
     * Définit la valeur de la propriété creditProfileDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditProfileDate(Date value) {
        this.creditProfileDate = value;
    }

    /**
     * Obtient la valeur de la propriété validFor.
     * 
     * @return
     *     possible object is
     *     {@link ValidFor }
     *     
     */
    @ManyToOne(targetEntity = ValidFor.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "VALID_FOR_CUSTOMER_CREDIT_PR_0")
    public ValidFor getValidFor() {
        return validFor;
    }

    /**
     * Définit la valeur de la propriété validFor.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidFor }
     *     
     */
    public void setValidFor(ValidFor value) {
        this.validFor = value;
    }

    /**
     * Obtient la valeur de la propriété creditRiskRating.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "CREDIT_RISK_RATING", length = 255)
    public String getCreditRiskRating() {
        return creditRiskRating;
    }

    /**
     * Définit la valeur de la propriété creditRiskRating.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditRiskRating(String value) {
        this.creditRiskRating = value;
    }

    /**
     * Obtient la valeur de la propriété creditScore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "CREDIT_SCORE", length = 255)
    public String getCreditScore() {
        return creditScore;
    }

    /**
     * Définit la valeur de la propriété creditScore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditScore(String value) {
        this.creditScore = value;
    }

    /**
     * Obtient la valeur de la propriété hjid.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.codehaus.jackson.annotate.JsonIgnore
    public Long getHjid() {
        return hjid;
    }

    /**
     * Définit la valeur de la propriété hjid.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

}
