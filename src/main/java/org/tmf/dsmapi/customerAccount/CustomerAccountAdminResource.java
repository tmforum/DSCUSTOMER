package org.tmf.dsmapi.customerAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.jaxrs.Report;
import org.tmf.dsmapi.customer.model.Contact;
import org.tmf.dsmapi.customer.model.ContactMedium;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.CustomerAccountBalance;
import org.tmf.dsmapi.customer.model.CustomerAccountRef;
import org.tmf.dsmapi.customer.model.CustomerAccountRelationship;
import org.tmf.dsmapi.customer.model.CustomerAccountTaxExemption;
import org.tmf.dsmapi.customer.model.CustomerRef;
import org.tmf.dsmapi.customer.model.Medium;
import org.tmf.dsmapi.customer.model.PaymentMeanRef;
import org.tmf.dsmapi.customer.model.PaymentPlan;
import org.tmf.dsmapi.customer.model.ValidFor;
import org.tmf.dsmapi.customerAccount.event.CustomerAccountEvent;
import org.tmf.dsmapi.customerAccount.event.CustomerAccountEventFacade;
import org.tmf.dsmapi.customerAccount.event.CustomerAccountEventPublisherLocal;

@Stateless
@Path("/admin/customerAccount")
public class CustomerAccountAdminResource {

    @EJB
    CustomerAccountFacade customerFacade;
    @EJB
    CustomerAccountEventFacade eventFacade;
//    @EJB
//    CustomerAccountEventPublisherLocal publisher;

    @GET
    @Produces({"application/json"})
    public List<CustomerAccount> findAll() {
        return customerFacade.findAll();
    }

    /**
     *
     * For test purpose only
     *
     * @param entities
     * @return
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response post(List<CustomerAccount> entities, @Context UriInfo info) throws UnknownResourceException {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = customerFacade.count();
        int affectedRows=0;

        // Try to persist entities
        try {
            for (CustomerAccount entitie : entities) {
                customerFacade.checkCreationOrUpdate(entitie);
                customerFacade.create(entitie);
                entitie.setHref(info.getAbsolutePath() + "/" + Long.toString(entitie.getId()));
                customerFacade.edit(entitie);
                affectedRows = affectedRows + 1;
//                publisher.createNotification(entitie, new Date());
            }
        } catch (BadUsageException e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        Report stat = new Report(customerFacade.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response update(@PathParam("id") long id, CustomerAccount entity) throws UnknownResourceException {
        Response response = null;
        CustomerAccount customer = customerFacade.find(id);
        if (customer != null) {
            entity.setId(id);
            customerFacade.edit(entity);
//            publisher.updateNotification(entity, new Date());
            // 200 OK + location
            response = Response.status(Response.Status.OK).entity(entity).build();

        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    /**
     *
     * For test purpose only
     *
     * @return
     * @throws org.tmf.dsmapi.commons.exceptions.UnknownResourceException
     */
    @DELETE
    public Report deleteAll() throws UnknownResourceException {

        eventFacade.removeAll();
        int previousRows = customerFacade.count();
        customerFacade.removeAll();
        List<CustomerAccount> pis = customerFacade.findAll();
        for (CustomerAccount pi : pis) {
            delete(pi.getId());
        }

        int currentRows = customerFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    /**
     *
     * For test purpose only
     *
     * @param id
     * @return
     * @throws UnknownResourceException
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) throws UnknownResourceException {
        int previousRows = customerFacade.count();
        CustomerAccount entity = customerFacade.find(id);

        // Event deletion
//        publisher.deleteNotification(entity, new Date());
        try {
            //Pause for 4 seconds to finish notification
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CustomerAccountAdminResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        // remove event(s) binding to the resource
        List<CustomerAccountEvent> events = eventFacade.findAll();
        for (CustomerAccountEvent event : events) {
            if (event.getResource().getId().equals(id)) {
                eventFacade.remove(event.getId());
            }
        }
        //remove resource
        customerFacade.remove(id);

        int affectedRows = 1;
        Report stat = new Report(customerFacade.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 200 
        Response response = Response.ok(stat).build();
        return response;
    }

    @GET
    @Produces({"application/json"})
    @Path("event")
    public List<CustomerAccountEvent> findAllEvents() {
        return eventFacade.findAll();
    }

    @DELETE
    @Path("event")
    public Report deleteAllEvent() {

        int previousRows = eventFacade.count();
        eventFacade.removeAll();
        int currentRows = eventFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("event/{id}")
    public Response deleteEvent(@PathParam("id") String id) throws UnknownResourceException {

        int previousRows = eventFacade.count();
        List<CustomerAccountEvent> events = eventFacade.findAll();
        for (CustomerAccountEvent event : events) {
            if (event.getResource().getId().equals(id)) {
                eventFacade.remove(event.getId());

            }
        }
        int currentRows = eventFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 200 
        Response response = Response.ok(stat).build();
        return response;
    }

    /**
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces({"application/json"})
    public Report count() {
        return new Report(customerFacade.count());
    }

    @GET
    @Produces({"application/json"})
    @Path("proto")
    public CustomerAccount proto() {
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

        return customerAccount;
    }
}
