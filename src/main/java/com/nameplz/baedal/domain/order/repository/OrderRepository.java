package com.nameplz.baedal.domain.order.repository;

import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByStore(Store store);
}
