package com.nameplz.baedal.domain.store.repository;

import static com.nameplz.baedal.domain.store.domain.QProduct.product;
import static com.nameplz.baedal.domain.store.domain.QStore.store;

import com.nameplz.baedal.domain.category.domain.QCategory;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.domain.StoreStatus;
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
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Store> findStoreList(String title, UUID categoryId, UUID territoryId,
        StoreStatus status,
        Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        checkNotDelete(builder);

        // 제목검색
        if (StringUtils.hasText(title)) {
            builder.and(store.title.like("%" + title + "%"));
        }

        // 카테고리 검색
        if (categoryId != null) {
            builder.and(store.category.id.eq(categoryId));
        }

        // 지역 검색
        if (territoryId != null) {
            builder.and(store.territory.id.eq(territoryId));
        }

        // 가게 상태 검색
        if (status != null) {
            builder.and(store.status.eq(status));
        }

        return query
            .select(store)
            .from(store)
            .leftJoin(store.category, QCategory.category)
            .fetchJoin()
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(storeSort(pageable))
            .fetch();
    }

    /**
     * 가게의 정렬 기준 확인 현재는 createdAt, updatedAt만 지원한다.
     */
    private OrderSpecifier<?> storeSort(Pageable pageable) {
        // 만약 정렬조건이 없으면 기본값 정렬 진행
        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier<>(Order.DESC, store.createdAt);
        }

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty()) {
                case "updatedAt":
                    return new OrderSpecifier<>(direction, store.updatedAt);
                case "createdAt":

                default:
                    return new OrderSpecifier<>(direction, store.createdAt);
            }
        }
        // 도달 안할 것으로 확인 되지만 에러 발생 처리
        return new OrderSpecifier<>(Order.DESC, product.createdAt);
    }

    private void checkNotDelete(BooleanBuilder builder) {
        builder.and(store.deletedAt.isNull());
    }
}
