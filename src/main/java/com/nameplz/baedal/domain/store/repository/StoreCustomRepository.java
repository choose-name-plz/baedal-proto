package com.nameplz.baedal.domain.store.repository;

import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.domain.StoreStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface StoreCustomRepository {

    List<Store> findStoreList(String title, UUID categoryId, StoreStatus status, Pageable pageable);

}
