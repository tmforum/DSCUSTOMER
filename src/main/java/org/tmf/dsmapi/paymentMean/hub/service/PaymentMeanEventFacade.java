package org.tmf.dsmapi.paymentMean.hub.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.facade.AbstractFacade;
import org.tmf.dsmapi.paymentMean.hub.model.PaymentMeanEvent;

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
