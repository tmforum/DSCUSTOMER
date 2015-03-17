package org.tmf.dsmapi.paymentMean.hub.service;

import javax.ejb.Local;
import org.tmf.dsmapi.paymentMean.hub.model.PaymentMeanEvent;
import org.tmf.dsmapi.hub.model.Hub;

@Local
public interface PaymentMeanRESTEventPublisherLocal {

    public void publish(Hub hub, PaymentMeanEvent event);
    
}
