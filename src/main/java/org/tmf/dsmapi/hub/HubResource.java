package org.tmf.dsmapi.hub;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.jaxrs.Report;
import org.tmf.dsmapi.customer.event.CustomerEvent;
import org.tmf.dsmapi.customer.event.CustomerEventTypeEnum;
import org.tmf.dsmapi.customer.model.BankAccount;
import org.tmf.dsmapi.customer.model.Characteristic;
import org.tmf.dsmapi.customer.model.Contact;
import org.tmf.dsmapi.customer.model.ContactMedium;
import org.tmf.dsmapi.customer.model.CreditCard;
import org.tmf.dsmapi.customer.model.Customer;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.CustomerAccountBalance;
import org.tmf.dsmapi.customer.model.CustomerAccountRef;
import org.tmf.dsmapi.customer.model.CustomerAccountRelationship;
import org.tmf.dsmapi.customer.model.CustomerAccountTaxExemption;
import org.tmf.dsmapi.customer.model.CustomerCreditProfile;
import org.tmf.dsmapi.customer.model.CustomerRef;
import org.tmf.dsmapi.customer.model.Medium;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.customer.model.PaymentMeanRef;
import org.tmf.dsmapi.customer.model.PaymentPlan;
import org.tmf.dsmapi.customer.model.RelatedParty;
import org.tmf.dsmapi.customer.model.ValidFor;
import org.tmf.dsmapi.customerAccount.event.CustomerAccountEvent;
import org.tmf.dsmapi.customerAccount.event.CustomerAccountEventTypeEnum;
import org.tmf.dsmapi.paymentMean.event.PaymentMeanEvent;
import org.tmf.dsmapi.paymentMean.event.PaymentMeanEventTypeEnum;

@Stateless
@Path("/customerManagement/v2/hub")
public class HubResource {

    @EJB
    HubFacade hubFacade;

    public HubResource() {
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(Hub entity) throws BadUsageException {
        entity.setId(null);
        hubFacade.create(entity);
        //201
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @DELETE
    public Report deleteAllHub() {

        int previousRows = hubFacade.count();
        hubFacade.removeAll();
        int currentRows = hubFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) throws UnknownResourceException {
        Hub hub = hubFacade.find(id);
        if (null == hub) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            hubFacade.remove(id);
            // 204
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    @Produces({"application/json"})
    public List<Hub> findAll() {
        return hubFacade.findAll();
    }

    @GET
    @Produces({"application/json"})
    @Path("proto")
    public Hub proto() {
        Hub hub = new Hub();
        hub.setCallback("Callback");
        hub.setQuery("queryString");
        hub.setId("id");
        return hub;
    }

    @GET
    @Produces({"application/json"})
    @Path("proto/customer/event")
    public CustomerEvent protocustomerevent() {
        CustomerEvent event = new CustomerEvent();
        CustomerEventTypeEnum x = CustomerEventTypeEnum.CustomerCreateNotification;
        event.setEventType(x);
        event.setEventTime(new Date());
        event.setId("42");

        Customer customer = new Customer();

        customer.setId(new Long(123));
        customer.setHref("http://serverLocalisation:port/DSCustomerManagement/api/customerManagement/v2/customerAccount/123");
        customer.setName("DisplayName");
        customer.setStatus("New");
        customer.setDescription("customer description");
        customer.setCustomerRank("3");

        ValidFor validFor = new ValidFor();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2014, 05, 15);
        validFor.setStartDateTime(gc.getTime());
        customer.setValidFor(validFor);

        RelatedParty relatedParty = new RelatedParty();
        relatedParty.setId("1");
        relatedParty.setHref("http://serverlocation:port/partyManagement/individual/1");
        relatedParty.setRole("customer");
        relatedParty.setName("John Doe");
        customer.setRelatedParty(relatedParty);

        List<Characteristic> characteristics = new ArrayList<Characteristic>();
        Characteristic charac1 = new Characteristic();
        charac1.setName("Characteristic name 1");
        charac1.setValue("Characteristic value 1");
        Characteristic charac2 = new Characteristic();
        charac2.setName("Characteristic name 2");
        charac2.setValue("Characteristic value 2");
        characteristics.add(charac1);
        characteristics.add(charac2);
        customer.setCharacteristic(characteristics);

        List<ContactMedium> contactMediums = new ArrayList<ContactMedium>();
        ContactMedium cm = new ContactMedium();
        Medium medium = new Medium();
        cm.setType("Email");
        medium.setEmailAddress("abc@tmforum.com");
        cm.setMedium(medium);
        gc.set(2015, 01, 01);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        cm.setValidFor(validFor);
        contactMediums.add(cm);

        cm = new ContactMedium();
        medium = new Medium();
        cm.setType("TelephoneNumber");
        medium.setNumber("+436641234567");
        medium.setType("business");
        cm.setMedium(medium);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        cm.setValidFor(validFor);
        cm.setPreferred(true);
        contactMediums.add(cm);

        cm = new ContactMedium();
        medium = new Medium();
        cm.setType("PostalAddress");
        medium.setCity("Wien");
        medium.setCountry("Austria");
        medium.setPostcode("1020");
        medium.setStateOrProvince("Quebec");
        medium.setStreetOne("Lassallestrasse7");
        medium.setStreetTwo("");
        cm.setMedium(medium);
        gc.set(2015, 01, 01);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        cm.setValidFor(validFor);
        contactMediums.add(cm);

        customer.setContactMedium(contactMediums);

        List<CustomerAccountRef> customerAccounts = new ArrayList<CustomerAccountRef>();
        CustomerAccountRef ca = new CustomerAccountRef();
        ca.setId("1");
        ca.setHref("http://serverlocation:port/customerManagement/customerAccount/1");
        ca.setName("customerAccount1");
        ca.setDescription("CustomerAccountDesc1");
        ca.setStatus("Active");
        customerAccounts.add(ca);

        ca = new CustomerAccountRef();
        ca.setId("2");
        ca.setHref("http://serverlocation:port/customerManagement/customerAccount/2");
        ca.setName("customerAccount2");
        ca.setDescription("CustomerAccountDesc2");
        ca.setStatus("Active");
        customerAccounts.add(ca);

        customer.setCustomerAccount(customerAccounts);


        List<CustomerCreditProfile> customerCreditProfiles = new ArrayList<CustomerCreditProfile>();
        CustomerCreditProfile ccp = new CustomerCreditProfile();
        ccp.setCreditProfileDate(new Date());
        ccp.setCreditRiskRating("1");
        ccp.setCreditScore("1");
        gc.set(2015, 01, 04);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        ccp.setValidFor(validFor);
        customerCreditProfiles.add(ccp);

        ccp = new CustomerCreditProfile();
        ccp.setCreditProfileDate(new Date());
        ccp.setCreditRiskRating("2");
        ccp.setCreditScore("2");
        gc.set(2015, 01, 04);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        ccp.setValidFor(validFor);
        customerCreditProfiles.add(ccp);

        customer.setCustomerCreditProfile(customerCreditProfiles);

        List<PaymentMeanRef> paymentMeans = new ArrayList<PaymentMeanRef>();
        PaymentMeanRef pm = new PaymentMeanRef();
        pm.setId("45");
        pm.setHref("http://serverlocation:port/customerManagement/paymentMean/45");
        pm.setName("my favourite payment mean");
        paymentMeans.add(pm);

        pm = new PaymentMeanRef();
        pm.setId("64");
        pm.setHref("http://serverlocation:port/customerManagement/paymentMean/64");
        pm.setName("my credit card payment mean");
        paymentMeans.add(pm);

        customer.setPaymentMean(paymentMeans);

        event.setResource(customer);

        return event;
    }

    @GET
    @Produces({"application/json"})
    @Path("proto/customerAccount/event")
    public CustomerAccountEvent protocustomeraccountevent() {
        CustomerAccountEvent event = new CustomerAccountEvent();
        CustomerAccountEventTypeEnum x = CustomerAccountEventTypeEnum.CustomerAccountCreateNotification;
        event.setEventType(x);
        event.setEventTime(new Date());
        event.setId("42");

        GregorianCalendar gc = new GregorianCalendar();
        ValidFor validFor = null;

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setId(new Long(1234));
        customerAccount.setHref("http://serverlocation:port/customerManagement/customerAccount/1234");
        customerAccount.setLastModified(gc.getTime());
        customerAccount.setName("sampleaccount");
        customerAccount.setAccountType("Residential");
        customerAccount.setStatus("in progress");
        customerAccount.setDescription("description of customer account");
        customerAccount.setCreditLimit("1212");
        customerAccount.setPin("pin0");
        customerAccount.setReceivableBalance(new Float("52.3"));

        List<CustomerAccountTaxExemption> customerAccountTaxExemptions = new ArrayList<CustomerAccountTaxExemption>();
        CustomerAccountTaxExemption caTaxeExemption = new CustomerAccountTaxExemption();
        caTaxeExemption.setIssuingJurisdiction("SampleJurisdiction");
        caTaxeExemption.setCertificateNumber("CATaxExemption1");
        caTaxeExemption.setReason("Reason");
        validFor = new ValidFor();
        gc.set(2015, 04, 01);
        validFor.setStartDateTime(gc.getTime());
        gc.set(2099, 01, 01);
        validFor.setEndDateTime(gc.getTime());
        caTaxeExemption.setValidFor(validFor);
        customerAccountTaxExemptions.add(caTaxeExemption);

        caTaxeExemption = new CustomerAccountTaxExemption();
        caTaxeExemption.setIssuingJurisdiction("SampleJurisdiction2");
        caTaxeExemption.setCertificateNumber("CATaxExemption2");
        caTaxeExemption.setReason("Reason2");
        validFor = new ValidFor();
        gc.set(2015, 04, 01);
        validFor.setStartDateTime(gc.getTime());
        gc.set(2099, 01, 01);
        validFor.setEndDateTime(gc.getTime());
        caTaxeExemption.setValidFor(validFor);
        customerAccountTaxExemptions.add(caTaxeExemption);

        customerAccount.setCustomerAccountTaxExemption(customerAccountTaxExemptions);


        List<CustomerAccountRelationship> customerAccountRelationships = new ArrayList<CustomerAccountRelationship>();
        CustomerAccountRelationship caRelationship = new CustomerAccountRelationship();
        caRelationship.setRelationshipType("Type xx");
        validFor = new ValidFor();
        gc.set(2015, 04, 01);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        caRelationship.setValidFor(validFor);
        List<CustomerAccountRef> caRefs = new ArrayList<CustomerAccountRef>();
        CustomerAccountRef caRef = new CustomerAccountRef();
        caRef.setId("1");
        caRef.setHref("http://serverlocation:port/customerManagement/customerAccount/1");
        caRef.setName("CustomerAccount1");
        caRef.setDescription("CustomerAccountDesc1");
        caRefs.add(caRef);
        caRelationship.setCustomerAccount(caRefs);
        customerAccountRelationships.add(caRelationship);
        customerAccount.setCustomerAccountRelationship(customerAccountRelationships);

        List<Contact> contacts = new ArrayList<Contact>();
        Contact contact = new Contact();
        contact.setType("primary");
        contact.setName("DisplayName");
        validFor = new ValidFor();
        gc.set(2015, 04, 01);
        validFor.setStartDateTime(gc.getTime());
        gc.set(2099, 01, 01);
        validFor.setEndDateTime(gc.getTime());
        contact.setValidFor(validFor);
        contact.setPartyRoleType("CustomerAccountRepresentative");

        List<ContactMedium> contactMediums = new ArrayList<ContactMedium>();
        ContactMedium contactMedium = new ContactMedium();
        contactMedium.setType("Email");
        gc.set(2015, 01, 01);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        contactMedium.setValidFor(validFor);
        Medium medium = new Medium();
        medium.setEmailAddress("abc@tmforum.com");
        contactMedium.setMedium(medium);
        contactMediums.add(contactMedium);

        contactMedium = new ContactMedium();
        contactMedium.setType("TelephoneNumber");
        medium = new Medium();
        medium.setType("mobile");
        medium.setNumber("+6123456789");
        contactMedium.setMedium(medium);
        contactMediums.add(contactMedium);

        contactMedium = new ContactMedium();
        contactMedium.setType("TelephoneNumber");
        medium = new Medium();
        medium.setType("business");
        medium.setNumber("+436641234567");
        contactMedium.setPreferred(Boolean.TRUE);
        contactMedium.setMedium(medium);
        contactMediums.add(contactMedium);

        contactMedium = new ContactMedium();
        contactMedium.setType("PostalAddress");
        medium = new Medium();
        medium.setCity("Wien");
        medium.setCountry("Austria");
        medium.setPostcode("1020");
        medium.setStateOrProvince("Quebec");
        medium.setStreetOne("Lassallestrasse7");
        medium.setStreetTwo("");
        contactMedium.setMedium(medium);
        contactMediums.add(contactMedium);

        contact.setMedium(contactMediums);

        customerAccount.setContact(contacts);

        CustomerRef customerRef = new CustomerRef();
        customerRef.setId("1");
        customerRef.setHref("http://serverlocation:port/customerManagement/customer/1");
        customerRef.setName("Customer1");
        customerRef.setDescription("CustomerDesc1");
        customerAccount.setCustomer(customerRef);

        List<CustomerAccountBalance> customerAccountBalances = new ArrayList<CustomerAccountBalance>();
        CustomerAccountBalance caBalance = new CustomerAccountBalance();
        caBalance.setType("ReceivableBalance");
        caBalance.setAmount(new Float("52.3"));
        caBalance.setStatus("Due");
        gc.set(2015, 02, 29);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        customerAccountBalances.add(caBalance);

        caBalance = new CustomerAccountBalance();
        caBalance.setType("DepositBalance");
        caBalance.setAmount(new Float("52.3"));
        caBalance.setStatus("Paid");
        gc.set(2015, 02, 29);
        validFor.setStartDateTime(gc.getTime());
        validFor.setEndDateTime(null);
        customerAccountBalances.add(caBalance);

        customerAccount.setCustomerAccountBalance(customerAccountBalances);

        List<PaymentPlan> paymentPlans = new ArrayList<PaymentPlan>();
        PaymentPlan paymentPlan = new PaymentPlan();
        paymentPlan.setStatus("Effective");
        paymentPlan.setType("Type1");
        paymentPlan.setPriority(new Float("1"));
        paymentPlan.setAmount(new Float("15.3"));
        paymentPlan.setPaymentFrequency("monthly");
        paymentPlan.setNumberOfPayments("4");
        gc.set(2015, 03, 03);
        validFor.setStartDateTime(gc.getTime());
        gc.set(2099, 01, 01);
        validFor.setEndDateTime(gc.getTime());
        paymentPlan.setValidFor(validFor);
        paymentPlans.add(paymentPlan);

        paymentPlan = new PaymentPlan();
        paymentPlan.setStatus("Ineffective");
        paymentPlan.setType("Type2");
        paymentPlan.setPriority(new Float("2"));
        paymentPlan.setAmount(new Float("20"));
        paymentPlan.setPaymentFrequency("monthly");
        paymentPlan.setNumberOfPayments("2");
        gc.set(2015, 03, 13);
        validFor.setStartDateTime(gc.getTime());
        gc.set(2099, 01, 01);
        validFor.setEndDateTime(gc.getTime());
        paymentPlan.setValidFor(validFor);
        PaymentMeanRef paymentMean = new PaymentMeanRef();
        paymentMean.setId("70");
        paymentMean.setHref("http://serverlocation:port/customerManagement/paymentMean/70");
        paymentPlan.setPaymentMean(paymentMean);
        paymentPlans.add(paymentPlan);

        customerAccount.setPaymentPlan(paymentPlans);

        event.setResource(customerAccount);

        return event;
    }

    @GET
    @Produces({"application/json"})
    @Path("proto/paymentMean/event")
    public PaymentMeanEvent protopaymentmeanevent() {
        PaymentMeanEvent event = new PaymentMeanEvent();
        PaymentMeanEventTypeEnum x = PaymentMeanEventTypeEnum.PaymentMeanCreateNotification;
        event.setEventType(x);
        event.setEventTime(new Date());
        event.setId("56");

        GregorianCalendar gc = new GregorianCalendar();
        ValidFor validFor = null;
        
        PaymentMean paymentMean = new PaymentMean();
        paymentMean.setName("My favourite payment mean");
        validFor = new ValidFor();
        gc.set(2015, 04, 01);
        validFor.setStartDateTime(gc.getTime());
        gc.set(2099, 01, 01);
        validFor.setEndDateTime(gc.getTime());
        paymentMean.setValidFor(validFor);
        paymentMean.setType("BankAccountDebit");
        
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBIC("PSSTFRPPPAR");
        bankAccount.setDomiciliation("LaBanquePostale–75900ParixCedex15");
        bankAccount.setIBAN("FR4620061009010835927F33098");
        bankAccount.setAccountHolder("Mr.GustaveFlaubert");
        paymentMean.setBankAccount(bankAccount);
        
        RelatedParty relatedParty = new RelatedParty();
        relatedParty.setId("1");
        relatedParty.setHref("http://serverlocation:port/partyManagement/individual/1");
        relatedParty.setRole("customer");
        relatedParty.setName("Gustave Flaubert");
        paymentMean.setRelatedParty(relatedParty);
        
        CreditCard creditcard = new CreditCard();
        creditcard.setType("MasterCard");
        gc.set(2020, 01, 01);
        creditcard.setExpirationDate(gc.getTime());
        creditcard.setNumber("CreditCardNumber");
        creditcard.setHolder("Gustave Flaubert");
        paymentMean.setCreditCard(creditcard);
        
        
        event.setResource(paymentMean);

        return event;
    }

}
