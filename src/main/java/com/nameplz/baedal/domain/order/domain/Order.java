package com.nameplz.baedal.domain.order.domain;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "comment")
    private String comment;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @BatchSize(size = 500)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    public static Order create(OrderStatus orderStatus, String comment, Address address, User user, Store store) {

        Order order = new Order();

        order.orderStatus = orderStatus;
        order.comment = comment;
        order.address = address;
        order.user = user;
        order.store = store;

        return order;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }
}
