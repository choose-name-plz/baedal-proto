package com.nameplz.baedal.domain.orderproduct.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "p_order_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Integer quantity;

    @Column
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static OrderProduct createOrderProduct(
        Integer quantity, Integer amount, Product product, Order order) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.quantity = quantity;
        orderProduct.amount = amount;
        // 외부 연결점
        orderProduct.product = product;
        orderProduct.order = order;

        return orderProduct;
    }
}
