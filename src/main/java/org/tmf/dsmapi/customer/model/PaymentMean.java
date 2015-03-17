//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.03.17 à 03:29:55 PM CET 
//


package org.tmf.dsmapi.customer.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * <p>Classe Java pour PaymentMean complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="PaymentMean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="href" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://orange.com/api/customer/tmf/v2/model/business}ValidFor" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccount" type="{http://orange.com/api/customer/tmf/v2/model/business}BankAccount" minOccurs="0"/>
 *         &lt;element name="relatedParty" type="{http://orange.com/api/customer/tmf/v2/model/business}RelatedParty" minOccurs="0"/>
 *         &lt;element name="creditCard" type="{http://orange.com/api/customer/tmf/v2/model/business}CreditCard" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentMean", propOrder = {
    "id",
    "href",
    "name",
    "validFor",
    "type",
    "bankAccount",
    "relatedParty",
    "creditCard"
})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity(name = "PaymentMean")
@Table(name = "PAYMENT_MEAN")
@Inheritance(strategy = InheritanceType.JOINED)
public class PaymentMean
    implements Serializable
{

    private final static long serialVersionUID = 11L;
    protected Long id;
    protected String href;
    protected String name;
    protected ValidFor validFor;
    protected String type;
    protected BankAccount bankAccount;
    protected RelatedParty relatedParty;
    protected CreditCard creditCard;

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "ID", scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété href.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "HREF", length = 255)
    public String getHref() {
        return href;
    }

    /**
     * Définit la valeur de la propriété href.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "NAME_", length = 255)
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
    @JoinColumn(name = "VALID_FOR_PAYMENT_MEAN_HJID")
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
     * Obtient la valeur de la propriété type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "TYPE_", length = 255)
    public String getType() {
        return type;
    }

    /**
     * Définit la valeur de la propriété type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Obtient la valeur de la propriété bankAccount.
     * 
     * @return
     *     possible object is
     *     {@link BankAccount }
     *     
     */
    @ManyToOne(targetEntity = BankAccount.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "BANK_ACCOUNT_PAYMENT_MEAN_HJ_0")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Définit la valeur de la propriété bankAccount.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccount }
     *     
     */
    public void setBankAccount(BankAccount value) {
        this.bankAccount = value;
    }

    /**
     * Obtient la valeur de la propriété relatedParty.
     * 
     * @return
     *     possible object is
     *     {@link RelatedParty }
     *     
     */
    @ManyToOne(targetEntity = RelatedParty.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "RELATED_PARTY_PAYMENT_MEAN_H_0")
    public RelatedParty getRelatedParty() {
        return relatedParty;
    }

    /**
     * Définit la valeur de la propriété relatedParty.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedParty }
     *     
     */
    public void setRelatedParty(RelatedParty value) {
        this.relatedParty = value;
    }

    /**
     * Obtient la valeur de la propriété creditCard.
     * 
     * @return
     *     possible object is
     *     {@link CreditCard }
     *     
     */
    @ManyToOne(targetEntity = CreditCard.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CREDIT_CARD_PAYMENT_MEAN_HJID")
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * Définit la valeur de la propriété creditCard.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCard }
     *     
     */
    public void setCreditCard(CreditCard value) {
        this.creditCard = value;
    }

}
