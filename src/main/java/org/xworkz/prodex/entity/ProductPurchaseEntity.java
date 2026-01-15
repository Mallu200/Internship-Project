package org.xworkz.prodex.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.xworkz.prodex.enums.PurchaseStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_purchase")
@NamedQueries({
        @NamedQuery(name = "ProductPurchaseEntity.findByStatus",
                query = "SELECT p FROM ProductPurchaseEntity p WHERE p.status = :status"),

        @NamedQuery(name = "ProductPurchaseEntity.countByStatus",
                query = "SELECT COUNT(p) FROM ProductPurchaseEntity p WHERE p.status = :status"),

        @NamedQuery(name = "ProductPurchaseEntity.findByMemberEmail",
                query = "SELECT p FROM ProductPurchaseEntity p WHERE p.memberEmail = :email"),
        @NamedQuery(
                name = "CustomerEntity.findAllDebitCustomerNames",
                query = "SELECT c.customerName FROM CustomerEntity c WHERE c.customerType = :type"
        ),
        @NamedQuery(name = "ProductPurchaseEntity.findById",
                query = "SELECT p FROM ProductPurchaseEntity p WHERE p.purchaseId = :id")
})
public class ProductPurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "product_group_name", nullable = false)
    private String productGroupName;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "initial_price", nullable = false)
    private Double initialPrice;

    @Column(name = "stock_in_hand", nullable = false)
    private Integer stockInHand;

    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "order_due_date", nullable = false)
    private String orderDueDate;

    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PurchaseStatus status;

    @Column(name = "admin_comment")
    private String adminComment;
}