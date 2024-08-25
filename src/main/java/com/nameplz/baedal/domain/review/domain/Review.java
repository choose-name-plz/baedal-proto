package com.nameplz.baedal.domain.review.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "is_reported")
    private boolean isReported;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public static Review create(String content, Integer rating) {

        Review review = new Review();

        review.content = content;
        review.rating = rating;
        review.isReported = false;

        return review;
    }
}
