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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * <p>Classe Java pour Contact complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Contact">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validFor" type="{http://orange.com/api/customer/tmf/v2/model/business}ValidFor" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partyRoleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medium" type="{http://orange.com/api/customer/tmf/v2/model/business}ContactMedium" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedParty" type="{http://orange.com/api/customer/tmf/v2/model/business}RelatedParty" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contact", propOrder = {
    "type",
    "validFor",
    "name",
    "partyRoleType",
    "medium",
    "relatedParty"
})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity(name = "Contact")
@Table(name = "CONTACT")
@Inheritance(strategy = InheritanceType.JOINED)
public class Contact
    implements Serializable
{

    private final static long serialVersionUID = 11L;
    protected String type;
    protected ValidFor validFor;
    protected String name;
    protected String partyRoleType;
    protected List<ContactMedium> medium;
    protected RelatedParty relatedParty;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

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
    @JoinColumn(name = "VALID_FOR_CONTACT_HJID")
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
     * Obtient la valeur de la propriété partyRoleType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "PARTY_ROLE_TYPE", length = 255)
    public String getPartyRoleType() {
        return partyRoleType;
    }

    /**
     * Définit la valeur de la propriété partyRoleType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartyRoleType(String value) {
        this.partyRoleType = value;
    }

    /**
     * Gets the value of the medium property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medium property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedium().add(newItem);
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
    @JoinColumn(name = "MEDIUM_CONTACT_HJID")
    public List<ContactMedium> getMedium() {
        if (medium == null) {
            medium = new ArrayList<ContactMedium>();
        }
        return this.medium;
    }

    /**
     * 
     * 
     */
    public void setMedium(List<ContactMedium> medium) {
        this.medium = medium;
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
    @JoinColumn(name = "RELATED_PARTY_CONTACT_HJID")
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
