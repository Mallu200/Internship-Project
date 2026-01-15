package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.ProductPurchaseDto;

public interface ReminderService {
    boolean scheduleAdminReminders(ProductPurchaseDto productPurchaseDto, String placerName, String senderEmail);
}
