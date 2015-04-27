package org.tmf.dsmapi.paymentMean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    public void create(PaymentMean entity) throws BadUsageException {
        if (entity.getId() != null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, "While creating PaymentMean, id must be null");
        }

        super.create(entity);
    }

    public void checkCreation(PaymentMean paymentMean) throws BadUsageException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(paymentMean, JsonNode.class);

        List<String> updateNodesName = new ArrayList<>();
        updateNodesName = BeanUtils.getNodesName(node, paymentMean, "paymentMean", updateNodesName);

        if (!updateNodesName.contains("paymentMean.name")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "name is mandatory");
        }

        if (!updateNodesName.contains("paymentMean.relatedParty")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty is mandatory");
        }

        if (!updateNodesName.contains("paymentMean.type")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "type is mandatory");
        } else {
            if (! paymentMean.getType().equalsIgnoreCase("CreditCard")) {
                if (!updateNodesName.contains("paymentMean.bankAccount")) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.bankAccount is mandatory if patmentMean.type is not 'CreditCard'");
                }
            } else {
                if (!updateNodesName.contains("paymentMean.creditCard")) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.creditCard is mandatory if patmentMean.type is not 'CreditCard'");
                }
            }
        }

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
            publisher.updateNotification(currentPaymentMean, new Date());
        }

        return currentPaymentMean;
    }

    public void checkUpdate(PaymentMean paymentMean) throws BadUsageException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(paymentMean, JsonNode.class);

        List<String> updateNodesName = new ArrayList<>();
        updateNodesName = BeanUtils.getNodesName(node, paymentMean, "paymentMean", updateNodesName);

        if (updateNodesName.contains("paymentMean.id")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "'id' is not modifiable");
        }

        if (updateNodesName.contains("paymentMean.href")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "'href is not modifiable");
        }

        if (!updateNodesName.contains("paymentMean.name")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "name is mandatory");
        }

        if (!updateNodesName.contains("paymentMean.validFor")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "validFor is mandatory");
        }
    }

}
