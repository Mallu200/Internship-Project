package org.xworkz.prodex.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xworkz.prodex.dto.ProductGroupNameDto;
import org.xworkz.prodex.dto.ProductPurchaseDto;
import org.xworkz.prodex.dto.PurchaseActionDto;
import org.xworkz.prodex.entity.ProductGroupNameEntity;
import org.xworkz.prodex.entity.ProductPurchaseEntity;
import org.xworkz.prodex.enums.PurchaseStatus;
import org.xworkz.prodex.repository.ProductPurchaseRepository;
import org.xworkz.prodex.service.ProductPurchaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    @Autowired
    private ProductPurchaseRepository productPurchaseRepository;

    @Override
    public boolean saveProductGroupName(ProductGroupNameDto groupKeywordsDto, List<String> serviceErrors) {
        try {
            if (groupKeywordsDto == null || groupKeywordsDto.getProductGroupName() == null) {
                serviceErrors.add("Product group name cannot be empty.");
                return false;
            }
            ProductGroupNameEntity productGroup = new ProductGroupNameEntity();
            BeanUtils.copyProperties(groupKeywordsDto, productGroup);
            ProductGroupNameEntity savedEntity = productPurchaseRepository.saveProductGroup(productGroup);
            return savedEntity != null;
        } catch (Exception e) {
            serviceErrors.add("Product group name already exists or database error.");
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllProductGroupNames() {
        try {
            List<String> names = productPurchaseRepository.findAllProductGroupNames();
            return (names != null) ? names : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllDebitCustomerNames() {
        try {
            List<String> customers = productPurchaseRepository.findAllDebitCustomerNames();
            return (customers != null) ? customers : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean submitProductRequest(ProductPurchaseDto dto, List<String> errors) {
        if (dto == null) {
            errors.add("Purchase data is null.");
            return false;
        }
        try {
            ProductPurchaseEntity entity = new ProductPurchaseEntity();
            BeanUtils.copyProperties(dto, entity);

            if (entity.getItemName() == null || entity.getItemName().trim().isEmpty()) {
                entity.setItemName(dto.getMake() + " " + dto.getModel());
            }

            entity.setStatus(PurchaseStatus.PENDING);
            return productPurchaseRepository.saveProductRequest(entity);
        } catch (Exception e) {
            errors.add("Submission error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveProductRequest(ProductPurchaseDto purchaseDto) {
        if (purchaseDto == null) return false;
        try {
            ProductPurchaseEntity entity = new ProductPurchaseEntity();
            BeanUtils.copyProperties(purchaseDto, entity);

            if (entity.getStatus() == null) {
                entity.setStatus(PurchaseStatus.PENDING);
            }

            return productPurchaseRepository.saveProductRequest(entity);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePurchaseStatus(PurchaseActionDto actionDto) {
        if (actionDto == null || actionDto.getPurchaseId() == null) {
            return false;
        }

        try {
            ProductPurchaseEntity entity = productPurchaseRepository.findById(actionDto.getPurchaseId());

            if (entity != null) {
                if (entity.getStatus() == PurchaseStatus.PENDING || actionDto.getStatus() == PurchaseStatus.RECEIVED) {
                    entity.setStatus(actionDto.getStatus());
                    entity.setAdminComment(actionDto.getAdminComment());
                    return productPurchaseRepository.updateStatus(entity);
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateStatus(Long purchaseId, PurchaseStatus newStatus) {
        PurchaseActionDto simpleAction = new PurchaseActionDto();
        simpleAction.setPurchaseId(purchaseId);
        simpleAction.setStatus(newStatus);
        simpleAction.setAdminComment("Status updated to " + newStatus);
        return this.updatePurchaseStatus(simpleAction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPurchaseDto> findRequestsByMemberEmail(String email) {
        try {
            List<ProductPurchaseEntity> entities = productPurchaseRepository.findByMemberEmail(email);
            return convertEntitiesToDtos(entities);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getPendingRequestCount() {
        try {
            return productPurchaseRepository.countByStatus(PurchaseStatus.PENDING);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPurchaseDto> findRequestsByStatus(PurchaseStatus status) {
        try {
            List<ProductPurchaseEntity> entities = productPurchaseRepository.findByStatus(status);
            return convertEntitiesToDtos(entities);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<ProductPurchaseDto> convertEntitiesToDtos(List<ProductPurchaseEntity> entities) {
        if (entities == null || entities.isEmpty()) return Collections.emptyList();
        List<ProductPurchaseDto> dtos = new ArrayList<>();
        for (ProductPurchaseEntity entity : entities) {
            ProductPurchaseDto dto = new ProductPurchaseDto();
            BeanUtils.copyProperties(entity, dto);
            dtos.add(dto);
        }
        return dtos;
    }
}