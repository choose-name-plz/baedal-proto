package com.nameplz.baedal.domain.product.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.orderproduct.domain.OrderProduct;
import com.nameplz.baedal.domain.store.domain.Store;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "p_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String name;

    @Column
    private String description;

    @Column
    private Integer price;

    @Column
    private String image;

    @Column(name = "is_public")
    private boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public static Product product(String name, String description, Integer price, String image, Store store) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.image = image;
        product.store = store;

        // 기본 값
        product.isPublic = true;
        return product;
    }
}
