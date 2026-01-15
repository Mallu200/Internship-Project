package org.xworkz.prodex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupNameDto {

    @NotBlank(message = "Product Group Name must not be blank.")
    private String productGroupName;
}
