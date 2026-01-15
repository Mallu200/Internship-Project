package org.xworkz.prodex.repository;

import org.xworkz.prodex.entity.ProductGroupNameEntity;
import org.xworkz.prodex.entity.ProductPurchaseEntity;
import org.xworkz.prodex.enums.PurchaseStatus;

import java.util.List;

public interface ProductPurchaseRepository {

    ProductGroupNameEntity saveProductGroup(ProductGroupNameEntity productGroup);

    List<String> findAllProductGroupNames();

    List<String> findAllDebitCustomerNames();

    boolean saveProductRequest(ProductPurchaseEntity entity);

    long countByStatus(PurchaseStatus purchaseStatus);

    ProductPurchaseEntity findById(Long id);

    List<ProductPurchaseEntity> findByStatus(PurchaseStatus status);

    boolean updateStatus(ProductPurchaseEntity entity);

    List<ProductPurchaseEntity> findByMemberEmail(String email);
}
