package com.nameplz.baedal.domain.category.domain;


import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.store.domain.Store;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
}
