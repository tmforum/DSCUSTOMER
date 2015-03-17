package org.tmf.dsmapi.customerAccount.service;

import org.tmf.dsmapi.commons.facade.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customerAccount.hub.service.CustomerAccountEventPublisherLocal;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class CustomerAccountFacade extends AbstractFacade<CustomerAccount> {

    @PersistenceContext(unitName = "DSCustomerPU")
    private EntityManager em;
    @EJB
    CustomerAccountEventPublisherLocal publisher;

    public CustomerAccountFacade() {
        super(CustomerAccount.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(CustomerAccount entity) throws BadUsageException {
        if (entity.getId() != null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, "While creating CustomerAccount, id must be null");
        }

        super.create(entity);
    }

}
