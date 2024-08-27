package com.nameplz.baedal.domain.category.repository;

import static com.nameplz.baedal.domain.category.domain.QCategory.category;

import com.nameplz.baedal.domain.category.domain.Category;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JPAQueryFactory query;

    @Override
    public List<Category> getCategoryList(String categoryName) {

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(categoryName)) {
            builder.and(category.name.eq(categoryName));
        }

        log.info("검색할 카테 고리 : {}", categoryName);
        return query.select(category)
            .from(category)
            .where(builder)
            .fetch();
    }
}
