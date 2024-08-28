package com.nameplz.baedal.domain.store.repository;

import com.nameplz.baedal.domain.store.domain.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {

    public List<Product> findProductListByStoreId(UUID storeId, boolean hideNotPublic,
        Pageable pageable);


}
