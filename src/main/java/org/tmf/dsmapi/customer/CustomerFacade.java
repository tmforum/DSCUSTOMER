package org.tmf.dsmapi.customer;

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
import org.tmf.dsmapi.customer.model.Customer;
import org.tmf.dsmapi.customer.event.CustomerEventPublisherLocal;
import org.tmf.dsmapi.customer.model.Characteristic;
import org.tmf.dsmapi.customer.model.ContactMedium;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.CustomerCreditProfile;
import org.tmf.dsmapi.customer.model.PaymentMean;
import org.tmf.dsmapi.customer.model.RelatedParty;

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

    public void checkCreation(Customer newCustomer) throws BadUsageException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(newCustomer, JsonNode.class);

        List<String> updateNodesName = new ArrayList<>();
        updateNodesName = BeanUtils.getNodesName(node, newCustomer, "customer", updateNodesName);

        if (!updateNodesName.contains("customer.name")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "name is mandatory");
        }

        if (updateNodesName.contains("customer.relatedParty")) {
            if (null == newCustomer.getRelatedParty().getHref()) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty.href is mandatory");
            }
        }

        if (updateNodesName.contains("customer.characteristic")) {
            List<Characteristic> l_characteristic = newCustomer.getCharacteristic();
            for (Characteristic characteristic : l_characteristic) {
                if (null == characteristic.getName()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.name is mandatory");
                }
                if (null == characteristic.getValue()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.value is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.contactMedium")) {
            List<ContactMedium> l_contactMedium = newCustomer.getContactMedium();
            for (ContactMedium contactMedium : l_contactMedium) {
                if (null == contactMedium.getType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.type is mandatory");
                }
                if (null == contactMedium.getMedium()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.medium is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.customerAccount")) {
            List<CustomerAccount> l_customerAccount = newCustomer.getCustomerAccount();
            for (CustomerAccount customerAccount : l_customerAccount) {
                if (null == customerAccount.getId()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.id is mandatory");
                }
                if (null == customerAccount.getName()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.name is mandatory");
                }
                if (null == customerAccount.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.status is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.customerCreditProfile")) {
            List<CustomerCreditProfile> l_customerCreditProfile = newCustomer.getCustomerCreditProfile();
            for (CustomerCreditProfile customerCreditProfile : l_customerCreditProfile) {
                if (null == customerCreditProfile.getCreditProfileDate()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.creditProfileDate is mandatory");
                }
                if (null == customerCreditProfile.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.validFor is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.paymentMean")) {
            List<PaymentMean> l_paymentMean = newCustomer.getPaymentMean();
            for (PaymentMean paymentMean : l_paymentMean) {
                if (null == paymentMean.getId()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.id is mandatory");
                }
                if (null == paymentMean.getHref()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.href is mandatory");
                }
            }
        }

    }

    public Customer updateAttributs(long id, Customer partialCustomer) throws UnknownResourceException, BadUsageException {
        Customer currentCustomer = this.find(id);

        if (currentCustomer == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialCustomer, JsonNode.class);
        partialCustomer.setId(id);

        List<String> updateNodesName = new ArrayList<>();
        updateNodesName = BeanUtils.getNodesName(node, partialCustomer, "customer", updateNodesName);

        if (updateNodesName.contains("customer.id")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "customer.id is not modifiable");
        }

        if (updateNodesName.contains("customer.relatedParty")) {
            if (null == partialCustomer.getRelatedParty().getId()) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty.id is mandatory");
            }
        }

        if (updateNodesName.contains("customer.characteristic")) {
            List<Characteristic> l_characteristic = partialCustomer.getCharacteristic();
            for (Characteristic characteristic : l_characteristic) {
                if (null == characteristic.getName()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.name is mandatory");
                }
                if (null == characteristic.getValue()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.value is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.contactMedium")) {
            List<ContactMedium> l_contactMedium = partialCustomer.getContactMedium();
            for (ContactMedium contactMedium : l_contactMedium) {
                if (null == contactMedium.getType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.type is mandatory");
                }
                if (null == contactMedium.getMedium()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.medium is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.customerAccount")) {
            List<CustomerAccount> l_customerAccount = partialCustomer.getCustomerAccount();
            for (CustomerAccount customerAccount : l_customerAccount) {
                if (null == customerAccount.getId()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.id is mandatory");
                }
                if (null == customerAccount.getName()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.name is mandatory");
                }
                if (null == customerAccount.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.status is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.customerCreditProfile")) {
            List<CustomerCreditProfile> l_customerCreditProfile = partialCustomer.getCustomerCreditProfile();
            for (CustomerCreditProfile customerCreditProfile : l_customerCreditProfile) {
                if (null == customerCreditProfile.getCreditProfileDate()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.creditProfileDate is mandatory");
                }
                if (null == customerCreditProfile.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.validFor is mandatory");
                }
            }
        }

        if (updateNodesName.contains("customer.paymentMean")) {
            List<PaymentMean> l_paymentMean = partialCustomer.getPaymentMean();
            for (PaymentMean paymentMean : l_paymentMean) {
                if (null == paymentMean.getId()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.id is mandatory");
                }
                if (null == paymentMean.getHref()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.href is mandatory");
                }
            }
        }

        partialCustomer.setId(id);
        if (BeanUtils.patch(currentCustomer, partialCustomer, node)) {
            publisher.updateNotification(currentCustomer, new Date());
        }

        return currentCustomer;
    }
}
