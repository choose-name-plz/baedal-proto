package com.nameplz.baedal.domain.store.repository;

import static com.nameplz.baedal.domain.store.domain.QProduct.product;

import com.nameplz.baedal.domain.store.domain.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Slf4j
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory query;

    public List<Product> findProductListByStoreId(UUID storeId, boolean hideNotPublic,
        Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        if (hideNotPublic) {
            builder.and(product.isPublic.isTrue());
        }

        if (storeId != null) {
            builder.and(product.store.id.eq(storeId));
        }

        checkNotDelete(builder);

        return query.select(product)
            .from(product)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(productSort(pageable))
            .fetch();
    }

    /**
     * 상품의 정렬 기준 확인 현재는 createdAt, updatedAt만 지원한다.
     */
    private OrderSpecifier<?> productSort(Pageable pageable) {
        // 만약 정렬조건이 없으면 기본값 정렬 진행
        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier<>(Order.DESC, product.createdAt);
        }

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty()) {
                case "updatedAt":
                    return new OrderSpecifier<>(direction, product.updatedAt);
                case "createdAt":
                default:
                    return new OrderSpecifier<>(direction, product.createdAt);

            }
        }
        // 도달 안할 것으로 확인 되지만 에러 발생 처리
        return new OrderSpecifier<>(Order.DESC, product.createdAt);
    }

    private void checkNotDelete(BooleanBuilder builder) {
        builder.and(product.deletedAt.isNull());
    }

}
