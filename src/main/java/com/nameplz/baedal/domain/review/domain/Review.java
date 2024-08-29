package com.nameplz.baedal.domain.review.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.user.domain.User;
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
    @Embedded
    private Rating rating;

    @Column(name = "is_reported")
    private boolean isReported;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static Review create(String content, Rating rating, boolean isReported, User user, Order order) {

        Review review = new Review();

        review.content = content;
        review.rating = rating;
        review.isReported = isReported;
        review.user = user;
        review.order = order;

        return review;
    }

    public void update(Rating rating, boolean isReported, String content) {
        this.rating = rating;
        this.isReported = isReported;
        this.content = content;
    }
}
