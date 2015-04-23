/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.customer.event;

public enum CustomerEventTypeEnum {

    CustomerCreationNotification("CustomerCreationNotification"),
    CustomerUpdateNotification("CustomerUpdateNotification"),
    CustomerDeletionNotification("CustomerDeletionNotification"),
    CustomerValueChangeNotification("CustomerValueChangeNotification");

    private String text;

    CustomerEventTypeEnum(String text) {
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
    public static org.tmf.dsmapi.customer.event.CustomerEventTypeEnum fromString(String text) {
        if (text != null) {
            for (CustomerEventTypeEnum b : CustomerEventTypeEnum.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}