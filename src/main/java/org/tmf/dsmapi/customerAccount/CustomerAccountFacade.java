package org.tmf.dsmapi.customerAccount;

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
import org.tmf.dsmapi.customer.model.Contact;
import org.tmf.dsmapi.customer.model.Customer;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.CustomerAccountBalance;
import org.tmf.dsmapi.customer.model.CustomerAccountRelationship;
import org.tmf.dsmapi.customer.model.CustomerAccountTaxExemption;
import org.tmf.dsmapi.customer.model.PaymentPlan;
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
        entity.setLastModified(new Date());

        super.create(entity);
    }

    public void checkCreationOrUpdate(CustomerAccount customerAccount) throws BadUsageException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(customerAccount, JsonNode.class);

        List<String> updateNodesName = new ArrayList<>();
        updateNodesName = BeanUtils.getNodesName(node, customerAccount, "ca", updateNodesName);

        if (!updateNodesName.contains("ca.name")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "name is mandatory");
        }

        if (!updateNodesName.contains("ca.accountType")) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "accountType is mandatory");
        }

        if (updateNodesName.contains("ca.customerAccountTaxExemption")) {
            List<CustomerAccountTaxExemption> l_customerAccountTaxExemption = customerAccount.getCustomerAccountTaxExemption();
            for (CustomerAccountTaxExemption customerAccountTaxExemption : l_customerAccountTaxExemption) {
                if (null == customerAccountTaxExemption.getIssuingJurisdiction()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountTaxExemption.issuingJurisdiction is mandatory");
                }
                if (null == customerAccountTaxExemption.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountTaxExemption.validFor is mandatory");
                }
            }
        }

        if (updateNodesName.contains("ca.customerAccountRelationship")) {
            List<CustomerAccountRelationship> l_customerAccountRelationship = customerAccount.getCustomerAccountRelationship();
            for (CustomerAccountRelationship customerAccountRelationship : l_customerAccountRelationship) {
                if (null == customerAccountRelationship.getRelationshipType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountRelationship.relationshipType is mandatory");
                }
                if (null == customerAccountRelationship.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountRelationship.validFor is mandatory");
                }
            }
        }

        if (updateNodesName.contains("ca.contact")) {
            List<Contact> l_contact = customerAccount.getContact();
            for (Contact contact : l_contact) {
                if (null == contact.getType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contact.type is mandatory");
                }
                if (null == contact.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contact.validFor is mandatory");
                }
            }
        }

        if (!updateNodesName.contains("ca.customer")) {
            if (!updateNodesName.contains("ca.customer.id")) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customer.id is mandatory");
            }
            if (!updateNodesName.contains("ca.customer.href")) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customer.href is mandatory");
            }
        }

        if (updateNodesName.contains("ca.customerAccountBalance")) {
            List<CustomerAccountBalance> l_customerAccountBalance = customerAccount.getCustomerAccountBalance();
            for (CustomerAccountBalance customerAccountBalance : l_customerAccountBalance) {
//                if (null == customerAccountBalance.getHjid()) {
//                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountBalance.hjid is mandatory");
//                }
                if (null == customerAccountBalance.getType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountBalance.type is mandatory");
                }
                if (null == customerAccountBalance.getAmount()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountBalance.ammount is mandatory");
                }
                if (null == customerAccountBalance.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountBalance.validFor is mandatory");
                }
                if (null == customerAccountBalance.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccountBalance.status is mandatory");
                }
            }
        }

        if (updateNodesName.contains("ca.paymentPlan")) {
            List<PaymentPlan> l_paymentPlan = customerAccount.getPaymentPlan();
            for (PaymentPlan paymentPlan : l_paymentPlan) {
//                if (null == paymentPlan.getHjid()) {
//                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentPlan.hjid is mandatory");
//                }
                if (null == paymentPlan.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentPlan.status is mandatory");
                }
                if (null == paymentPlan.getAmount()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentPlan.amount is mandatory");
                }
                if (null == paymentPlan.getPaymentFrequency()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentPlan.paymentFrequency is mandatory");
                }
                if (null == paymentPlan.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentPlan.validFor is mandatory");
                }
            }
        }

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
            publisher.updateNotification(currentCustomerAccount, new Date());
        }

        return currentCustomerAccount;
    }
}
