package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.ProductGroupNameDto;
import org.xworkz.prodex.dto.ProductPurchaseDto;
import org.xworkz.prodex.dto.PurchaseActionDto;
import org.xworkz.prodex.enums.PurchaseStatus;

import java.util.List;

public interface ProductPurchaseService {

    boolean saveProductGroupName(ProductGroupNameDto groupNameDto, List<String> serviceErrors);

    List<String> getAllProductGroupNames();

    List<String> getAllDebitCustomerNames();

    boolean submitProductRequest(ProductPurchaseDto dto, List<String> errors);

    long getPendingRequestCount();

    List<ProductPurchaseDto> findRequestsByStatus(PurchaseStatus status);

    boolean updatePurchaseStatus(PurchaseActionDto dto);

    List<ProductPurchaseDto> findRequestsByMemberEmail(String userEmail);

    boolean updateStatus(Long purchaseId, PurchaseStatus newStatus);

    boolean saveProductRequest(ProductPurchaseDto purchaseDto);
}