package org.tmf.dsmapi.customerAccount;

import java.util.Date;
import org.tmf.dsmapi.commons.facade.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.utils.BeanUtils;
import org.tmf.dsmapi.customer.model.Customer;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customerAccount.event.CustomerAccountEventPublisherLocal;

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

    public CustomerAccount updateAttributs(long id, CustomerAccount partialCustomerAccount) throws UnknownResourceException, BadUsageException {
        CustomerAccount currentCustomerAccount = this.find(id);

        if (currentCustomerAccount == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialCustomerAccount, JsonNode.class);


        partialCustomerAccount.setId(id);
        if (BeanUtils.patch(currentCustomerAccount, partialCustomerAccount, node)) {
            publisher.valueChangedNotification(currentCustomerAccount, new Date());
        }

        return currentCustomerAccount;
    }
}
