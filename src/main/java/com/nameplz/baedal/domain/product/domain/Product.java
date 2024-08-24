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

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public static Product product(String name, String description, Integer price, String image) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.image = image;
        product.isPublic = true;
        return product;
    }

    /**
     * 상품의 가게 설정
     */
    public void setStoreInfo(Store store) {
        this.store = store;
    }
}
