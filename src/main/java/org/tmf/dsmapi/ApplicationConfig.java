package org.tmf.dsmapi;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("api/customerManagement/v2")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages ("org.codehaus.jackson.jaxrs");
        register(org.tmf.dsmapi.commons.jaxrs.BadUsageExceptionMapper.class);
        register(org.tmf.dsmapi.commons.jaxrs.JacksonConfigurator.class);
        register(org.tmf.dsmapi.commons.jaxrs.JsonMappingExceptionMapper.class);
        register(org.tmf.dsmapi.commons.jaxrs.UnknowResourceExceptionMapper.class);
        register(org.tmf.dsmapi.customer.CustomerAdminResource.class);
        register(org.tmf.dsmapi.customer.CustomerResource.class);
        register(org.tmf.dsmapi.customerAccount.CustomerAccountAdminResource.class);
        register(org.tmf.dsmapi.customerAccount.CustomerAccountResource.class);
        register(org.tmf.dsmapi.hub.HubResource.class);
        register(org.tmf.dsmapi.paymentMean.PaymentMeanAdminResource.class);
        register(org.tmf.dsmapi.paymentMean.PaymentMeanResource.class);
    }

}
