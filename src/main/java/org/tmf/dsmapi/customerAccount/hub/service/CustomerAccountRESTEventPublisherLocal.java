package org.tmf.dsmapi.customerAccount.hub.service;

import javax.ejb.Local;
import org.tmf.dsmapi.customerAccount.hub.model.CustomerAccountEvent;
import org.tmf.dsmapi.hub.model.Hub;

@Local
public interface CustomerAccountRESTEventPublisherLocal {

    public void publish(Hub hub, CustomerAccountEvent event);
    
}
