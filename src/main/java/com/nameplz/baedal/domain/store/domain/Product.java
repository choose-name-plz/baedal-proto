package com.nameplz.baedal.domain.store.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@SQLDelete(sql = "")
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

    public static Product createProduct(String name, String description, Integer price,
        String image, Store store) {
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

    /**
     * Product 정보 수정
     */
    public void updateProduct(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     * Product 공개 여부 수정
     */
    public void updateProductPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

}
