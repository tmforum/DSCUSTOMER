package org.tmf.dsmapi.paymentMean.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.hub.Hub;
import org.tmf.dsmapi.hub.HubFacade;

/**
 *
 * Should be async or called with MDB
 */
@Stateless
@Asynchronous
public class PaymentMeanEventPublisher implements PaymentMeanEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    @EJB
    PaymentMeanEventFacade eventFacade;
    @EJB
    PaymentMeanRESTEventPublisherLocal restEventPublisherLocal;

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
    public void publish(PaymentMeanEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(PaymentMeanEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(PaymentMean bean, Date date) {
        PaymentMeanEvent event = new PaymentMeanEvent();
        event.setEventTime(date);
        event.setEventType(PaymentMeanEventTypeEnum.PaymentMeanCreationNotification);
        event.setResource(bean);
        publish(event);

    }

    @Override
    public void deletionNotification(PaymentMean bean, Date date) {
        PaymentMeanEvent event = new PaymentMeanEvent();
        event.setEventTime(date);
        event.setEventType(PaymentMeanEventTypeEnum.PaymentMeanDeletionNotification);
        event.setResource(bean);
        publish(event);
    }
	
    @Override
    public void updateNotification(PaymentMean bean, Date date) {
        PaymentMeanEvent event = new PaymentMeanEvent();
        event.setEventTime(date);
        event.setEventType(PaymentMeanEventTypeEnum.PaymentMeanUpdateNotification);
        event.setResource(bean);
        publish(event);
    }

    @Override
    public void valueChangedNotification(PaymentMean bean, Date date) {
        PaymentMeanEvent event = new PaymentMeanEvent();
        event.setEventTime(date);
        event.setEventType(PaymentMeanEventTypeEnum.PaymentMeanValueChangeNotification);
        event.setResource(bean);
        publish(event);
    }
}
