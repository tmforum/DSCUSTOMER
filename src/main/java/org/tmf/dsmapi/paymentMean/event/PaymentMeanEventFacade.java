package org.tmf.dsmapi.paymentMean.event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

@Stateless
public class PaymentMeanEventFacade extends AbstractFacade<PaymentMeanEvent>{
    
    @PersistenceContext(unitName = "DSCustomerPU")
    private EntityManager em;
   

    
    /**
     *
     */
    public PaymentMeanEventFacade() {
        super(PaymentMeanEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
