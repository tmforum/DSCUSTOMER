package org.tmf.dsmapi.customer;

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
import org.tmf.dsmapi.customer.event.CustomerEventPublisherLocal;

/**
 *
 * @author pierregauthier
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "DSCustomerPU")
    private EntityManager em;
    @EJB
    CustomerEventPublisherLocal publisher;

    public CustomerFacade() {
        super(Customer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Customer entity) throws BadUsageException {
        if (entity.getId() != null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, "While creating Customer, id must be null");
        }

        super.create(entity);
    }

    public Customer updateAttributs(long id, Customer partialCustomer) throws UnknownResourceException, BadUsageException {
        Customer currentCustomer = this.find(id);

        if (currentCustomer == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialCustomer, JsonNode.class);

//            String token = "characteristic";
//            if (!BeanUtils.verify(partialCustomer, node, token)) {
//                Logger.getLogger("CustomerResource").log(Level.INFO, "VERIFY : " + token + " NOT FOUND");
//                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, token + " is not found");
//            }
//
//            token = "contactMedium";
//            if (!BeanUtils.verify(partialCustomer, node, token)) {
//                Logger.getLogger("CustomerResource").log(Level.INFO, "VERIFY : " + token + " NOT FOUND");
//                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, token + " is not found");
//            }
//            token = "account";
//            if (!BeanUtils.verify(partialCustomer, node, token)) {
//                Logger.getLogger("CustomerResource").log(Level.INFO, "VERIFY : " + token + " NOT FOUND");
//                isOkForUpdate = false;
//                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, token+" is not found");
//            }
//
//            token = "creditProfile";
//            if (!BeanUtils.verify(partialCustomer, node, token)) {
//                Logger.getLogger("CustomerResource").log(Level.INFO, "VERIFY : " + token + " NOT FOUND");
//                isOkForUpdate = false;
//                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, token+" is not found");
//            }
//
//            token = "paymentMean";
//            if (!BeanUtils.verify(partialCustomer, node, token)) {
//                Logger.getLogger("CustomerResource").log(Level.INFO, "VERIFY : " + token + " NOT FOUND");
//                isOkForUpdate = false;
//                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, token+" is not found");
//            }

        partialCustomer.setId(id);
        if (BeanUtils.patch(currentCustomer, partialCustomer, node)) {
            publisher.valueChangedNotification(currentCustomer, new Date());
        }

        return currentCustomer;
    }
}
