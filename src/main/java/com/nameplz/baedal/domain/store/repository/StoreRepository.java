package com.nameplz.baedal.domain.store.repository;

import com.nameplz.baedal.domain.store.domain.Store;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreCustomRepository {

}
