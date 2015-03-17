//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.03.17 à 03:29:55 PM CET 
//


package org.tmf.dsmapi.customer.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tmf.dsmapi.customer.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PaymentMean_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "PaymentMean");
    private final static QName _PaymentMeanRef_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "PaymentMeanRef");
    private final static QName _CustomerRef_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerRef");
    private final static QName _CustomerAccountRelationship_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerAccountRelationship");
    private final static QName _BankAccount_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "BankAccount");
    private final static QName _CustomerAccountBalance_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerAccountBalance");
    private final static QName _CustomerCreditProfile_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerCreditProfile");
    private final static QName _CustomerAccountRef_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerAccountRef");
    private final static QName _RelatedParty_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "RelatedParty");
    private final static QName _CreditCard_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CreditCard");
    private final static QName _PaymentPlan_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "PaymentPlan");
    private final static QName _CustomerAccountTaxExemption_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerAccountTaxExemption");
    private final static QName _ContactMedium_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "ContactMedium");
    private final static QName _ValidFor_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "ValidFor");
    private final static QName _CustomerAccount_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "CustomerAccount");
    private final static QName _Contact_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "Contact");
    private final static QName _Medium_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "Medium");
    private final static QName _Customer_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "Customer");
    private final static QName _Characteristic_QNAME = new QName("http://orange.com/api/customer/tmf/v2/model/business", "Characteristic");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tmf.dsmapi.customer.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link Characteristic }
     * 
     */
    public Characteristic createCharacteristic() {
        return new Characteristic();
    }

    /**
     * Create an instance of {@link Medium }
     * 
     */
    public Medium createMedium() {
        return new Medium();
    }

    /**
     * Create an instance of {@link Contact }
     * 
     */
    public Contact createContact() {
        return new Contact();
    }

    /**
     * Create an instance of {@link CustomerAccount }
     * 
     */
    public CustomerAccount createCustomerAccount() {
        return new CustomerAccount();
    }

    /**
     * Create an instance of {@link ContactMedium }
     * 
     */
    public ContactMedium createContactMedium() {
        return new ContactMedium();
    }

    /**
     * Create an instance of {@link ValidFor }
     * 
     */
    public ValidFor createValidFor() {
        return new ValidFor();
    }

    /**
     * Create an instance of {@link PaymentPlan }
     * 
     */
    public PaymentPlan createPaymentPlan() {
        return new PaymentPlan();
    }

    /**
     * Create an instance of {@link CreditCard }
     * 
     */
    public CreditCard createCreditCard() {
        return new CreditCard();
    }

    /**
     * Create an instance of {@link CustomerAccountTaxExemption }
     * 
     */
    public CustomerAccountTaxExemption createCustomerAccountTaxExemption() {
        return new CustomerAccountTaxExemption();
    }

    /**
     * Create an instance of {@link CustomerAccountRef }
     * 
     */
    public CustomerAccountRef createCustomerAccountRef() {
        return new CustomerAccountRef();
    }

    /**
     * Create an instance of {@link RelatedParty }
     * 
     */
    public RelatedParty createRelatedParty() {
        return new RelatedParty();
    }

    /**
     * Create an instance of {@link CustomerCreditProfile }
     * 
     */
    public CustomerCreditProfile createCustomerCreditProfile() {
        return new CustomerCreditProfile();
    }

    /**
     * Create an instance of {@link CustomerAccountBalance }
     * 
     */
    public CustomerAccountBalance createCustomerAccountBalance() {
        return new CustomerAccountBalance();
    }

    /**
     * Create an instance of {@link BankAccount }
     * 
     */
    public BankAccount createBankAccount() {
        return new BankAccount();
    }

    /**
     * Create an instance of {@link CustomerAccountRelationship }
     * 
     */
    public CustomerAccountRelationship createCustomerAccountRelationship() {
        return new CustomerAccountRelationship();
    }

    /**
     * Create an instance of {@link CustomerRef }
     * 
     */
    public CustomerRef createCustomerRef() {
        return new CustomerRef();
    }

    /**
     * Create an instance of {@link PaymentMeanRef }
     * 
     */
    public PaymentMeanRef createPaymentMeanRef() {
        return new PaymentMeanRef();
    }

    /**
     * Create an instance of {@link PaymentMean }
     * 
     */
    public PaymentMean createPaymentMean() {
        return new PaymentMean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentMean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "PaymentMean")
    public JAXBElement<PaymentMean> createPaymentMean(PaymentMean value) {
        return new JAXBElement<PaymentMean>(_PaymentMean_QNAME, PaymentMean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentMeanRef }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "PaymentMeanRef")
    public JAXBElement<PaymentMeanRef> createPaymentMeanRef(PaymentMeanRef value) {
        return new JAXBElement<PaymentMeanRef>(_PaymentMeanRef_QNAME, PaymentMeanRef.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerRef }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerRef")
    public JAXBElement<CustomerRef> createCustomerRef(CustomerRef value) {
        return new JAXBElement<CustomerRef>(_CustomerRef_QNAME, CustomerRef.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAccountRelationship }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerAccountRelationship")
    public JAXBElement<CustomerAccountRelationship> createCustomerAccountRelationship(CustomerAccountRelationship value) {
        return new JAXBElement<CustomerAccountRelationship>(_CustomerAccountRelationship_QNAME, CustomerAccountRelationship.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "BankAccount")
    public JAXBElement<BankAccount> createBankAccount(BankAccount value) {
        return new JAXBElement<BankAccount>(_BankAccount_QNAME, BankAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAccountBalance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerAccountBalance")
    public JAXBElement<CustomerAccountBalance> createCustomerAccountBalance(CustomerAccountBalance value) {
        return new JAXBElement<CustomerAccountBalance>(_CustomerAccountBalance_QNAME, CustomerAccountBalance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerCreditProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerCreditProfile")
    public JAXBElement<CustomerCreditProfile> createCustomerCreditProfile(CustomerCreditProfile value) {
        return new JAXBElement<CustomerCreditProfile>(_CustomerCreditProfile_QNAME, CustomerCreditProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAccountRef }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerAccountRef")
    public JAXBElement<CustomerAccountRef> createCustomerAccountRef(CustomerAccountRef value) {
        return new JAXBElement<CustomerAccountRef>(_CustomerAccountRef_QNAME, CustomerAccountRef.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelatedParty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "RelatedParty")
    public JAXBElement<RelatedParty> createRelatedParty(RelatedParty value) {
        return new JAXBElement<RelatedParty>(_RelatedParty_QNAME, RelatedParty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CreditCard")
    public JAXBElement<CreditCard> createCreditCard(CreditCard value) {
        return new JAXBElement<CreditCard>(_CreditCard_QNAME, CreditCard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentPlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "PaymentPlan")
    public JAXBElement<PaymentPlan> createPaymentPlan(PaymentPlan value) {
        return new JAXBElement<PaymentPlan>(_PaymentPlan_QNAME, PaymentPlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAccountTaxExemption }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerAccountTaxExemption")
    public JAXBElement<CustomerAccountTaxExemption> createCustomerAccountTaxExemption(CustomerAccountTaxExemption value) {
        return new JAXBElement<CustomerAccountTaxExemption>(_CustomerAccountTaxExemption_QNAME, CustomerAccountTaxExemption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactMedium }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "ContactMedium")
    public JAXBElement<ContactMedium> createContactMedium(ContactMedium value) {
        return new JAXBElement<ContactMedium>(_ContactMedium_QNAME, ContactMedium.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidFor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "ValidFor")
    public JAXBElement<ValidFor> createValidFor(ValidFor value) {
        return new JAXBElement<ValidFor>(_ValidFor_QNAME, ValidFor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "CustomerAccount")
    public JAXBElement<CustomerAccount> createCustomerAccount(CustomerAccount value) {
        return new JAXBElement<CustomerAccount>(_CustomerAccount_QNAME, CustomerAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Contact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "Contact")
    public JAXBElement<Contact> createContact(Contact value) {
        return new JAXBElement<Contact>(_Contact_QNAME, Contact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Medium }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "Medium")
    public JAXBElement<Medium> createMedium(Medium value) {
        return new JAXBElement<Medium>(_Medium_QNAME, Medium.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Customer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "Customer")
    public JAXBElement<Customer> createCustomer(Customer value) {
        return new JAXBElement<Customer>(_Customer_QNAME, Customer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Characteristic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://orange.com/api/customer/tmf/v2/model/business", name = "Characteristic")
    public JAXBElement<Characteristic> createCharacteristic(Characteristic value) {
        return new JAXBElement<Characteristic>(_Characteristic_QNAME, Characteristic.class, null, value);
    }

}
