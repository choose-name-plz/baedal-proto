package com.nameplz.baedal.domain.store.domain;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "p_store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String title;

    @Column
    private String description;

    @Column
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_status", nullable = false)
    private StoreStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "territory_id")
    private Territory territory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    public static Store createStore(
            String title, String description, String image, User user, Territory territory,
            Category category) {
        Store store = new Store();
        // 필드 값
        store.title = title;
        store.description = description;
        store.image = image;
        // 외부 연결점
        store.user = user;
        store.territory = territory;
        store.category = category;

        // 기본 값
        store.status = StoreStatus.CLOSE;
        return store;
    }

    /**
     * 연관 관계 매핑
     */
    public void addProduct(Product product) {
        this.productList.add(product);
    }


    /**
     * 가게 업데이트
     */
    public void updateStore(String title, String description, String image, StoreStatus status,
                            Territory territory,
                            Category category) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.territory = territory;
        this.status = status;
        this.category = category;
    }

    /**
     * 가게 상태 변경
     */
    public void updateStoreStatus(StoreStatus status) {
        this.status = status;
    }

    /**
     * 가게 카테고리 변경
     */
    public void updateStoreCategory(Category category) {
        this.category = category;
    }

    /**
     * 가게 지역 변경
     */
    public void updateStoreTerritory(Territory territory) {
        this.territory = territory;
    }

    /**
     * 가게의 상품이 존재하는지 확인
     */
    public boolean hasProductList(List<UUID> orderedProductIdList) {
        return productList.stream()
                .allMatch(product -> orderedProductIdList.contains(product.getId()));
    }
}