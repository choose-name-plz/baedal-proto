package com.nameplz.baedal.domain.category.repository;

import static com.nameplz.baedal.domain.category.domain.QCategory.category;

import com.nameplz.baedal.domain.category.domain.Category;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {
    
    private final JPAQueryFactory query;

    @Override
    public List<Category> getCategoryList(String categoryName) {

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(categoryName)) {
            builder.and(category.name.eq(categoryName));
        }

        // 삭제 정보는 조회하지 않는다.
        checkNotDelete(builder);

        return query.select(category)
            .from(category)
            .where(builder)
            .fetch();
    }

    private void checkNotDelete(BooleanBuilder builder) {
        builder.and(category.deletedAt.isNull());
    }
}
