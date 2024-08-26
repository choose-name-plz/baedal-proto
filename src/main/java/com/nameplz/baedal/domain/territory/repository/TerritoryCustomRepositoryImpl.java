package com.nameplz.baedal.domain.territory.repository;

import static com.nameplz.baedal.domain.territory.domain.QTerritory.territory;

import com.nameplz.baedal.domain.territory.domain.Territory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class TerritoryCustomRepositoryImpl implements TerritoryCustomRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JPAQueryFactory query;

    @Override
    public List<Territory> getCategoryList(String name) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(name)) {
            builder.and(territory.name.eq(name));
        }
        return query
            .select(territory)
            .from(territory)
            .where(builder)
            .fetch();
    }

}
