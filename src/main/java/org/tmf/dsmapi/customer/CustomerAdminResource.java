package org.tmf.dsmapi.customer;

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
import org.tmf.dsmapi.customer.model.Customer;
import org.tmf.dsmapi.customer.event.CustomerEvent;
import org.tmf.dsmapi.customer.event.CustomerEventFacade;
import org.tmf.dsmapi.customer.event.CustomerEventPublisherLocal;
import org.tmf.dsmapi.customer.model.Characteristic;
import org.tmf.dsmapi.customer.model.ContactMedium;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.CustomerAccountRef;
import org.tmf.dsmapi.customer.model.CustomerCreditProfile;
import org.tmf.dsmapi.customer.model.Medium;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.customer.model.PaymentMeanRef;
import org.tmf.dsmapi.customer.model.RelatedParty;
import org.tmf.dsmapi.customer.model.ValidFor;

@Stateless
@Path("/admin/customer")
public class CustomerAdminResource {

    @EJB
    CustomerFacade customerFacade;
    @EJB
    CustomerEventFacade eventFacade;
//    @EJB
//    CustomerEventPublisherLocal publisher;

    @GET
    @Produces({"application/json"})
    public List<Customer> findAll() {
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
    public Response post(List<Customer> entities, @Context UriInfo info) throws UnknownResourceException {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = customerFacade.count();
        int affectedRows=0;

        // Try to persist entities
        try {
            for (Customer entitie : entities) {
                customerFacade.checkCreation(entitie);
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
    public Response update(@PathParam("id") long id, Customer entity) throws UnknownResourceException {
        Response response = null;
        Customer customer = customerFacade.find(id);
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
        List<Customer> pis = customerFacade.findAll();
        for (Customer pi : pis) {
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
        Customer entity = customerFacade.find(id);

        // Event deletion
//        publisher.deleteNotification(entity, new Date());
        try {
            //Pause for 4 seconds to finish notification
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CustomerAdminResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        // remove event(s) binding to the resource
        List<CustomerEvent> events = eventFacade.findAll();
        for (CustomerEvent event : events) {
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
    public List<CustomerEvent> findAllEvents() {
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
        List<CustomerEvent> events = eventFacade.findAll();
        for (CustomerEvent event : events) {
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
    public Customer proto() {
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

        return customer;
    }
}
