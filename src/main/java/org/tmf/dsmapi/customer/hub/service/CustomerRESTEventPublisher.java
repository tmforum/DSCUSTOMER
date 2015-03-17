package org.tmf.dsmapi.customer.hub.service;

import java.util.List;
import java.util.Set;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MultivaluedMap;
import org.codehaus.jackson.node.ObjectNode;
import org.tmf.dsmapi.commons.utils.Jackson;
import org.tmf.dsmapi.commons.utils.URIParser;
import org.tmf.dsmapi.customer.hub.model.CustomerEvent;
import org.tmf.dsmapi.hub.model.Hub;
import org.tmf.dsmapi.hub.service.RESTClient;

@Stateless
//@Asynchronous
public class CustomerRESTEventPublisher implements CustomerRESTEventPublisherLocal {

    @EJB
    CustomerEventFacade eventFacade;

    @EJB
    RESTClient client;

    @Override
    public void publish(Hub hub, CustomerEvent event) {

        MultivaluedMap<String, String> query = URIParser.getParameters(hub.getQuery());
        query.putSingle("id", event.getId());

        // fields to filter view
        Set<String> fieldSet = URIParser.getFieldsSelection(query);

        List<CustomerEvent> resultList = null;
        resultList = eventFacade.findByCriteria(query, CustomerEvent.class);

        if (resultList != null && !resultList.isEmpty()) {
            if (!fieldSet.isEmpty() && !fieldSet.contains(URIParser.ALL_FIELDS)) {
                fieldSet.add("id");
                fieldSet.add("date");
                fieldSet.add("eventType");
                fieldSet.add("reason");
                ObjectNode rootNode = Jackson.createNode(event, fieldSet);
                client.publishEvent(hub.getCallback(), rootNode);
            } else {
                client.publishEvent(hub.getCallback(), event);
            }
            
        }
    }

}
