package org.tmf.dsmapi.customer.hub.service;

import javax.ejb.Local;
import org.tmf.dsmapi.customer.hub.model.CustomerEvent;
import org.tmf.dsmapi.hub.model.Hub;

@Local
public interface CustomerRESTEventPublisherLocal {

    public void publish(Hub hub, CustomerEvent event);
    
}
