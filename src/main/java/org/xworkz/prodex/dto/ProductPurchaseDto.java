package org.xworkz.prodex.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.xworkz.prodex.enums.PurchaseStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchaseDto {

    private Long purchaseId;

    @NotBlank(message = "Customer Name is required.")
    private String customerName; 

    @NotBlank(message = "Product Group Name is required.")
    private String productGroupName;

    @NotBlank(message = "Manufacturer (Make) is required.")
    private String make; 

    @NotBlank(message = "Model is required.")
    private String model;

    @NotBlank(message = "Product Code is required.")
    private String productCode;

    @NotBlank(message = "Item Name is required.")
    private String itemName; 

    @NotNull(message = "Initial Price is required.")
    @Min(value = 1, message = "Initial Price must be greater than zero.")
    private Double initialPrice;

    @NotNull(message = "Stock In Hand is required.")
    @Min(value = 0, message = "Stock must be zero or a positive number.")
    private Integer stockInHand; 

    @NotNull(message = "Purchase Price is required.")
    @Min(value = 1, message = "Purchase Price must be greater than zero.")
    private Double purchasePrice;

    @NotBlank(message = "Order Due Date is required.")
    private String orderDueDate;

    private String memberEmail;

    private PurchaseStatus status = PurchaseStatus.PENDING;

}