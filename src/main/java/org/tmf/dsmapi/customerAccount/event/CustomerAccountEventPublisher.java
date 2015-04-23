package org.tmf.dsmapi.customerAccount.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.hub.Hub;
import org.tmf.dsmapi.hub.HubFacade;

/**
 *
 * Should be async or called with MDB
 */
@Stateless
@Asynchronous
public class CustomerAccountEventPublisher implements CustomerAccountEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    @EJB
    CustomerAccountEventFacade eventFacade;
    @EJB
    CustomerAccountRESTEventPublisherLocal restEventPublisherLocal;

    /** 
     * Add business logic below. (Right-click in editor and choose
     * "Insert Code > Add Business Method")
     * Access Hubs using callbacks and send to http publisher 
     *(pool should be configured around the RESTEventPublisher bean)
     * Loop into array of Hubs
     * Call RestEventPublisher - Need to implement resend policy plus eviction
     * Filtering is done in RestEventPublisher based on query expression
    */ 
    @Override
    public void publish(CustomerAccountEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(CustomerAccountEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(CustomerAccount bean, Date date) {
        CustomerAccountEvent event = new CustomerAccountEvent();
        event.setEventTime(date);
        event.setEventType(CustomerAccountEventTypeEnum.CustomerAccountCreationNotification);
        event.setResource(bean);
        publish(event);

    }

    @Override
    public void deletionNotification(CustomerAccount bean, Date date) {
        CustomerAccountEvent event = new CustomerAccountEvent();
        event.setEventTime(date);
        event.setEventType(CustomerAccountEventTypeEnum.CustomerAccountDeletionNotification);
        event.setResource(bean);
        publish(event);
    }
	
    @Override
    public void updateNotification(CustomerAccount bean, Date date) {
        CustomerAccountEvent event = new CustomerAccountEvent();
        event.setEventTime(date);
        event.setEventType(CustomerAccountEventTypeEnum.CustomerAccountUpdateNotification);
        event.setResource(bean);
        publish(event);
    }

    @Override
    public void valueChangedNotification(CustomerAccount bean, Date date) {
        CustomerAccountEvent event = new CustomerAccountEvent();
        event.setEventTime(date);
        event.setEventType(CustomerAccountEventTypeEnum.CustomerAccountValueChangeNotification);
        event.setResource(bean);
        publish(event);
    }
}
