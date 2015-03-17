package org.tmf.dsmapi.paymentMean.service;

import org.tmf.dsmapi.commons.facade.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.paymentMean.hub.service.PaymentMeanEventPublisherLocal;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class PaymentMeanFacade extends AbstractFacade<PaymentMean> {

    @PersistenceContext(unitName = "DSCustomerPU")
    private EntityManager em;
    @EJB
    PaymentMeanEventPublisherLocal publisher;

    public PaymentMeanFacade() {
        super(PaymentMean.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(PaymentMean entity) throws BadUsageException {
        if (entity.getId() != null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, "While creating PaymentMean, id must be null");
        }

        super.create(entity);
    }

}
