package org.tmf.dsmapi.customer;

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
import org.tmf.dsmapi.customer.model.CustomerAccountRef;
import org.tmf.dsmapi.customer.model.CustomerCreditProfile;
import org.tmf.dsmapi.customer.model.PaymentMeanRef;

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

    public void checkCreation(Customer newCustomer) throws BadUsageException, UnknownResourceException {

        if (newCustomer.getId() != null) {
            if (this.find(newCustomer.getId()) != null) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                        "Duplicate Exception, Customer with same id :" + newCustomer.getId() + " alreay exists");
            }
        }

        if (null == newCustomer.getName()
                || newCustomer.getName().isEmpty()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "name is mandatory");
        }

        if (null != newCustomer.getRelatedParty()) {
            if (null == newCustomer.getRelatedParty().getHref()
                    || newCustomer.getRelatedParty().getHref().isEmpty()) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty.href is mandatory");
            }
        }

        if (null != newCustomer.getCharacteristic()
                && !newCustomer.getCharacteristic().isEmpty()) {
            List<Characteristic> l_characteristic = newCustomer.getCharacteristic();
            for (Characteristic characteristic : l_characteristic) {
                if (null == characteristic.getName()
                        || characteristic.getName().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.name is mandatory");
                }
                if (null == characteristic.getValue()
                        || characteristic.getValue().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.value is mandatory");
                }
            }
        }

        if (null != newCustomer.getContactMedium()
                && !newCustomer.getContactMedium().isEmpty()) {
            List<ContactMedium> l_contactMedium = newCustomer.getContactMedium();
            for (ContactMedium contactMedium : l_contactMedium) {
                if (null == contactMedium.getType()
                        || contactMedium.getType().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.type is mandatory");
                }
                if (null == contactMedium.getMedium()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.medium is mandatory");
                }
            }
        }

        if (null != newCustomer.getCustomerAccount()
                && !newCustomer.getCustomerAccount().isEmpty()) {
            List<CustomerAccountRef> l_customerAccount = newCustomer.getCustomerAccount();
            for (CustomerAccountRef customerAccountRef : l_customerAccount) {
                if (null == customerAccountRef.getId()
                        || customerAccountRef.getId().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.id is mandatory");
                }
                if (null == customerAccountRef.getName()
                        || customerAccountRef.getName().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.name is mandatory");
                }
                if (null == customerAccountRef.getStatus()
                        || customerAccountRef.getStatus().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.status is mandatory");
                }
            }
        }

        if (null != newCustomer.getCustomerCreditProfile()
                && !newCustomer.getCustomerCreditProfile().isEmpty()) {
            List<CustomerCreditProfile> l_customerCreditProfile = newCustomer.getCustomerCreditProfile();
            for (CustomerCreditProfile customerCreditProfile : l_customerCreditProfile) {
                if (null == customerCreditProfile.getCreditProfileDate()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.creditProfileDate is mandatory");
                }
                if (null == customerCreditProfile.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.validFor is mandatory");
                } else {
                    if (customerCreditProfile.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "startDateTime is mandatory");
                    }
                }
            }

            if (null != newCustomer.getPaymentMean()
                    && !newCustomer.getPaymentMean().isEmpty()) {
                List<PaymentMeanRef> l_paymentMean = newCustomer.getPaymentMean();
                for (PaymentMeanRef paymentMean : l_paymentMean) {
                    if (null == paymentMean.getId()
                            || paymentMean.getId().isEmpty()) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.id is mandatory");
                    }
                    if (null == paymentMean.getHref()
                            || paymentMean.getHref().isEmpty()) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.href is mandatory");
                    }
                }
            }

        }

    }

    public Customer patchAttributs(long id, Customer partialCustomer) throws UnknownResourceException, BadUsageException {
        Customer currentCustomer = this.find(id);

        if (currentCustomer == null) {
            throw new UnknownResourceException(ExceptionType.UNKNOWN_RESOURCE);
        }

        if (null != partialCustomer.getId()) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, "customer.id is not modifiable");
        }

        if (null != partialCustomer.getRelatedParty()) {
            if (null == partialCustomer.getRelatedParty().getId()
                    || partialCustomer.getRelatedParty().getId().isEmpty()) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "relatedParty.id is mandatory");
            }
        }

        if (null != partialCustomer.getCharacteristic()
                && !partialCustomer.getCharacteristic().isEmpty()) {
            List<Characteristic> l_characteristic = partialCustomer.getCharacteristic();
            for (Characteristic characteristic : l_characteristic) {
                if (null == characteristic.getName()
                        || characteristic.getName().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.name is mandatory");
                }
                if (null == characteristic.getValue()
                        || characteristic.getValue().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "characteristic.value is mandatory");
                }
            }
        }

        if (null != partialCustomer.getContactMedium()
                && !partialCustomer.getContactMedium().isEmpty()) {
            List<ContactMedium> l_contactMedium = partialCustomer.getContactMedium();
            for (ContactMedium contactMedium : l_contactMedium) {
                if (null == contactMedium.getType()
                        || contactMedium.getType().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.type is mandatory");
                }
                if (null == contactMedium.getMedium()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "contactMedium.medium is mandatory");
                }
            }
        }

        if (null != partialCustomer.getCustomerAccount()
                && !partialCustomer.getCustomerAccount().isEmpty()) {
            List<CustomerAccountRef> l_customerAccount = partialCustomer.getCustomerAccount();
            for (CustomerAccountRef customerAccount : l_customerAccount) {
                if (null == customerAccount.getId()
                        || customerAccount.getId().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.id is mandatory");
                }
                if (null == customerAccount.getName()
                        || customerAccount.getName().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.name is mandatory");
                }
                if (null == customerAccount.getStatus()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerAccount.status is mandatory");
                }
            }
        }

        if (null != partialCustomer.getCustomerCreditProfile()
                && !partialCustomer.getCustomerCreditProfile().isEmpty()) {
            List<CustomerCreditProfile> l_customerCreditProfile = partialCustomer.getCustomerCreditProfile();
            for (CustomerCreditProfile customerCreditProfile : l_customerCreditProfile) {
                if (null == customerCreditProfile.getCreditProfileDate()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.creditProfileDate is mandatory");
                }
                if (null == customerCreditProfile.getValidFor()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "customerCreditProfile.validFor is mandatory");
                } else {
                    if (customerCreditProfile.getValidFor().getStartDateTime() == null) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_GENERIC,
                                "startDateTime is mandatory");
                    }
                }
            }
        }

        if (null != partialCustomer.getPaymentMean()
                && !partialCustomer.getPaymentMean().isEmpty()) {
            List<PaymentMeanRef> l_paymentMean = partialCustomer.getPaymentMean();
            for (PaymentMeanRef paymentMean : l_paymentMean) {
                if (null == paymentMean.getId()
                        || paymentMean.getId().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.id is mandatory");
                }
                if (null == paymentMean.getHref()
                        || paymentMean.getHref().isEmpty()) {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_MANDATORY_FIELDS, "paymentMean.href is mandatory");
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(partialCustomer, JsonNode.class);
        partialCustomer.setId(id);
        if (BeanUtils.patch(currentCustomer, partialCustomer, node)) {
            publisher.updateNotification(currentCustomer, new Date());
        }

        return currentCustomer;
    }
}
