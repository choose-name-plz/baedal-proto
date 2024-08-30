package com.nameplz.baedal.domain.category.domain;


import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.store.domain.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "p_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Store> storeList = new ArrayList<>();

    public static Category createCategory(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }

    public void updateCategoryName(String newCategoryName) {
        this.name = newCategoryName;
    }

    // 삭제된 카테고리 복구
    public void cancelDeleteCategory() {
        this.deletedAt = null;
        this.deletedUser = null;
    }
}
