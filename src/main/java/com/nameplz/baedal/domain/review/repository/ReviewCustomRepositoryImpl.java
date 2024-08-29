package com.nameplz.baedal.domain.review.repository;

import com.nameplz.baedal.domain.review.domain.Review;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.nameplz.baedal.domain.review.domain.QReview.review;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory query;

    /**
     * 주문 아이디로 리뷰 단건 조회
     */
    @Override
    public Review findByOrderIdWithoutDeleted(UUID orderId) {

        return query
                .selectFrom(review)
                .where(isReviewNotDeleted(), isOrderIdEqualTo(orderId), isOrderNotDeleted())
                .fetchOne();
    }

    private BooleanExpression isOrderIdEqualTo(UUID orderId) {
        return review.order.id.eq(orderId);
    }

    /**
     * 유저 아이디로 리뷰 리스트 조회
     */
    @Override
    public List<Review> findAllByUsernameWithoutDeleted(String username, Pageable pageable) {

        JPAQuery<Review> jpaQuery = query
                .selectFrom(review)
                .leftJoin(review.user)
                .fetchJoin()
                .where(isReviewNotDeleted(), isUserNotDeleted(), isUsernameEqualTo(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        pageable.getSort().forEach(order -> jpaQuery.orderBy(createOrderSpecifier(order)));

        return jpaQuery.fetch();
    }

    private BooleanExpression isUsernameEqualTo(String username) {
        return review.user.username.eq(username);
    }

    /**
     * 가게 아이디로 리뷰 리스트 조회 - 리뷰 평점 조회용
     */
    @Override
    public List<Review> findAllByStoreIdWithoutDeleted(UUID storeId, Pageable pageable) {


        JPAQuery<Review> jpaQuery = query
                .selectFrom(review)
                .leftJoin(review.order.store)
                .fetchJoin()
                .where(
                        isReviewNotDeleted(),
                        isStoreNotDeleted(),
                        isReportedFalse(),
                        isStoreIdEqualTo(storeId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        pageable.getSort().forEach(order -> jpaQuery.orderBy(createOrderSpecifier(order)));

        return jpaQuery.fetch();
    }

    private BooleanExpression isReportedFalse() {
        return review.isReported.isFalse();
    }

    private BooleanExpression isStoreIdEqualTo(UUID storeId) {
        return review.order.store.id.eq(storeId);
    }

    /**
     * 생성일, 수정일 기준으로 정렬 조건을 생성
     * 기본값은 생성일 내림차순
     */
    private OrderSpecifier<LocalDateTime> createOrderSpecifier(Sort.Order order) {

        String sortType = order.getProperty(); // 정렬 기준 필드
        Order orderDirection = order.isAscending() ? Order.ASC : Order.DESC; // 정렬 방향

        return switch (sortType) {
            case "createdAt" -> new OrderSpecifier<>(orderDirection, review.createdAt);
            case "updatedAt" -> new OrderSpecifier<>(orderDirection, review.updatedAt);
            default -> new OrderSpecifier<>(Order.DESC, review.createdAt);
        };
    }

    private BooleanExpression isOrderNotDeleted() {
        return review.order.deletedAt.isNull();
    }

    private BooleanExpression isUserNotDeleted() {
        return review.user.deletedAt.isNull();
    }

    private BooleanExpression isStoreNotDeleted() {
        return review.order.store.deletedAt.isNull();
    }

    private BooleanExpression isReviewNotDeleted() {
        return review.deletedAt.isNull();
    }
}
