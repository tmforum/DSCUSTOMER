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
import org.tmf.dsmapi.customer.model.CustomerAccount;
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

    @Override
    public void create(PaymentMean entity) throws BadUsageException {
        if (entity.getId() != null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, "While creating PaymentMean, id must be null");
        }

        super.create(entity);
    }

    public PaymentMean updateAttributs(long id, PaymentMean partialPaymentMean) throws UnknownResourceException, BadUsageException {
        PaymentMean currentPaymentMean = this.find(id);

        if (currentPaymentMean == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialPaymentMean, JsonNode.class);


        partialPaymentMean.setId(id);
        if (BeanUtils.patch(currentPaymentMean, partialPaymentMean, node)) {
            publisher.valueChangedNotification(currentPaymentMean, new Date());
        }

        return currentPaymentMean;
    }

}
