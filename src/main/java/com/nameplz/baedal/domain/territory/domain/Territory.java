package com.nameplz.baedal.domain.territory.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.store.domain.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "p_teritory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Territory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "territory")
    private List<Store> storeList = new ArrayList<>();

    public static Territory createTerritory(String name) {
        Territory territory = new Territory();
        territory.name = name;
        return territory;
    }

    public void updateTerritoryName(String newTerritoryName) {
        name = newTerritoryName;
    }

    // 지역 삭제 취소
    public void cancelDeleteTerritory() {
        deletedAt = null;
        deletedUser = null;
    }

}
