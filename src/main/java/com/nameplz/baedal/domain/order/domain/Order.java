package com.nameplz.baedal.domain.order.domain;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    // 취소 가능한 시간 (분 단위) 변수명
    private static final int ORDER_CANCELABLE_MINUTES = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "comment")
    private String comment;

    @Column
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

    public static Order create(OrderStatus orderStatus, OrderType orderType, String comment, Address address, User user, Store store) {

        Order order = new Order();

        order.orderStatus = orderStatus;
        order.orderType = orderType;
        order.comment = comment;
        order.address = address;
        order.user = user;
        order.store = store;

        return order;
    }

    /**
     * 주문 총 가격 조회
     */
    public Money getTotalOrderPrice() {

        return orderLines.stream()
                .map(OrderLine::getTotalPrice)
                .reduce(new Money(0), Money::add);
    }

    /**
     * 주문목록 추가
     */
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    /**
     * 주문 상태 변경 - 가게주인과 관리자만 가능
     */
    public void updateOrderStatus(OrderStatus orderStatus) {
        // TODO : 권환 확인 필요
        this.orderStatus = orderStatus;
    }

    /**
     * 주문 취소 - 요구사항에 따라 5분 이내에만 취소 가능
     */
    public void cancelOrder() {
        if (!isOrderCancelable()) {
            throw new GlobalException(ResultCase.ORDER_CANCEL_DENIED);
        }

        this.orderStatus = OrderStatus.CANCELED;
    }

    private boolean isOrderCancelable() {
        return LocalDateTime.now().isBefore(this.createdAt.plusMinutes(ORDER_CANCELABLE_MINUTES));
    }
}
