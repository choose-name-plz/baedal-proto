package com.nameplz.baedal.domain.order.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.store.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "p_orderline")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Integer quantity;

    @Column
    @Embedded
    private Money amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static OrderLine create(Integer quantity, Money amount, Product product, Order order) {

        OrderLine orderProduct = new OrderLine();
        orderProduct.quantity = quantity;
        orderProduct.amount = amount;
        // 외부 연결점
        orderProduct.product = product;
        orderProduct.order = order;

        return orderProduct;
    }
}
