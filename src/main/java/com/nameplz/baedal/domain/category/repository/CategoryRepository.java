package com.nameplz.baedal.domain.category.repository;

import com.nameplz.baedal.domain.category.domain.Category;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID>,
    CategoryCustomRepository {

    Optional<Category> findByName(String name);

    Optional<Category> findByIdAndDeletedAtIsNull(UUID categoryId);
}
