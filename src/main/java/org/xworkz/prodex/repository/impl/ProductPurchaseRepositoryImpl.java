package org.xworkz.prodex.repository.impl;

import org.springframework.stereotype.Repository;
import org.xworkz.prodex.entity.ProductGroupNameEntity;
import org.xworkz.prodex.entity.ProductPurchaseEntity;
import org.xworkz.prodex.enums.CustomerType; 
import org.xworkz.prodex.enums.PurchaseStatus;
import org.xworkz.prodex.repository.ProductPurchaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductPurchaseRepositoryImpl implements ProductPurchaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductGroupNameEntity saveProductGroup(ProductGroupNameEntity productGroup) {
        try {
            entityManager.persist(productGroup);
            return productGroup;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> findAllProductGroupNames() {
        try {
            return entityManager.createNamedQuery("ProductGroupNameEntity.findAllProductGroupNames", String.class)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> findAllDebitCustomerNames() {
        try {
            return entityManager.createNamedQuery("CustomerEntity.findAllDebitCustomerNames", String.class)
                    .setParameter("type", CustomerType.DEBTOR)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(); 
            return Collections.emptyList();
        }
    }

    @Override
    public boolean saveProductRequest(ProductPurchaseEntity entity) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long countByStatus(PurchaseStatus purchaseStatus) {
        try {
            return entityManager.createNamedQuery("ProductPurchaseEntity.countByStatus", Long.class)
                    .setParameter("status", purchaseStatus)
                    .getSingleResult();
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public ProductPurchaseEntity findById(Long id) {
        try {
            return entityManager.createNamedQuery("ProductPurchaseEntity.findById", ProductPurchaseEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProductPurchaseEntity> findByStatus(PurchaseStatus status) {
        try {
            return entityManager.createNamedQuery("ProductPurchaseEntity.findByStatus", ProductPurchaseEntity.class)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updateStatus(ProductPurchaseEntity entity) {
        try {
            entityManager.merge(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ProductPurchaseEntity> findByMemberEmail(String email) {
        try {
            return entityManager.createNamedQuery("ProductPurchaseEntity.findByMemberEmail", ProductPurchaseEntity.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}