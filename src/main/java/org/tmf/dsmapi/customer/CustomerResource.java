/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.utils.Jackson;
import org.tmf.dsmapi.commons.jaxrs.PATCH;
import org.tmf.dsmapi.commons.utils.URIParser;
import org.tmf.dsmapi.customer.model.Customer;
import org.tmf.dsmapi.customer.event.CustomerEventPublisherLocal;
import org.tmf.dsmapi.customer.event.CustomerEvent;
import org.tmf.dsmapi.customer.event.CustomerEventFacade;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.CustomerAccountRef;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.customer.model.PaymentMeanRef;

@Stateless
@Path("/customerManagement/v2/customer")
public class CustomerResource {

    @EJB
    CustomerFacade customerFacade;
    @EJB
    CustomerEventFacade eventFacade;
    @EJB
    CustomerEventPublisherLocal publisher;

    public CustomerResource() {
    }

    /**
     * Test purpose only
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response create(Customer entity, @Context UriInfo info) throws BadUsageException, UnknownResourceException {
        customerFacade.checkCreation(entity);
        customerFacade.create(entity);
        entity.setHref(info.getAbsolutePath()+ "/" + Long.toString(entity.getId()));
        customerFacade.edit(entity);
        publisher.createNotification(entity, new Date());
        // 201
        Response response = Response.status(Response.Status.CREATED).entity(entity).build();
        return response;
    }

    @GET
    @Produces({"application/json"})
    public Response find(@Context UriInfo info) throws BadUsageException {

        // search queryParameters
        MultivaluedMap<String, String> queryParameters = info.getQueryParameters();

        Map<String, List<String>> mutableMap = new HashMap();
        for (Map.Entry<String, List<String>> e : queryParameters.entrySet()) {
            mutableMap.put(e.getKey(), e.getValue());
        }

        // fields to filter view
        Set<String> fieldSet = URIParser.getFieldsSelection(mutableMap);

        Set<Customer> resultList = findByCriteria(mutableMap);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(URIParser.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(URIParser.ID_FIELD);
            List<ObjectNode> nodeList = Jackson.createNodes(resultList, fieldSet);
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    // return Set of unique elements to avoid List with same elements in case of join
    private Set<Customer> findByCriteria(Map<String, List<String>> criteria) throws BadUsageException {

        List<Customer> resultList = null;
        if (criteria != null && !criteria.isEmpty()) {
            resultList = customerFacade.findByCriteria(criteria, Customer.class);
        } else {
            resultList = customerFacade.findAll();
        }
        if (resultList == null) {
            return new LinkedHashSet<Customer>();
        } else {
            return new LinkedHashSet<Customer>(resultList);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response get(@PathParam("id") long id, @Context UriInfo info) throws UnknownResourceException {

        // search queryParameters
        MultivaluedMap<String, String> queryParameters = info.getQueryParameters();

        Map<String, List<String>> mutableMap = new HashMap();
        for (Map.Entry<String, List<String>> e : queryParameters.entrySet()) {
            mutableMap.put(e.getKey(), e.getValue());
        }

        // fields to filter view
        Set<String> fieldSet = URIParser.getFieldsSelection(mutableMap);

        Customer customer = customerFacade.find(id);
        Response response;

        // If the result list (list of bills) is not empty, it conains only 1 unique bill
        if (customer != null) {
            // 200
            if (fieldSet.isEmpty() || fieldSet.contains(URIParser.ALL_FIELDS)) {
                response = Response.ok(customer).build();
            } else {
                fieldSet.add(URIParser.ID_FIELD);
                ObjectNode node = Jackson.createNode(customer, fieldSet);
                response = Response.ok(node).build();
            }
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
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
            publisher.updateNotification(entity, new Date());
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
     * @param id
     * @return
     * @throws UnknownResourceException
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) throws UnknownResourceException {
            Customer entity = customerFacade.find(id);

            // Event deletion
            publisher.deleteNotification(entity, new Date());
            try {
                //Pause for 4 seconds to finish notification
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                // Log someting to the console (should never happen)
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
            // 204 
            return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response patch(@PathParam("id") long id, Customer partialCustomer) throws BadUsageException, UnknownResourceException {
        Response response = null;
        
        Customer currentCustomer = customerFacade.patchAttributs(id, partialCustomer);
        
        // 200 OK + location
        response = Response.status(Response.Status.OK).entity(currentCustomer).build();

        return response;
    }
}
