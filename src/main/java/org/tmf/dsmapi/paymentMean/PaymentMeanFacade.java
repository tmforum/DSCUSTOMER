package org.tmf.dsmapi.paymentMean;

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
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.paymentMean.event.PaymentMeanEventPublisherLocal;

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

    public void checkCreation(PaymentMean paymentMean) throws BadUsageException, UnknownResourceException {

        if (paymentMean.getId() != null) {
            if (this.find(paymentMean.getId()) != null) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, 
                        "Duplicate Exception, PaymentMean with same id :"+paymentMean.getId()+" alreay exists");
            }
        }

        if (null == paymentMean.getName()
                || paymentMean.getName().isEmpty()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "name is mandatory");
        }

        if (null == paymentMean.getRelatedParty()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty is mandatory");
        }

        if (null == paymentMean.getType()
                || paymentMean.getType().isEmpty()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "type is mandatory");
        } else {
            if (!paymentMean.getType().equalsIgnoreCase("CreditCard")) {
                if (null == paymentMean.getBankAccount()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.bankAccount is mandatory if patmentMean.type is not 'CreditCard'");
                }
            } else {
                if (null == paymentMean.getCreditCard()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.creditCard is mandatory if patmentMean.type is not 'CreditCard'");
                }
            }
        }

    }

    public PaymentMean patchAttributs(long id, PaymentMean partialPaymentMean) throws UnknownResourceException, BadUsageException {
        PaymentMean currentPaymentMean = this.find(id);

        if (currentPaymentMean == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        if (null != partialPaymentMean.getId()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "'id' is not patchable");
        }

        if (null != partialPaymentMean.getHref()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "'href is not patchable");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialPaymentMean, JsonNode.class);


        partialPaymentMean.setId(id);
        if (BeanUtils.patch(currentPaymentMean, partialPaymentMean, node)) {
            publisher.updateNotification(currentPaymentMean, new Date());
        }

        return currentPaymentMean;
    }

}
