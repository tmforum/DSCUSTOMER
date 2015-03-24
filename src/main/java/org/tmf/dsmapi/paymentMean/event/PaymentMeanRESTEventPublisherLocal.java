package org.tmf.dsmapi.paymentMean.event;

import javax.ejb.Local;
import org.tmf.dsmapi.hub.Hub;

@Local
public interface PaymentMeanRESTEventPublisherLocal {

    public void publish(Hub hub, PaymentMeanEvent event);
    
}
