package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.ProductPurchaseDto;

public interface EmailService {

    boolean sendAdminNotificationEmail(ProductPurchaseDto dto, String placerName);
}