/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.paymentMean.event;

public enum PaymentMeanEventTypeEnum {

    PaymentMeanCreationNotification("PaymentMeanCreationNotification"),
    PaymentMeanUpdateNotification("PaymentMeanUpdateNotification"),
    PaymentMeanDeletionNotification("PaymentMeanDeletionNotification"),
    PaymentMeanValueChangeNotification("PaymentMeanValueChangeNotification");

    private String text;

    PaymentMeanEventTypeEnum(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return this.text;
    }

    /**
     *
     * @param text
     * @return
     */
    public static org.tmf.dsmapi.paymentMean.event.PaymentMeanEventTypeEnum fromString(String text) {
        if (text != null) {
            for (PaymentMeanEventTypeEnum b : PaymentMeanEventTypeEnum.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}