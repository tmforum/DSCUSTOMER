/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.paymentMean.event;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import static org.codehaus.jackson.annotate.JsonAutoDetect.Visibility.ANY;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.commons.utils.CustomJsonDateSerializer;
import org.tmf.dsmapi.customer.model.CustomerAccount;
import org.tmf.dsmapi.customer.model.PaymentMean;

@XmlRootElement
@Entity
@Table(name="Event_PaymentMean")
@JsonPropertyOrder(value = {"id", "eventTime", "eventType", "event"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaymentMeanEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
 @JsonProperty("eventId")
    private String id;


    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date eventTime;

    @Enumerated(value = EnumType.STRING)
    private PaymentMeanEventTypeEnum eventType;

    private PaymentMean resource; //check for object

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @JsonAutoDetect(fieldVisibility = ANY)
    class EventBody {
        private PaymentMean paymentMean;
        public PaymentMean getPayMean() {
            return paymentMean;
        }
        public EventBody(PaymentMean paymentMean) { 
        this.paymentMean = paymentMean;
    }
    
       
    }
   @JsonProperty("event")
   public EventBody getEvent() {
       
       return new EventBody(getResource() );
   }
    


    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public PaymentMeanEventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(PaymentMeanEventTypeEnum eventType) {
        this.eventType = eventType;
    }

   @JsonIgnore
    public PaymentMean getResource() {
        
        
        return resource;
    }

    public void setResource(PaymentMean resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "PaymentMeanEvent{" + "id=" + id + ", eventTime=" + eventTime + ", eventType=" + eventType + ", resource=" + resource + '}';
    }

   

}
