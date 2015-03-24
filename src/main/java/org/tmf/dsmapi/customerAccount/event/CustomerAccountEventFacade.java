package org.tmf.dsmapi.customerAccount.event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

@Stateless
public class CustomerAccountEventFacade extends AbstractFacade<CustomerAccountEvent>{
    
    @PersistenceContext(unitName = "DSCustomerPU")
    private EntityManager em;
   

    
    /**
     *
     */
    public CustomerAccountEventFacade() {
        super(CustomerAccountEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
