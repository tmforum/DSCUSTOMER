//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.03.17 à 03:29:55 PM CET 
//


package org.tmf.dsmapi.customer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * <p>Classe Java pour Customer complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Customer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="href" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://orange.com/api/customer/tmf/v2/model/business}ValidFor" minOccurs="0"/>
 *         &lt;element name="customerRank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relatedParty" type="{http://orange.com/api/customer/tmf/v2/model/business}RelatedParty" minOccurs="0"/>
 *         &lt;element name="characteristic" type="{http://orange.com/api/customer/tmf/v2/model/business}Characteristic" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactMedium" type="{http://orange.com/api/customer/tmf/v2/model/business}ContactMedium" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customerAccount" type="{http://orange.com/api/customer/tmf/v2/model/business}CustomerAccount" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customerCreditProfile" type="{http://orange.com/api/customer/tmf/v2/model/business}CustomerCreditProfile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paymentMean" type="{http://orange.com/api/customer/tmf/v2/model/business}PaymentMean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", propOrder = {
    "id",
    "href",
    "name",
    "status",
    "description",
    "validFor",
    "customerRank",
    "relatedParty",
    "characteristic",
    "contactMedium",
    "customerAccount",
    "customerCreditProfile",
    "paymentMean"
})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity(name = "Customer")
@Table(name = "CUSTOMER")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer
    implements Serializable
{

    private final static long serialVersionUID = 11L;
    protected Long id;
    protected String href;
    protected String name;
    protected String status;
    protected String description;
    protected ValidFor validFor;
    protected String customerRank;
    protected RelatedParty relatedParty;
    protected List<Characteristic> characteristic;
    protected List<ContactMedium> contactMedium;
    protected List<CustomerAccount> customerAccount;
    protected List<CustomerCreditProfile> customerCreditProfile;
    protected List<PaymentMean> paymentMean;

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
     * Obtient la valeur de la propriété status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "STATUS", length = 255)
    public String getStatus() {
        return status;
    }

    /**
     * Définit la valeur de la propriété status.
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
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "DESCRIPTION", length = 255)
    public String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
    @JoinColumn(name = "VALID_FOR_CUSTOMER_HJID")
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
     * Obtient la valeur de la propriété customerRank.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "CUSTOMER_RANK", length = 255)
    public String getCustomerRank() {
        return customerRank;
    }

    /**
     * Définit la valeur de la propriété customerRank.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerRank(String value) {
        this.customerRank = value;
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
    @JoinColumn(name = "RELATED_PARTY_CUSTOMER_HJID")
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
     * Gets the value of the characteristic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the characteristic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCharacteristic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Characteristic }
     * 
     * 
     */
    @OneToMany(targetEntity = Characteristic.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CHARACTERISTIC_CUSTOMER_ID")
    public List<Characteristic> getCharacteristic() {
        if (characteristic == null) {
            characteristic = new ArrayList<Characteristic>();
        }
        return this.characteristic;
    }

    /**
     * 
     * 
     */
    public void setCharacteristic(List<Characteristic> characteristic) {
        this.characteristic = characteristic;
    }

    /**
     * Gets the value of the contactMedium property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactMedium property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactMedium().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactMedium }
     * 
     * 
     */
    @OneToMany(targetEntity = ContactMedium.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CONTACT_MEDIUM_CUSTOMER_ID")
    public List<ContactMedium> getContactMedium() {
        if (contactMedium == null) {
            contactMedium = new ArrayList<ContactMedium>();
        }
        return this.contactMedium;
    }

    /**
     * 
     * 
     */
    public void setContactMedium(List<ContactMedium> contactMedium) {
        this.contactMedium = contactMedium;
    }

    /**
     * Gets the value of the customerAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerAccount }
     * 
     * 
     */
    @OneToMany(targetEntity = CustomerAccount.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CUSTOMER_ACCOUNT_CUSTOMER_ID")
    public List<CustomerAccount> getCustomerAccount() {
        if (customerAccount == null) {
            customerAccount = new ArrayList<CustomerAccount>();
        }
        return this.customerAccount;
    }

    /**
     * 
     * 
     */
    public void setCustomerAccount(List<CustomerAccount> customerAccount) {
        this.customerAccount = customerAccount;
    }

    /**
     * Gets the value of the customerCreditProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerCreditProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerCreditProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerCreditProfile }
     * 
     * 
     */
    @OneToMany(targetEntity = CustomerCreditProfile.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CUSTOMER_CREDIT_PROFILE_CUST_0")
    public List<CustomerCreditProfile> getCustomerCreditProfile() {
        if (customerCreditProfile == null) {
            customerCreditProfile = new ArrayList<CustomerCreditProfile>();
        }
        return this.customerCreditProfile;
    }

    /**
     * 
     * 
     */
    public void setCustomerCreditProfile(List<CustomerCreditProfile> customerCreditProfile) {
        this.customerCreditProfile = customerCreditProfile;
    }

    /**
     * Gets the value of the paymentMean property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentMean property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentMean().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentMean }
     * 
     * 
     */
    @OneToMany(targetEntity = PaymentMean.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_MEAN_CUSTOMER_ID")
    public List<PaymentMean> getPaymentMean() {
        if (paymentMean == null) {
            paymentMean = new ArrayList<PaymentMean>();
        }
        return this.paymentMean;
    }

    /**
     * 
     * 
     */
    public void setPaymentMean(List<PaymentMean> paymentMean) {
        this.paymentMean = paymentMean;
    }

}
