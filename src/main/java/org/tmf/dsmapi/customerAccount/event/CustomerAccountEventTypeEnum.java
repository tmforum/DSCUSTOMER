/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.customerAccount.event;

public enum CustomerAccountEventTypeEnum {

    CustomerAccountCreationNotification("CustomerAccountCreationNotification"),
    CustomerAccountUpdateNotification("CustomerAccountUpdateNotification"),
    CustomerAccountDeletionNotification("CustomerAccountDeletionNotification"),
    CustomerAccountValueChangeNotification("CustomerAccountValueChangeNotification");

    private String text;

    CustomerAccountEventTypeEnum(String text) {
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
    public static org.tmf.dsmapi.customerAccount.event.CustomerAccountEventTypeEnum fromString(String text) {
        if (text != null) {
            for (CustomerAccountEventTypeEnum b : CustomerAccountEventTypeEnum.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}