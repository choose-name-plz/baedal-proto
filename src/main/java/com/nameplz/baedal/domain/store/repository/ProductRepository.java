package com.nameplz.baedal.domain.store.repository;

import com.nameplz.baedal.domain.store.domain.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductCustomRepository {

    /**
     * 현재 공개 상태인 메뉴를 갖고 온다.
     */
    List<Product> findAllByStoreIdAndIsPublicAndDeletedAtIsNull(UUID storeId, boolean isPublic,
        Pageable pageable);
}
