package org.tmf.dsmapi.customer.event;

import javax.ejb.Local;
import org.tmf.dsmapi.hub.Hub;

@Local
public interface CustomerRESTEventPublisherLocal {

    public void publish(Hub hub, CustomerEvent event);
    
}
