package org.xworkz.prodex.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "product_group_names")
@NamedQueries({
        @NamedQuery(
                name = "ProductGroupNameEntity.findAllProductGroupNames",
                query = "SELECT p.productGroupName FROM ProductGroupNameEntity p ORDER BY p.productGroupName ASC"
        )
})
public class ProductGroupNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer productId;

    @Column(name = "product_group_name", nullable = false, unique = true)
    private String productGroupName;
}