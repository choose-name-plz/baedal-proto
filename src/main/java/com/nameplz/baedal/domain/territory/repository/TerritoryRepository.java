package com.nameplz.baedal.domain.territory.repository;

import com.nameplz.baedal.domain.territory.domain.Territory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerritoryRepository extends JpaRepository<Territory, UUID>,
    TerritoryCustomRepository {

    Optional<Territory> findByName(String name);

}
