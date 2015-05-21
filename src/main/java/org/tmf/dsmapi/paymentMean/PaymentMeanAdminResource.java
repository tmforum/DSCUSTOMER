package org.tmf.dsmapi.paymentMean;

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
import org.tmf.dsmapi.customer.model.BankAccount;
import org.tmf.dsmapi.customer.model.CreditCard;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.customer.model.RelatedParty;
import org.tmf.dsmapi.customer.model.ValidFor;
import org.tmf.dsmapi.paymentMean.event.PaymentMeanEvent;
import org.tmf.dsmapi.paymentMean.event.PaymentMeanEventFacade;
import org.tmf.dsmapi.paymentMean.event.PaymentMeanEventPublisherLocal;

@Stateless
@Path("/admin/paymentMean")
public class PaymentMeanAdminResource {

    @EJB
    PaymentMeanFacade customerFacade;
    @EJB
    PaymentMeanEventFacade eventFacade;
//    @EJB
//    PaymentMeanEventPublisherLocal publisher;

    @GET
    @Produces({"application/json"})
    public List<PaymentMean> findAll() {
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
    public Response post(List<PaymentMean> entities, @Context UriInfo info) throws UnknownResourceException {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = customerFacade.count();
        int affectedRows=0;

        // Try to persist entities
        try {
            for (PaymentMean entitie : entities) {
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
    public Response update(@PathParam("id") long id, PaymentMean entity) throws UnknownResourceException {
        Response response = null;
        PaymentMean customer = customerFacade.find(id);
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
        List<PaymentMean> pis = customerFacade.findAll();
        for (PaymentMean pi : pis) {
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
        PaymentMean entity = customerFacade.find(id);

        // Event deletion
//        publisher.deleteNotification(entity, new Date());
        try {
            //Pause for 4 seconds to finish notification
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PaymentMeanAdminResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        // remove event(s) binding to the resource
        List<PaymentMeanEvent> events = eventFacade.findAll();
        for (PaymentMeanEvent event : events) {
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
    public List<PaymentMeanEvent> findAllEvents() {
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
        List<PaymentMeanEvent> events = eventFacade.findAll();
        for (PaymentMeanEvent event : events) {
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
    public PaymentMean proto() {
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

        return paymentMean;
    }

}
