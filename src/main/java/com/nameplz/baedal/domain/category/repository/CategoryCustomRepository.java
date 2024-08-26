package com.nameplz.baedal.domain.category.repository;

import com.nameplz.baedal.domain.category.domain.Category;
import java.util.List;

public interface CategoryCustomRepository {

    List<Category> getCategoryList(String categoryName);

}
