package org.tmf.dsmapi.customer.event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

@Stateless
public class CustomerEventFacade extends AbstractFacade<CustomerEvent>{
    
    @PersistenceContext(unitName = "DSCustomerPU")
    private EntityManager em;
   

    
    /**
     *
     */
    public CustomerEventFacade() {
        super(CustomerEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
