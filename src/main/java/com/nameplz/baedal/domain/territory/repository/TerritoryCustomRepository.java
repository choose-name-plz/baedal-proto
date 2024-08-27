package com.nameplz.baedal.domain.territory.repository;

import com.nameplz.baedal.domain.territory.domain.Territory;
import java.util.List;

public interface TerritoryCustomRepository {

    List<Territory> getCategoryList(String name);
}
