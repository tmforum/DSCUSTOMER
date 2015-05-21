package org.tmf.dsmapi.customerAccount;

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

    public void checkCreationOrUpdate(CustomerAccount customerAccount) throws BadUsageException, UnknownResourceException {

        if (customerAccount.getId() != null) {
            if (this.find(customerAccount.getId()) != null) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC, 
                        "Duplicate Exception, CustomerAccount with same id :"+customerAccount.getId()+" alreay exists");
            }
        }

        customerAccount.setLastModified(new Date());

        if (null == customerAccount.getName()
                || customerAccount.getName().isEmpty()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                    "name is mandatory");
        }

        if (null == customerAccount.getAccountType()
                || customerAccount.getAccountType().isEmpty()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                    "accountType is mandatory");
        }

        if (null != customerAccount.getCustomerAccountTaxExemption()
                && !customerAccount.getCustomerAccountTaxExemption().isEmpty()) {
            List<CustomerAccountTaxExemption> l_customerAccountTaxExemption = customerAccount.getCustomerAccountTaxExemption();
            for (CustomerAccountTaxExemption customerAccountTaxExemption : l_customerAccountTaxExemption) {
                if (null == customerAccountTaxExemption.getIssuingJurisdiction()
                        || customerAccountTaxExemption.getIssuingJurisdiction().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountTaxExemption.issuingJurisdiction is mandatory");
                }
                if (null == customerAccountTaxExemption.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountTaxExemption.validFor is mandatory");
                } else {
                    if (customerAccountTaxExemption.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "customerAccountTaxExemption.validFor.startDateTime is mandatory");
                    }
                }
            }
        }

        if (null != customerAccount.getCustomerAccountRelationship()
                && !customerAccount.getCustomerAccountRelationship().isEmpty() ) {
            List<CustomerAccountRelationship> l_customerAccountRelationship = customerAccount.getCustomerAccountRelationship();
            for (CustomerAccountRelationship customerAccountRelationship : l_customerAccountRelationship) {
                if (null == customerAccountRelationship.getRelationshipType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountRelationship.relationshipType is mandatory");
                }
                if (null == customerAccountRelationship.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS,
                            "customerAccountRelationship.validFor is mandatory");
                } else {
                    if (customerAccountRelationship.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "customerAccountRelationship.validFor.startDateTime is mandatory");
                    }
                }
            }
        }

        if (null != customerAccount.getContact()
                && !customerAccount.getContact().isEmpty()) {
            List<Contact> l_contact = customerAccount.getContact();
            for (Contact contact : l_contact) {
                if (null == contact.getType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "contact.type is mandatory");
                }
                if (null == contact.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "contact.validFor is mandatory");
                } else {
                    if (contact.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "contact.validFor.startDateTime is mandatory");
                    }
                }
            }
        }

        if (null != customerAccount.getCustomer()) {
            if (null == customerAccount.getCustomer().getId()) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                        "customer.id is mandatory");
            }
            if (null == customerAccount.getCustomer().getHref()) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                        "customer.href is mandatory");
            }
        }

        if (null != customerAccount.getCustomerAccountBalance()
                && ! customerAccount.getCustomerAccountBalance().isEmpty()) {
            List<CustomerAccountBalance> l_customerAccountBalance = customerAccount.getCustomerAccountBalance();
            for (CustomerAccountBalance customerAccountBalance : l_customerAccountBalance) {
                if (null == customerAccountBalance.getType()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountBalance.type is mandatory");
                }
                if (null == customerAccountBalance.getAmount()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountBalance.ammount is mandatory");
                }
                if (null == customerAccountBalance.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountBalance.validFor is mandatory");
                } else {
                    if (customerAccountBalance.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "customerAccountBalance.validFor.startDateTime is mandatory");
                    }
                }
                if (null == customerAccountBalance.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "customerAccountBalance.status is mandatory");
                }
            }
        }

        if (null != customerAccount.getPaymentPlan()
                && !customerAccount.getPaymentPlan().isEmpty()) {
            List<PaymentPlan> l_paymentPlan = customerAccount.getPaymentPlan();
            for (PaymentPlan paymentPlan : l_paymentPlan) {
                if (null == paymentPlan.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "paymentPlan.status is mandatory");
                }
                if (null == paymentPlan.getAmount()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "paymentPlan.amount is mandatory");
                }
                if (null == paymentPlan.getPaymentFrequency()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "paymentPlan.paymentFrequency is mandatory");
                }
                if (null == paymentPlan.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, 
                            "paymentPlan.validFor is mandatory");
                } else {
                    if (paymentPlan.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "paymentPlan.validFor.startDateTime is mandatory");
                    }
                }
            }
        }

    }

    public CustomerAccount patchAttributs(long id, CustomerAccount partialCustomerAccount) throws UnknownResourceException, BadUsageException {
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
