package com.nameplz.baedal.domain.order.repository;

import com.nameplz.baedal.domain.order.domain.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    /**
     * Pageable 인터페이스를 이용하여 페이징 처리 List 타입으로 반환하여 count 쿼리를 실행하지 않게 함 (Page 타입으로 반환하면 count 쿼리를 추가로
     * 실행하여 성능 저하가 발생할 수 있음)
     */
    List<Order> findAllByUser_UsernameAndDeletedAtIsNull(String username, Pageable pageable);

    List<Order> findAllByStore_IdAndDeletedAtIsNull(UUID storeId, Pageable pageable);

    Optional<Order> findByIdAndDeletedAtIsNull(UUID orderId);
}
