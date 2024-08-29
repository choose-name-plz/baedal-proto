package com.nameplz.baedal.domain.payment.repository;

import com.nameplz.baedal.domain.payment.domain.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByDeletedAtIsNull(Pageable pageable);
}
