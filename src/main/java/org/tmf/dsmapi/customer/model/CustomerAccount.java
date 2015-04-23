//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.03.17 à 03:29:55 PM CET 
//


package org.tmf.dsmapi.customer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.commons.utils.CustomDateTimeAdapter;


/**
 * <p>Classe Java pour CustomerAccount complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="CustomerAccount">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="href" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receivableBalance" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="customerAccountTaxExemption" type="{http://orange.com/api/customer/tmf/v2/model/business}CustomerAccountTaxExemption" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customerAccountRelationship" type="{http://orange.com/api/customer/tmf/v2/model/business}CustomerAccountRelationship" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contact" type="{http://orange.com/api/customer/tmf/v2/model/business}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://orange.com/api/customer/tmf/v2/model/business}CustomerRef" minOccurs="0"/>
 *         &lt;element name="customerAccountBalance" type="{http://orange.com/api/customer/tmf/v2/model/business}CustomerAccountBalance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paymentPlan" type="{http://orange.com/api/customer/tmf/v2/model/business}PaymentPlan" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lastModified" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccount", propOrder = {
    "id",
    "href",
    "name",
    "description",
    "status",
    "accountType",
    "creditLimit",
    "pin",
    "receivableBalance",
    "customerAccountTaxExemption",
    "customerAccountRelationship",
    "contact",
    "customer",
    "customerAccountBalance",
    "paymentPlan",
    "lastModified"
})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity(name = "CustomerAccount")
@Table(name = "CUSTOMER_ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomerAccount
    implements Serializable
{

    private final static long serialVersionUID = 11L;
    protected Long id;
    protected String href;
    protected String name;
    protected String description;
    protected String status;
    protected String accountType;
    protected String creditLimit;
    protected String pin;
    protected Float receivableBalance;
    protected List<CustomerAccountTaxExemption> customerAccountTaxExemption;
    protected List<CustomerAccountRelationship> customerAccountRelationship;
    protected List<Contact> contact;
    protected CustomerRef customer;
    protected List<CustomerAccountBalance> customerAccountBalance;
    protected List<PaymentPlan> paymentPlan;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(CustomDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date lastModified;

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
     * Obtient la valeur de la propriété accountType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "ACCOUNT_TYPE", length = 255)
    public String getAccountType() {
        return accountType;
    }

    /**
     * Définit la valeur de la propriété accountType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountType(String value) {
        this.accountType = value;
    }

    /**
     * Obtient la valeur de la propriété creditLimit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "CREDIT_LIMIT", length = 255)
    public String getCreditLimit() {
        return creditLimit;
    }

    /**
     * Définit la valeur de la propriété creditLimit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditLimit(String value) {
        this.creditLimit = value;
    }

    /**
     * Obtient la valeur de la propriété pin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "PIN", length = 255)
    public String getPin() {
        return pin;
    }

    /**
     * Définit la valeur de la propriété pin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPin(String value) {
        this.pin = value;
    }

    /**
     * Obtient la valeur de la propriété receivableBalance.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    @Basic
    @Column(name = "RECEIVABLE_BALANCE", precision = 20, scale = 10)
    public Float getReceivableBalance() {
        return receivableBalance;
    }

    /**
     * Définit la valeur de la propriété receivableBalance.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setReceivableBalance(Float value) {
        this.receivableBalance = value;
    }

    /**
     * Gets the value of the customerAccountTaxExemption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerAccountTaxExemption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerAccountTaxExemption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerAccountTaxExemption }
     * 
     * 
     */
    @OneToMany(targetEntity = CustomerAccountTaxExemption.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CUSTOMER_ACCOUNT_TAX_EXEMPTI_1")
    public List<CustomerAccountTaxExemption> getCustomerAccountTaxExemption() {
        if (customerAccountTaxExemption == null) {
            customerAccountTaxExemption = new ArrayList<CustomerAccountTaxExemption>();
        }
        return this.customerAccountTaxExemption;
    }

    /**
     * 
     * 
     */
    public void setCustomerAccountTaxExemption(List<CustomerAccountTaxExemption> customerAccountTaxExemption) {
        this.customerAccountTaxExemption = customerAccountTaxExemption;
    }

    /**
     * Gets the value of the customerAccountRelationship property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerAccountRelationship property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerAccountRelationship().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerAccountRelationship }
     * 
     * 
     */
    @OneToMany(targetEntity = CustomerAccountRelationship.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CUSTOMER_ACCOUNT_RELATIONSHI_0")
    public List<CustomerAccountRelationship> getCustomerAccountRelationship() {
        if (customerAccountRelationship == null) {
            customerAccountRelationship = new ArrayList<CustomerAccountRelationship>();
        }
        return this.customerAccountRelationship;
    }

    /**
     * 
     * 
     */
    public void setCustomerAccountRelationship(List<CustomerAccountRelationship> customerAccountRelationship) {
        this.customerAccountRelationship = customerAccountRelationship;
    }

    /**
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    @OneToMany(targetEntity = Contact.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CONTACT_CUSTOMER_ACCOUNT_ID")
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
    }

    /**
     * 
     * 
     */
    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    /**
     * Obtient la valeur de la propriété customer.
     * 
     * @return
     *     possible object is
     *     {@link CustomerRef }
     *     
     */
    @ManyToOne(targetEntity = CustomerRef.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CUSTOMER_CUSTOMER_ACCOUNT_HJ_0")
    public CustomerRef getCustomer() {
        return customer;
    }

    /**
     * Définit la valeur de la propriété customer.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerRef }
     *     
     */
    public void setCustomer(CustomerRef value) {
        this.customer = value;
    }

    /**
     * Gets the value of the customerAccountBalance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerAccountBalance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerAccountBalance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerAccountBalance }
     * 
     * 
     */
    @OneToMany(targetEntity = CustomerAccountBalance.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CUSTOMER_ACCOUNT_BALANCE_CUS_0")
    public List<CustomerAccountBalance> getCustomerAccountBalance() {
        if (customerAccountBalance == null) {
            customerAccountBalance = new ArrayList<CustomerAccountBalance>();
        }
        return this.customerAccountBalance;
    }

    /**
     * 
     * 
     */
    public void setCustomerAccountBalance(List<CustomerAccountBalance> customerAccountBalance) {
        this.customerAccountBalance = customerAccountBalance;
    }

    /**
     * Gets the value of the paymentPlan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentPlan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentPlan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentPlan }
     * 
     * 
     */
    @OneToMany(targetEntity = PaymentPlan.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_PLAN_CUSTOMER_ACCOUN_0")
    public List<PaymentPlan> getPaymentPlan() {
        if (paymentPlan == null) {
            paymentPlan = new ArrayList<PaymentPlan>();
        }
        return this.paymentPlan;
    }

    /**
     * 
     * 
     */
    public void setPaymentPlan(List<PaymentPlan> paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    /**
     * Obtient la valeur de la propriété lastModified.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "LAST_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * Définit la valeur de la propriété lastModified.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModified(Date value) {
        this.lastModified = value;
    }

}
