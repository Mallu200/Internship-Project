package org.xworkz.prodex.dto;

import org.xworkz.prodex.enums.PurchaseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseActionDto {

    @NotNull(message = "Purchase ID is required for action.")
    private Long purchaseId;

    @NotNull(message = "Action Status is required.")
    private PurchaseStatus status;

    private String adminComment;
}