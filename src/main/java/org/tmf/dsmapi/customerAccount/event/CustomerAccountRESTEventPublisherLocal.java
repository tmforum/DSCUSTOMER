package org.tmf.dsmapi.customerAccount.event;

import javax.ejb.Local;
import org.tmf.dsmapi.hub.Hub;

@Local
public interface CustomerAccountRESTEventPublisherLocal {

    public void publish(Hub hub, CustomerAccountEvent event);
    
}
