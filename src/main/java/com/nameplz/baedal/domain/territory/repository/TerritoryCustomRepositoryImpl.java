package com.nameplz.baedal.domain.territory.repository;

import static com.nameplz.baedal.domain.territory.domain.QTerritory.territory;

import com.nameplz.baedal.domain.territory.domain.Territory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class TerritoryCustomRepositoryImpl implements TerritoryCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Territory> getCategoryList(String name) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(name)) {
            builder.and(territory.name.eq(name));
        }

        // 삭제 정보는 조회하지 않는다.
        checkNotDelete(builder);

        return query
            .select(territory)
            .from(territory)
            .where(builder)
            .fetch();
    }

    private void checkNotDelete(BooleanBuilder builder) {
        builder.and(territory.deletedAt.isNull());
    }

}
