package com.nameplz.baedal.domain.store.domain;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.review.domain.Review;
import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.user.domain.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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

    @Column(name = "is_opened")
    private boolean isOpened;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "territory_id")
    private Territory territory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();

    public static Store createStore(
        String title, String description, String image, User user,  Territory territory, Category category) {
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
        store.isOpened = true;
        return store;
    }

    /**
     * 연관 관계 매핑
     */
    public void addProduct(Product product) {
        this.productList.add(product);
    }

}